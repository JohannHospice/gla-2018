package DAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.elasticsearch.client.RestHighLevelClient;

public interface LocationInterface {
	/*
	 * Create index locations
	 * 
	 */
	void createIndexLocation() throws IOException;
	
    /**
     * 
     * @return the list of all locations 
     */
    ArrayList<Location> getLocations(RestHighLevelClient client, String map_name) throws IOException;
       
     
    /**
     * 
     * @param map, client
     * @return true if the location has been inserted into the DDB
     */
    boolean insertLocation(RestHighLevelClient client, Location location, Map map) throws IOException;
    
    boolean updateLocation(RestHighLevelClient client, Location location) throws IOException;
    
}
