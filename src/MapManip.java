/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author andylms
 */
public interface MapManip {
    /**
     * 
     * @return the list of all maps
     */
    ArrayList<Map> getMaps();    
    
    /**
     * 
     * @param user
     * @return the list of the specific user's maps
     */
    ArrayList<Map> getMaps(User user);
    
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
     * @return the list of public maps of a specific user
     */
    ArrayList<Map> getPublicMaps(User user);
    
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
