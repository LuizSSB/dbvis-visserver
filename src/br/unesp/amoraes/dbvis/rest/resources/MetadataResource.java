/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unesp.amoraes.dbvis.rest.resources;

import br.unesp.amoraes.dbvis.internals.FunctionsHelper;
import br.unesp.amoraes.dbvis.rest.APIServer;
import br.unesp.amoraes.dbvis.rest.domain.Device;
import br.unesp.amoraes.dbvis.rest.domain.Metadata;
import br.unesp.amoraes.dbvis.rest.domain.UserText;
import br.unesp.amoraes.dbvis.viewer.graph.GraphViewer;
import java.awt.Component;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.DELETE;
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
@Path("/metadata")
public class MetadataResource {
    
    
    @GET
    @Path("")
    public Response get(@QueryParam("deviceName") String deviceName, @QueryParam("token") String token){
        if(!APIServer.getInstance().isConnected(deviceName, token)){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"+new Metadata(GraphViewer.getInstance().getSelectedData()).getXml();
        Logger.getLogger(FunctionsHelper.class.getName()).log(Level.INFO, "Getting metadata");
        return Response.status(Response.Status.OK).type(MediaType.TEXT_XML).entity(xml).build();
    }
    
    
}
