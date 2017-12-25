/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unesp.amoraes.dbvis.rest.resources;

import br.unesp.amoraes.dbvis.internals.FunctionsHelper;
import br.unesp.amoraes.dbvis.rest.APIServer;
import br.unesp.amoraes.dbvis.rest.domain.Device;
import br.unesp.amoraes.dbvis.rest.domain.UserChart;
import br.unesp.amoraes.dbvis.rest.domain.UserText;
import br.unesp.amoraes.dbvis.viewer.graph.GraphViewer;
import java.awt.Component;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartInput;


/**
 *
 * @author Alessandro Moraes
 */
@Path("/userContents")
public class UserContentsResource {
    private static int textSequence = 0;
    private static int chartSequence = 0;
    public Component screenComponent = null;
    
    public UserContentsResource() {
        if (GraphViewer.getInstance() != null) {
            screenComponent = GraphViewer.getInstance().getVisualizationViewer();
        }
    }
    
    private synchronized int getNextSeqText(){
        int s = textSequence;
        textSequence++;
        return s;
    }
    
    private synchronized int getNextSeqChart(){
        int s = chartSequence;
        chartSequence++;
        return s;
    }
    
    
    
    @POST
    @Path("/texts")
    public Response addText(@QueryParam("deviceName") String deviceName, @QueryParam("token") String token, @FormParam("x") int x, @FormParam("y") int y, @FormParam("text") String text){
        if(!APIServer.getInstance().isConnected(deviceName, token)){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        UserText ut = new UserText();
        ut.setId(getNextSeqText());
        ut.setDate(new Date());
        ut.setText(text);
        ut.setX(x);
        ut.setY(y);
        ut.setDeviceName(deviceName);
        APIServer.getInstance().getDevice(deviceName).getUserContents().getTexts().put(ut.getId(), ut);
        Logger.getLogger(UserContentsResource.class.getName()).log(Level.INFO, "Text #"+ut.getId()+" added in "+x+","+y+": "+text);
        return Response.status(Response.Status.OK).type(MediaType.TEXT_PLAIN).entity(ut.getId()).build();
    }
    
    @DELETE
    @Path("/texts/{id}")
    public Response removeText(@QueryParam("deviceName") String deviceName, @QueryParam("token") String token, @PathParam("id") int id){
        if(!APIServer.getInstance().isConnected(deviceName, token)){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        UserText ut = APIServer.getInstance().getDevice(deviceName).getUserContents().getTexts().get(id);
        ut.setDate(new Date());
        ut.setText("");
        ut.setDeleted(true);
        Logger.getLogger(FunctionsHelper.class.getName()).log(Level.INFO, "Text #"+id+" removed");
        return Response.status(Response.Status.OK).type(MediaType.TEXT_PLAIN).entity(ut.getId()).build();
    }
    
    @GET
    @Path("/texts")
    public Response listTexts(@QueryParam("deviceName") String deviceName, @QueryParam("token") String token, @QueryParam("timestamp") long timestamp){
        if(!APIServer.getInstance().isConnected(deviceName, token)){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        Date d = new Date(timestamp);
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<collection>";
        for(Device device : APIServer.getInstance().getDevices()){
            for(UserText ut : device.getUserContents().getTexts().values()){
                if(ut.getDate().getTime() > timestamp){
                    xml += ut.getXml();
                }
            }
        }
        xml += "</collection>";
        //Logger.getLogger(FunctionsHelper.class.getName()).log(Level.INFO, "Getting texts newer than "+timestamp);
        return Response.status(Response.Status.OK).type(MediaType.TEXT_XML).entity(xml).build();
    }
    
    @POST
    @Path("/charts")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response addChart(MultipartInput input){
        String deviceName = null;
        String token = null;
        String text = null;
        String fileName = null;
        String nodesInfo = null;
        String axisX = null;
        String axisY = null;
        String axisZ = null;
        int type = -1;
        int id = getNextSeqChart();
        for(InputPart part : input.getParts()){
            String fieldName = getFieldName(part.getHeaders());
            if(fieldName.equals("deviceName")){
                try {
                    deviceName = part.getBodyAsString();
                } catch (IOException ex) {
                    Logger.getLogger(UserContentsResource.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else if(fieldName.equals("token")){
                try {
                    token = part.getBodyAsString();
                } catch (IOException ex) {
                    Logger.getLogger(UserContentsResource.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else if(fieldName.equals("text")){
                try {
                    text = part.getBodyAsString();
                } catch (IOException ex) {
                    Logger.getLogger(UserContentsResource.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else if(fieldName.equals("file")){
                InputStream inputStream = null;
                try {
                    inputStream = part.getBody(InputStream.class,null);
                    byte [] bytes = IOUtils.toByteArray(inputStream);
                    //constructs upload file path
                    fileName = APIServer.getInstance().getChartImagePrefix() + id + "." + getFileExtension(part.getHeaders());
                    writeFile(bytes,fileName);
                } catch (IOException ex) {
                    Logger.getLogger(UserContentsResource.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        inputStream.close();
                    } catch (IOException ex) {
                        Logger.getLogger(UserContentsResource.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            else if(fieldName.equals("nodesInfo")){
                try {
                    nodesInfo = part.getBodyAsString();
                } catch (IOException ex) {
                    Logger.getLogger(UserContentsResource.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else if(fieldName.equals("axisX")){
                try {
                    axisX = part.getBodyAsString();
                } catch (IOException ex) {
                    Logger.getLogger(UserContentsResource.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else if(fieldName.equals("axisY")){
                try {
                    axisY = part.getBodyAsString();
                } catch (IOException ex) {
                    Logger.getLogger(UserContentsResource.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else if(fieldName.equals("axisZ")){
                try {
                    axisZ = part.getBodyAsString();
                } catch (IOException ex) {
                    Logger.getLogger(UserContentsResource.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else if(fieldName.equals("type")){
                try {
                    type = Integer.parseInt(part.getBodyAsString());
                } catch (IOException ex) {
                    Logger.getLogger(UserContentsResource.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if(!APIServer.getInstance().isConnected(deviceName, token) || deviceName == null || token == null){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        UserChart uc = new UserChart();
        uc.setId(id);
        uc.setDate(new Date());
        uc.setText(text);
        uc.setFileName(fileName);
        uc.setDeviceName(deviceName);
        uc.setAxisX(axisX);
        uc.setAxisY(axisY);
        uc.setAxisZ(axisZ);
        uc.setNodesInfo(nodesInfo);
        uc.setType(type);
        APIServer.getInstance().getDevice(deviceName).getUserContents().getCharts().put(uc.getId(), uc);
        Logger.getLogger(UserContentsResource.class.getName()).log(Level.INFO, "Chart #"+uc.getId()+" added: "+text);
        return Response.status(Response.Status.OK).type(MediaType.TEXT_PLAIN).entity(uc.getId()).build();
    }
    
    @DELETE
    @Path("/charts/{id}")
    public Response removeChart(@QueryParam("deviceName") String deviceName, @QueryParam("token") String token, @PathParam("id") int id){
        if(!APIServer.getInstance().isConnected(deviceName, token)){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        UserChart uc = APIServer.getInstance().getDevice(deviceName).getUserContents().getCharts().get(id);
        uc.setDate(new Date());
        uc.setText("");
        uc.setDeleted(true);
        File file = new File(uc.getFileName());
        file.delete();
        Logger.getLogger(FunctionsHelper.class.getName()).log(Level.INFO, "Chart #"+id+" removed");
        return Response.status(Response.Status.OK).type(MediaType.TEXT_PLAIN).entity(uc.getId()).build();
    }
    
    @GET
    @Path("/charts")
    public Response listCharts(@QueryParam("deviceName") String deviceName, @QueryParam("token") String token, @QueryParam("timestamp") long timestamp){
        if(!APIServer.getInstance().isConnected(deviceName, token)){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        Date d = new Date(timestamp);
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<collection>";
        for(Device device : APIServer.getInstance().getDevices()){
            for(UserChart uc : device.getUserContents().getCharts().values()){
                if(uc.getDate().getTime() > timestamp){
                    xml += uc.getXml();
                }
            }
        }
        xml += "</collection>";
        //Logger.getLogger(FunctionsHelper.class.getName()).log(Level.INFO, "Getting texts newer than "+timestamp);
        return Response.status(Response.Status.OK).type(MediaType.TEXT_XML).entity(xml).build();
    }
    
    @GET
    @Path("/charts/{id},{owner}/download")
    public Response getChartImage(@QueryParam("deviceName") String deviceName, @QueryParam("token") String token, @PathParam("id") int id, @PathParam("owner") String owner){
        if(!APIServer.getInstance().isConnected(deviceName, token)){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        UserChart uc = APIServer.getInstance().getDevice(owner).getUserContents().getCharts().get(id);
        if(uc == null || uc.isDeleted()){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        File file = new File(uc.getFileName());
        byte[] output = null;
        String mimeType = null;
        try {
            InputStream is = new BufferedInputStream(new FileInputStream(file));
            mimeType = URLConnection.guessContentTypeFromStream(is);
            output = IOUtils.toByteArray(is);
            is.close();
        } catch (IOException ex) {
            Logger.getLogger(UserContentsResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.OK).type(mimeType).entity(output).build();
    }
    
    @GET
    @Path("/timestamp")
    public Response getTimestamp(@QueryParam("deviceName") String deviceName, @QueryParam("token") String token){
        if(!APIServer.getInstance().isConnected(deviceName, token)){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        return Response.status(Response.Status.OK).type(MediaType.TEXT_PLAIN).entity(new Date().getTime()).build();
    }
    
    private String getFieldName(MultivaluedMap<String, String> header) {

        String[] contentDisposition = header.getFirst("Content-Disposition").split(";");

        for (String fieldName : contentDisposition) {
            if ((fieldName.trim().startsWith("name"))) {

                String[] name = fieldName.split("=");

                String finalFieldName = name[1].trim().replaceAll("\"", "");
                return finalFieldName;
            }
        }
        return null;
    }
    
    private String getFileExtension(MultivaluedMap<String, String> header) {

        String[] contentDisposition = header.getFirst("Content-Disposition").split(";");

        for (String filename : contentDisposition) {
            if ((filename.trim().startsWith("filename"))) {

                String[] name = filename.split("=");

                String finalFileName = name[1].trim().replaceAll("\"", "");
                
                String extension = finalFileName.substring(finalFileName.length()-3);
                return extension;
            }
        }
        return null;
    }
    
    private void writeFile(byte[] content, String filename) throws IOException {
 
		File file = new File(filename);
 
		if (!file.exists()) {
			file.createNewFile();
		}
 
		FileOutputStream fop = new FileOutputStream(file);
 
		fop.write(content);
		fop.flush();
		fop.close();
 
	}
}
