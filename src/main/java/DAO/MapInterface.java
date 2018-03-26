package DAO;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.elasticsearch.client.RestHighLevelClient;

public interface MapInterface {
    /**
     * 
     * @return the list of all maps
     */
	ArrayList<Map> getMaps(RestHighLevelClient client) throws IOException;
    
    /**
     * @param user
     * @return the list of map of the user
     */
    Map getOneMap(RestHighLevelClient client, String name) throws IOException ;
    
    
    /**
     * 
     * @param date
     * @return the list of maps created after a specific date
     */
    ArrayList<Map> getMapsByTime(LocalDateTime date);
    
    /**
     * 
     * @return the list of public maps
     */
    ArrayList<Map> getPublicMaps(RestHighLevelClient client) throws IOException;
    
    /**
     * @param username
     * @return the list of public maps of the specific user
     */
    ArrayList<Map> getPublicMapsByUsername(RestHighLevelClient client, String username) throws IOException;
    
    /**
     * 
     * @param username
     * @return the list of maps of the specific user's friends, one list per friend
     */
    ArrayList<ArrayList<Map>> getFriendsMapByUsername(RestHighLevelClient client, String username) throws IOException;
    
    /**
     * create a new Map
     * @param mapname
     * @return true if the request succeded, else false
     */
    boolean createMap(RestHighLevelClient client, Map map) throws IOException;
    
    
    /**
     * 
     * @param map 
     * @return true if the request succeded, else false
     */
    boolean deleteMap(RestHighLevelClient client, String map_name) throws IOException;
    
    
    
}
