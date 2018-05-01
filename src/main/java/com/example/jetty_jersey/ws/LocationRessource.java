package com.example.jetty_jersey.ws;

import DAO.DAO;
import DAO.Location;
import DAO.Map;
import DAO.User;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.ArrayList;

@Path("/location")
public class LocationRessource extends Ressource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/get/by-mapId/{mapId}")
    public ArrayList<Location> getAllMapLocations(@Context HttpServletRequest httpRequest,
                                              @PathParam("mapId") String mapId) {   	   	
        try {
        	User user = getUserBySession(httpRequest);
        	Map map = DAO.getActionMap().getOneMap(DAO.client, mapId);
        	System.out.println("Map = "+map.getId());
        	if(map==null)
        		return null;
        	if (map.getIsPublic() || user.getMaps().contains(mapId) || map.getPrivateUsers().contains(user.getUsername()))
                {
            	System.out.println("GetAll location: ");
            	ArrayList<Location> locations = DAO.getActionLocation().getLocations(DAO.client, mapId);;
            	System.out.println("Nombre="+locations.size());
            	return locations;    
            	}
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/get/by-id/{locationId}")
    public Location getLocation(@Context HttpServletRequest httpRequest,
                                @PathParam("locationId") String locationId) {
        try {
            Location location = DAO.getActionLocation().getOneLocation(DAO.client, locationId);
            if(location==null)
            	return null;
            String mapName = location.getMapName();
            Map map = DAO.getActionMap().getOneMap(DAO.client, mapName);

            if (map.getIsPublic()) {
                return location;
            } else {
                try {
                    User user = getUserBySession(httpRequest);
                    if (user.getMaps().contains(mapName) || map.getPrivateUsers().contains(user.getUsername()))
                        return location;
                } catch (AuthException ignored) {
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/add")
    public Location addLocation(@Context HttpServletRequest httpRequest,
                               @FormParam("mapId") String mapId,
                               @FormParam("nameplace") String nameplace,
                               @FormParam("latitude") String latitude,
                               @FormParam("longitude") String longitude,
                               @FormParam("content") String content,
                               @FormParam("url") String url) {
        try {
        	Boolean success = false;
        	Location newLocation = null;
            User user = getUserBySession(httpRequest);
            ArrayList<String> urls = new ArrayList<String>();
            if(url!=null)
            	urls.add(url);
            ArrayList<String> contents = new ArrayList<String>();
            if(contents!=null)
            	contents.add(content);
            if (user.getMaps().contains(mapId)) {
            	newLocation = new Location(mapId, nameplace, latitude, longitude, urls, contents);
                success = DAO.getActionLocation().insertLocationAndUpdateMap(
                        DAO.client,newLocation,mapId);
                if(success)
                	return newLocation;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/remove")
    public boolean removeLocation(@Context HttpServletRequest httpRequest,
                                  @FormParam("mapId") String mapId,
                                  @FormParam("locationId") String locationId) {
        try {
            User user = getUserBySession(httpRequest);
            if (user.getMaps().contains(mapId)) {
                Map map = DAO.getActionMap().getOneMap(DAO.client, mapId);
                DAO.getActionLocation().deleteLocation(DAO.client, locationId);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/update")
    public Location updateLocation(@Context HttpServletRequest httpRequest,
                                  @FormParam("mapId") String mapId,
                                  @FormParam("locationId") String locationId,
                                  @FormParam("content") String content,
                                  @FormParam("latitude") String latitude,
                                  @FormParam("longitude") String longitude,
                                  @FormParam("url") String url) {
        try {
            User user = getUserBySession(httpRequest);
            if (user.getMaps().contains(mapId)) {
                Map map = DAO.getActionMap().getOneMap(DAO.client, mapId);
                if (map.getLocations().contains(locationId)) {
                    Location location = DAO.getActionLocation().getOneLocation(DAO.client, locationId);
                    if(latitude!=null)
                    	location.setLatitude(latitude);
                    if(longitude!=longitude)
                    	location.setLongitude(longitude);
                    if(content!=null)
                    	location.getContent().add(content);
                    if(url!=null)
                    	location.getUrlImg().add(url);
                    DAO.getActionLocation().updateLocation(DAO.client, location);
                    return location;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
