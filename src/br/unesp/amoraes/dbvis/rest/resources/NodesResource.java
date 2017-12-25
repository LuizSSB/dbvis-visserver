/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unesp.amoraes.dbvis.rest.resources;

import br.unesp.amoraes.dbvis.algorithm.graph.Edge;
import br.unesp.amoraes.dbvis.algorithm.graph.Node;
import br.unesp.amoraes.dbvis.internals.FunctionsHelper;
import br.unesp.amoraes.dbvis.rest.APIServer;
import br.unesp.amoraes.dbvis.rest.domain.NodeData;
import br.unesp.amoraes.dbvis.viewer.graph.GraphViewer;
import edu.uci.ics.jung.algorithms.layout.GraphElementAccessor;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import java.awt.Rectangle;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Alessandro Moraes
 */
@Path("/nodes")
public class NodesResource {
    
    public VisualizationViewer<Node,Edge> vv = GraphViewer.getInstance().getVisualizationViewer();
    
    @GET
    @Path("/point")
    public Response nodeInPoint(@QueryParam("deviceName") String deviceName, @QueryParam("token") String token, @QueryParam("x") int x, @QueryParam("y") int y){
        if(!APIServer.getInstance().isConnected(deviceName, token)){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        Logger.getLogger(FunctionsHelper.class.getName()).log(Level.INFO, "Node in: "+x+","+y);
        GraphElementAccessor<Node, Edge> pickSupport = vv.getPickSupport();
        Node n = pickSupport.getVertex(GraphViewer.getInstance().getLayout(), x, y);
        String node = "";
        if(n != null){
            node = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
            node += new NodeData(n, GraphViewer.getInstance().getSelectedData(), GraphViewer.getInstance().getIdColumn(), GraphViewer.getInstance().getLabelColumn()).getXml();
        }        
        return Response.status(Response.Status.OK).type(MediaType.TEXT_XML).entity(node).build();
    }
    
    @GET
    @Path("/area")
    public Response nodesInArea(@QueryParam("deviceName") String deviceName, @QueryParam("token") String token, @QueryParam("x") int x, @QueryParam("y") int y, @QueryParam("width") int width, @QueryParam("height") int height){
        if(!APIServer.getInstance().isConnected(deviceName, token)){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        Logger.getLogger(FunctionsHelper.class.getName()).log(Level.INFO, "Nodes in area: "+x+","+y+" to "+(x+width)+","+(y+height));
        GraphElementAccessor<Node, Edge> pickSupport = vv.getPickSupport();
        Rectangle area = new Rectangle(x, y, width, height);
        Collection<Node> nodes = pickSupport.getVertices(GraphViewer.getInstance().getLayout(),area);
        String result = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<collection>\n";
        for(Node n : nodes){
           result += new NodeData(n, GraphViewer.getInstance().getSelectedData(), GraphViewer.getInstance().getIdColumn(), GraphViewer.getInstance().getLabelColumn()).getXml()+"\n";  
        }
        result += "</collection>";
        return Response.status(Response.Status.OK).type(MediaType.TEXT_XML).entity(result).build();
    }
    
    @GET
    @Path("/{nodeId}")
    public Response node(@QueryParam("deviceName") String deviceName, @QueryParam("token") String token, @PathParam("nodeId") String nodeId){
        if(!APIServer.getInstance().isConnected(deviceName, token)){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        Node n = GraphViewer.getInstance().getNodes().get(nodeId);
        String node = "";
        if(n != null){
            node = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
            node += new NodeData(n, GraphViewer.getInstance().getSelectedData(), GraphViewer.getInstance().getIdColumn(), GraphViewer.getInstance().getLabelColumn()).getXml();
        }        
        return Response.status(Response.Status.OK).type(MediaType.TEXT_XML).entity(node).build();
    }
    
       
}
