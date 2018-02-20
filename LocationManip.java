/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gla.groupe4;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author andylms
 */
public interface LocationManip {
    /**
     * 
     * @return the list of all locations 
     */
    ArrayList<Location> getLocations();
    
    /**
     * 
     * @param map
     * @return the list of locations of the specific map
     */
    ArrayList<Location> getLocations(Map map);
    
    /**
     * 
     * @param user
     * @return the list of locations created by the user
     */
    ArrayList<Location> getLocations(User user);
    
    /** 
     * @param date
     * @return the list of locations created after the date
     */
    ArrayList<Location> getDatedLocations(Date date);
    
    /** 
     * @param date
     * @param map
     * @return the list of locations created after the date on a specific map
     */
    ArrayList<Location> getDatedLocations(Date date, Map map);
    
    
    /**
     * 
     * @return the list of all favorite locations
     */
    ArrayList<Location> getFavoriteLocations();
    
    /**
     * 
     * @param map
     * @return the list of favorite locations on the map
     */
    ArrayList<Location> getFavoriteLocations(Map map);
    
}
