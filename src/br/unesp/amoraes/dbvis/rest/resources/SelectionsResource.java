/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unesp.amoraes.dbvis.rest.resources;

import br.unesp.amoraes.dbvis.algorithm.graph.Node;
import br.unesp.amoraes.dbvis.internals.FunctionsHelper;
import br.unesp.amoraes.dbvis.rest.APIServer;
import br.unesp.amoraes.dbvis.rest.domain.NodeData;
import br.unesp.amoraes.dbvis.viewer.graph.GraphViewer;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Alessandro Moraes
 */
@Path("/selections")
public class SelectionsResource {
    
    public Component screenComponent = GraphViewer.getInstance().getVisualizationViewer();
    
    @POST
    @Path("/point")
    public Response selectPoint(@QueryParam("deviceName") String deviceName, @QueryParam("token") String token, @FormParam("x") int x, @FormParam("y") int y){
        if(!APIServer.getInstance().isConnected(deviceName, token)){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        screenComponent.dispatchEvent(new MouseEvent(screenComponent, MouseEvent.MOUSE_PRESSED, 1, 0, x, y, 1, false, MouseEvent.BUTTON1));
        screenComponent.dispatchEvent(new MouseEvent(screenComponent, MouseEvent.MOUSE_RELEASED, 1, 0, x, y, 1, false, MouseEvent.BUTTON1));
        screenComponent.dispatchEvent(new MouseEvent(screenComponent, MouseEvent.MOUSE_CLICKED, 1, 0, x, y, 1, false, MouseEvent.BUTTON1));
        Logger.getLogger(FunctionsHelper.class.getName()).log(Level.INFO, "Select: "+x+","+y);
        return Response.status(Response.Status.OK).type(MediaType.TEXT_PLAIN).entity("").build();
    }
    
    @POST
    @Path("/area")
    public Response selectArea(@QueryParam("deviceName") String deviceName, @QueryParam("token") String token, @FormParam("x") int x, @FormParam("y") int y, @FormParam("width") int width, @FormParam("height") int height){
        if(!APIServer.getInstance().isConnected(deviceName, token)){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        screenComponent.dispatchEvent(new MouseEvent(screenComponent, MouseEvent.MOUSE_PRESSED, 1, 0, x, y, 1, false, MouseEvent.BUTTON1));
        GraphViewer.getInstance().showAreaSelection(x, y, width, height);
        screenComponent.dispatchEvent(new MouseEvent(screenComponent, MouseEvent.MOUSE_RELEASED, 1, 0, x+width, y+height, 1, false, MouseEvent.BUTTON1));
        
        Logger.getLogger(FunctionsHelper.class.getName()).log(Level.INFO, "Select area: "+x+","+y+" to "+(x+width)+","+(y+height));
        return Response.status(Response.Status.OK).type(MediaType.TEXT_PLAIN).entity("").build();
    }
    
    @POST
    @Path("/node")
    public Response selectNode(@QueryParam("deviceName") String deviceName, @QueryParam("token") String token, @FormParam("nodeId") String nodeId){
        if(!APIServer.getInstance().isConnected(deviceName, token)){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        
        Node n = GraphViewer.getInstance().getNodes().get(nodeId);
        
        int x = new Long(Math.round(GraphViewer.getInstance().getLayout().getX(n))).intValue();
        int y = new Long(Math.round(GraphViewer.getInstance().getLayout().getY(n))).intValue();
        screenComponent.dispatchEvent(new MouseEvent(screenComponent, MouseEvent.MOUSE_PRESSED, 1, 0, x, y, 1, false, MouseEvent.BUTTON1));
        screenComponent.dispatchEvent(new MouseEvent(screenComponent, MouseEvent.MOUSE_RELEASED, 1, 0, x, y, 1, false, MouseEvent.BUTTON1));
        screenComponent.dispatchEvent(new MouseEvent(screenComponent, MouseEvent.MOUSE_CLICKED, 1, 0, x, y, 1, false, MouseEvent.BUTTON1));
        Logger.getLogger(FunctionsHelper.class.getName()).log(Level.INFO, "Select node: "+nodeId+" ("+x+","+y+")");
        return Response.status(Response.Status.OK).type(MediaType.TEXT_PLAIN).entity("").build();
    }
    
       
    @DELETE
    @Path("/")
    public Response unselectAll(@QueryParam("deviceName") String deviceName, @QueryParam("token") String token){
        if(!APIServer.getInstance().isConnected(deviceName, token)){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        int x = -1000, y = -1000;
        screenComponent.dispatchEvent(new MouseEvent(screenComponent, MouseEvent.MOUSE_PRESSED, 1, 0, x, y, 1, false, MouseEvent.BUTTON1));
        screenComponent.dispatchEvent(new MouseEvent(screenComponent, MouseEvent.MOUSE_RELEASED, 1, 0, x, y, 1, false, MouseEvent.BUTTON1));
        screenComponent.dispatchEvent(new MouseEvent(screenComponent, MouseEvent.MOUSE_CLICKED, 1, 0, x, y, 1, false, MouseEvent.BUTTON1));
        Logger.getLogger(FunctionsHelper.class.getName()).log(Level.INFO, "Unselect all nodes");
        return Response.status(Response.Status.OK).type(MediaType.TEXT_PLAIN).entity("").build();
    }
    
    
    @GET
    @Path("/")
    public Response getSelectedNodes(@QueryParam("deviceName") String deviceName, @QueryParam("token") String token){
        if(!APIServer.getInstance().isConnected(deviceName, token)){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        
        String nodes = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<collection>";
        for(Node n : GraphViewer.getInstance().getNodes().values()){
            if(n.isSelected()){
                nodes += new NodeData(n, GraphViewer.getInstance().getSelectedData(), GraphViewer.getInstance().getIdColumn(), GraphViewer.getInstance().getLabelColumn()).getXml();
            }
        }
        nodes += "</collection>";
        return Response.status(Response.Status.OK).type(MediaType.TEXT_XML).entity(nodes).build();
    }
    
}
