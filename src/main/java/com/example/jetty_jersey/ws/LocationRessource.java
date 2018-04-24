package com.example.jetty_jersey.ws;

import DAO.DAO;
import DAO.Location;
import DAO.User;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@Path("location")
public class LocationRessource extends Ressource {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/add")
    public boolean addLocation(@Context HttpServletRequest httpRequest,
                               @FormParam("mapname") String mapname,
                               @FormParam("locationname") String locationName,
                               @FormParam("latitude") float latitude,
                               @FormParam("longitude") float longitude) throws IOException {
        User user = getUserBySession(httpRequest);
        if (user.getMaps().contains(mapname)) {
            return DAO.getActionLocation().insertLocationAndUpdateMap(
                    DAO.client,
                    new Location(locationName, latitude, longitude),
                    mapname);
        }
        return false;
    }

    @POST
    @Path("/add")
    public boolean removeLocation(@Context HttpServletRequest httpRequest,
                                  @FormParam("mapname") String mapname,
                                  @FormParam("locationname") String locationName) throws IOException {
        User user = getUserBySession(httpRequest);
        //TODO
        return false;
    }
}
