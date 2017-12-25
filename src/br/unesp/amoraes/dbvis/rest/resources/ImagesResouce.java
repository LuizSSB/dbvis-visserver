/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unesp.amoraes.dbvis.rest.resources;

import br.unesp.amoraes.dbvis.internals.CurrentRepresentation;
import br.unesp.amoraes.dbvis.rest.APIServer;
import br.unesp.amoraes.dbvis.rest.domain.Image;
import br.unesp.amoraes.dbvis.rest.domain.NodeData;
import br.unesp.amoraes.dbvis.viewer.graph.GraphViewer;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 *
 * @author alessandro
 */
@Path("/images")
public class ImagesResouce {
    
    private static final String FULL0 = "full0";
    private static final String FULL1 = "full1";
    private static final String FULL2 = "full2";
    
    /**
     * Rerturns current images
     */
    @GET
    public Response getAllXml(@QueryParam("deviceName") String name, @QueryParam("token") String token){
        if(!APIServer.getInstance().isConnected(name, token)){
            return null;
        }
        List<Image> images = new LinkedList<Image>();
        Image image = new Image();
        image.setTime(CurrentRepresentation.getInstance().getTime());
        image.setId(FULL0);
        image.setUrl("/images/"+FULL0+"/download");
        images.add(image);
        image = new Image();
        image.setTime(CurrentRepresentation.getInstance().getTime());
        image.setId(FULL1);
        image.setUrl("/images/"+FULL1+"/download");
        images.add(image);
        image = new Image();
        image.setTime(CurrentRepresentation.getInstance().getTime());
        image.setId(FULL2);
        image.setUrl("/images/"+FULL2+"/download");
        images.add(image);
        
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<collection>\n";
        for(Image i : images){
            xml += i.getXml();
        }        
        xml+="</collection>";
        return Response.status(Response.Status.OK).type(MediaType.TEXT_XML).entity(xml).build();
        
    }
    
    @GET
    @Path("/{id}/download")
    public Response getImageDownload(@QueryParam("deviceName") String name, @QueryParam("token") String token, @PathParam("id") String id){
        if(!APIServer.getInstance().isConnected(name, token)){
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            if(id.equals(FULL0)){
                ImageIO.write(CurrentRepresentation.getInstance().getImageFar(), "png", baos);
            }else if(id.equals(FULL1)){
                ImageIO.write(CurrentRepresentation.getInstance().getImage(), "png", baos);
            }else if(id.equals(FULL2)){
                ImageIO.write(CurrentRepresentation.getInstance().getImageClose(), "png", baos);
            }
        } catch (IOException ex) {
            Logger.getLogger(ImagesResouce.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Status.OK).type("image/png").entity(baos.toByteArray()).build();
        
    }
    
    
    
}
