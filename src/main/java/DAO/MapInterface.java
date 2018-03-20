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
     * @param user
     * @return the list of map of the user
     */
    Map getMapById(String id);
    
    /**
     * @param user
     * @return the list of map of the user
     */
    ArrayList<Map> getMapsByUser(User user);
    
    /**
     * @param tokken
     * @return the list of map including "tokken"
     */
    ArrayList<Map> getMapsByName(String tokken);
    
    /**
     * @param username
     * @return the list of maps thanks to the username
     */
    ArrayList<Map> getMapsByUsername(String username);
    
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
    ArrayList<Map> getPublicMaps();
    
    /**
     * @param username
     * @return the list of public maps of the specific user
     */
    ArrayList<Map> getPublicMapsByUsername(String username);
    
    /**
     * @param user
     * @return the list of public maps of the specific user
     */
    ArrayList<Map> getPublicMapsByUser(User user);
    
    /**
     * 
     * @param user
     * @return the list of maps of the specific user's friends
     */
    ArrayList<Map> getFriendsMapByUser(User user);
    
    /**
     * 
     * @param username
     * @return the list of maps of the specific user's friends thanks to his name
     */
    ArrayList<Map> getFriendsMapByUsername(String username);
    
    /**
     * create a new Map
     * @param mapname
     * @return a new map with the specific name
     */
    Map createMap(String mapname);
    
    
    /**
     * 
     * @param map 
     */
    void deleteMap(long id);
    
    
    
}
