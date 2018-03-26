package DAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.elasticsearch.client.RestHighLevelClient;

public interface LocationInterface {
    /**
     * 
     * @return the list of all locations 
     */
    ArrayList<Location> getLocations(RestHighLevelClient client, String map_name) throws IOException;
    
    /** 
     * @param date
     * @return the list of locations created after the date
     */
    ArrayList<Location> getDatedLocations(RestHighLevelClient client, Date date) throws IOException;
    
    /** 
     * @param date
     * @param map
     * @return the list of locations created after the date on a specific map
     */
    ArrayList<Location> getDatedLocations(RestHighLevelClient client, Date date, Map map) throws IOException;
    
    
    /**
     * 
     * @return the list of all favorite locations
     */
    ArrayList<Location> getFavoriteLocations(RestHighLevelClient client) throws IOException;
    
    /**
     * 
     * @param map
     * @return the list of favorite locations on the map
     */
    ArrayList<Location> getFavoriteLocations(RestHighLevelClient client, Location location, Map map) throws IOException;
    
    /**
     * 
     * @param map, client
     * @return true if the location has been inserted into the DDB
     */
    boolean createLocation(RestHighLevelClient client, Location location, Map map) throws IOException;
    
}
