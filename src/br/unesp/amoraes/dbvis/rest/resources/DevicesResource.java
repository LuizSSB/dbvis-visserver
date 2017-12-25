/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unesp.amoraes.dbvis.rest.resources;

import br.unesp.amoraes.dbvis.rest.APIServer;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author alessandro
 */
@Path("/devices")
public class DevicesResource {
    
    @POST
    @Path("/")
    public Response connect(@FormParam("deviceName") String deviceName, @FormParam("password") String password){
        if(password.equals(APIServer.getInstance().getPassword())){
            String token = APIServer.getInstance().connectDevice(deviceName);
            return Response.status(Response.Status.OK).type(MediaType.TEXT_PLAIN).entity(token).build();
        }else{
            return Response.status(Response.Status.FORBIDDEN).type(MediaType.TEXT_PLAIN).entity("").build();
        }
    }
    
    @DELETE
    @Path("/")
    public Response disconnect(@QueryParam("deviceName") String deviceName, @QueryParam("token") String token){
        if(!APIServer.getInstance().isConnected(deviceName, token)){
            return Response.status(Response.Status.FORBIDDEN).type(MediaType.TEXT_PLAIN).entity("").build();    
        }
        APIServer.getInstance().disconnectDevice(deviceName);
        return Response.status(Response.Status.OK).type(MediaType.TEXT_PLAIN).entity("").build();
    }
    
    @PUT
    @Path("/")
    public Response updatePosition(@QueryParam("deviceName") String deviceName, @QueryParam("token") String token,
    @FormParam("left") int left, @FormParam("top") int top, @FormParam("right") int right, @FormParam("bottom") int bottom){
        if(!APIServer.getInstance().isConnected(deviceName, token)){
            return Response.status(Response.Status.FORBIDDEN).type(MediaType.TEXT_PLAIN).entity("").build();    
        }else{
            APIServer.getInstance().updatePosition(deviceName,left,top,right,bottom);
            return Response.status(Response.Status.OK).type(MediaType.TEXT_PLAIN).entity("").build();
        }
    }
    
}
