package DAO;

import java.time.LocalDateTime;
import java.util.ArrayList;

public interface MapInterface {
    /**
     * 
     * @return the list of all maps
     */
    ArrayList<Map> getMaps();    
    
    /**
     * 
     * @param date
     * @return the list of maps created after a specific date
     */
    ArrayList<Map> getMaps(LocalDateTime date);
    
    /**
     * 
     * @return the list of public maps
     */
    ArrayList<Map> getPublicMaps();
    
    /**
     * 
     * @param user
     * @return the list of maps of the specific user's friends
     */
    ArrayList<Map> getFriendsMap(User user);
    
    /**
     * create a new Map
     */
    void createMap();
    
    /**
     * 
     * @param map 
     */
    void deleteMap(Map map);
    
    
    
}
