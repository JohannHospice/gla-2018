package DAO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;


public class Map {
    public String name="NoDefined"; //C'est l'id : username_nomdemap
    public String nameToSearch="";
    public ArrayList<String> locations;
    public LocalDateTime created;
    public ArrayList<String> privateUsers; //un username
    
    public boolean isPublic=true;
    public boolean isFavorite=false;
    
    /**
     * 
     * @param name 
     */
    public Map(String username,String name){
        this.name = username+"_"+name;
        this.created = LocalDateTime.now();
        locations = new ArrayList<String>();
        privateUsers = new ArrayList<String>();
    }
    
    /**
     * 
     */
    public Map(){
        this.created = LocalDateTime.now();
        locations = new ArrayList<String>();
        privateUsers = new ArrayList<String>();
    }
    
    /**
     * 
     * @param name 
     */
    public void setName(String name){
        this.name = name;
    }
    public String getName()
    {
    	return name;
    }
    
    public void setNameToSearch(String nameToSearch){
        this.nameToSearch = nameToSearch;
    }
    public String getNameToSearch()
    {
    	return nameToSearch;
    }
    
    /**
     * 
     */
    public void setIsFavorite(){
        isFavorite=true;
    }
    public boolean getIsFavorite()
    {
    	return isFavorite;
    }
    
    /**
     * 
     */
    public void setIsPublic(){
        isPublic=false;
    }
    public boolean getIsPublic()
    {
    	return isPublic;
    }
    
    /**
     * 
     */
    public void changeFavorite(){
        isFavorite=!isFavorite;
    }
    
    /**
     * 
     */
    public void changePublic(){
        isPublic=!isPublic;
    }
    
    /**
     * 
     * @param location 
     */
    public void addLocation(String idLocation){
        this.locations.add(idLocation);
    }
    
    /**
     * 
     * @param location 
     */
    public void removeLocation(String idLocation){
        this.locations.remove(idLocation);
    }
    public void setLocations(ArrayList<String> locations)
    {
    	this.locations = locations;
    }
    public ArrayList<String> getLocations()
    {
    	return this.locations;
    }
    public void setCreated(LocalDateTime created)
    {
    	this.created = created;
    }
    public LocalDateTime getCreated()
    {
    	return this.created;
    }
    public void setPrivateUsers(ArrayList<String> privateUsers)
    {
    	this.privateUsers = privateUsers;
    }
    public ArrayList<String> getPrivateUsers()
    {
    	return this.privateUsers;
    }
    
}













