/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unesp.amoraes.dbvis.rest;

import br.unesp.amoraes.dbvis.beans.ParametersEntity;
import br.unesp.amoraes.dbvis.dao.ParametersDAO;
import br.unesp.amoraes.dbvis.rest.domain.Device;
import br.unesp.amoraes.dbvis.rest.resources.DevicesResource;
import br.unesp.amoraes.dbvis.rest.resources.ImagesResouce;
import br.unesp.amoraes.dbvis.rest.resources.MetadataResource;
import br.unesp.amoraes.dbvis.rest.resources.NodesResource;
import br.unesp.amoraes.dbvis.rest.resources.SelectionsResource;
import br.unesp.amoraes.dbvis.rest.resources.UserContentsResource;
import br.unesp.amoraes.dbvis.viewer.graph.GraphViewer;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jboss.resteasy.plugins.server.sun.http.HttpContextBuilder;
import org.jboss.resteasy.spi.ResteasyDeployment;

/**
 *
 * @author alessandro
 */
public class APIServer {
    private static APIServer instance;
    private HttpServer httpServer = null;
    private String chartImagePrefix;
    private HttpContextBuilder contextBuilder = null;
    private boolean started = false;
    private HashMap<String, Device> connectedDevices = new HashMap<String, Device>();
    protected static final int PORT_NUMBER = 8081;
    protected static final int PORT_NUMBER_SOCKET = 8082;
    private String password; //change to pic on internal db
    
    private APIServer(){
        this.connectedDevices.put("admin", new Device("admin", "test"));
        this.chartImagePrefix = "/tmp/dbvis_userchart_"+(new Date().getTime()+"_");
    }
    
    public static APIServer getInstance(){
        if(instance == null)
            instance = new APIServer();
        return instance;
    }

    public String getChartImagePrefix(){
        return this.chartImagePrefix;
    }
    
    public void start(){
        try {
            if(started){
                Logger.getLogger(APIServer.class.getName()).log(Level.WARNING, "Server already started");
            }
            ParametersEntity parameters = ParametersDAO.get();
            if(parameters == null || parameters.getApiPassword() == null ||  parameters.getApiPassword().equals("")){
                Logger.getLogger(APIServer.class.getName()).log(Level.INFO, "No password, starting with default 123456");
                this.password = "123456";
            }else{
                this.password = parameters.getApiPassword();
            }
            httpServer = HttpServer.create(new InetSocketAddress(PORT_NUMBER), 10);
            httpServer.setExecutor(Executors.newFixedThreadPool(50));
            contextBuilder = new HttpContextBuilder();
            ResteasyDeployment deployment = contextBuilder.getDeployment();
            deployment.getActualResourceClasses().add(ImagesResouce.class);
            deployment.getActualResourceClasses().add(DevicesResource.class);
            deployment.getActualResourceClasses().add(SelectionsResource.class);
            deployment.getActualResourceClasses().add(NodesResource.class);
            deployment.getActualResourceClasses().add(UserContentsResource.class);
            deployment.getActualResourceClasses().add(MetadataResource.class);
            HttpContext context = contextBuilder.bind(httpServer);
            
            httpServer.start();
            started = true;
            Logger.getLogger(APIServer.class.getName()).log(Level.INFO, "API Server started and running on port "+PORT_NUMBER);
            //start a socket server to receive movimentation from users (HTTP requests too slow?)
            MultiThreadedServer multiThreadedServer = new MultiThreadedServer(PORT_NUMBER_SOCKET, this);
            Thread tSocketServer = new Thread(multiThreadedServer);
            tSocketServer.start();
            
        } catch (IOException ex) {
            Logger.getLogger(APIServer.class.getName()).log(Level.SEVERE, "Error starting web server", ex);
        }
    }
    
    public void stop(){
        if(!started){
            Logger.getLogger(APIServer.class.getName()).log(Level.WARNING, "Server not started yet");
        }
        contextBuilder.cleanup();
        httpServer.stop(10);
        Logger.getLogger(APIServer.class.getName()).log(Level.INFO, "API Server stopped");
    }
    
    public String connectDevice(String name){
        if(this.connectedDevices.get(name) != null){
            Logger.getLogger(APIServer.class.getName()).log(Level.INFO, "Device already connected: "+name);
            return this.connectedDevices.get(name).getToken();
        }
        String token = UUID.randomUUID().toString();
        this.connectedDevices.put(name, new Device(name, token));
        Logger.getLogger(APIServer.class.getName()).log(Level.INFO, "New device connected: "+name);
        return token;
    }
    
    public void disconnectDevice(String name){
        if(this.connectedDevices.get(name) != null){
            this.connectedDevices.remove(name);
            GraphViewer.getInstance().removeDevice(name);
            Logger.getLogger(APIServer.class.getName()).log(Level.INFO, "Device disconnected: "+name);
        }
    }

    public boolean isConnected(String name, String token){
        if(this.connectedDevices.containsKey(name) && this.connectedDevices.get(name).getToken().equals(token)){
            return true;
        }
        return false;
    }
    
    public Device getDevice(String name){
        if(this.connectedDevices.containsKey(name)){
            return this.connectedDevices.get(name);
        }
        return null;
    }
    
    public Collection<Device> getDevices(){
        return this.connectedDevices.values();
    }

    public Object getPassword() {
        return password;
    }
    
    public void setPassword(String password){
        this.password = password;
    }

    public void updatePosition(String deviceName, int x, int y, int width, int height) {
        if(this.connectedDevices.containsKey(deviceName)){
            Device d = this.connectedDevices.get(deviceName);
            d.setX(x);
            d.setY(y);
            d.setWidth(width);
            d.setHeight(height);
            Logger.getLogger(APIServer.class.getName()).log(Level.INFO, "Device +"+deviceName+"+position: (x:"+x+",y:"+y+",width:"+width+",height:"+height);
            GraphViewer.getInstance().showDevice(x, y, width, height, deviceName);
        }
    }
    
    
}

class MultiThreadedServer implements Runnable{

    protected int          serverPort   = APIServer.PORT_NUMBER_SOCKET;
    protected ServerSocket serverSocket = null;
    protected boolean      isStopped    = false;
    protected Thread       runningThread= null;
    protected APIServer    apiServer = null;

    public MultiThreadedServer(int port, APIServer apiServer){
        this.serverPort = port;
        this.apiServer = apiServer;
    }

    public void run(){
        synchronized(this){
            this.runningThread = Thread.currentThread();
        }
        openServerSocket();
        while(! isStopped()){
            Socket clientSocket = null;
            try {
                clientSocket = this.serverSocket.accept();
            } catch (IOException e) {
                if(isStopped()) {
                    System.out.println("Server Stopped.") ;
                    
                    return;
                }
                throw new RuntimeException(
                    "Error accepting client connection", e);
            }
            new Thread(
                new WorkerRunnable(clientSocket, apiServer)
            ).start();
        }
        System.out.println("Server Stopped.") ;
    }


    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    public synchronized void stop(){
        this.isStopped = true;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }

    private void openServerSocket() {
        try {
            this.serverSocket = new ServerSocket(this.serverPort);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port 8080", e);
        }
    }

}


class WorkerRunnable implements Runnable{

    protected Socket clientSocket = null;
    boolean m_bRunThread = true;
    APIServer apiServer;

    public WorkerRunnable(Socket clientSocket, APIServer apiServer) {
        this.clientSocket = clientSocket;
        this.apiServer = apiServer;
    }

    public void run() {
            BufferedReader in = null;
            PrintWriter out = null;
            //Logger.getLogger(ClientServiceThread.class.getName(),"Socket client connected: "+myClientSocket.getInetAddress().getHostName());
            try {
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                while (m_bRunThread) {
                    // read incoming stream 
                    String clientCommand = in.readLine();
                    if(clientCommand != null){
                        System.out.println("Client Says :" + clientCommand);

                        if (clientCommand.equalsIgnoreCase("quit")) {
                            // Special command. Quit this thread 
                            m_bRunThread = false;
                            System.out.print("Stopping client thread for client : ");
                        } else if (clientCommand.startsWith("positionUpdate")) {
                            //String deviceName = , int x, int y, int width, int height;
                            StringTokenizer tokenizer = new StringTokenizer(clientCommand, "#");
                            tokenizer.nextToken(); //ignore the first (command)
                            String deviceName = tokenizer.nextToken();
                            String token = tokenizer.nextToken();
                            int x = Integer.parseInt(tokenizer.nextToken());
                            int y = Integer.parseInt(tokenizer.nextToken());
                            int width = Integer.parseInt(tokenizer.nextToken());
                            int height = Integer.parseInt(tokenizer.nextToken());
                            if(apiServer.isConnected(deviceName, token)){
                                apiServer.updatePosition(deviceName, x, y, width, height);
                            }
                        } else {
                            // Process it 
                            //out.println("Server Says : " + clientCommand);
                            //out.flush();
                            Logger.getLogger(WorkerRunnable.class.getName(),"Socket command received: "+clientCommand);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}
