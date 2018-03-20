package DAO;

import java.time.LocalDateTime;

public class Location {
    public String nameplace;
    public String urlImg;
    public String content;
    public LocalDateTime created;
    public boolean isFavorite=false;
    
    
    /**
     * 
     * @param map 
     */
    /*public Location(Map map){
        this.map = map;
        this.created = LocalDateTime.now();
    }   
    */
    
    /**
     * 
     * @param nameplace 
     */
    public Location(String nameplace){
        this.nameplace = nameplace;
        this.created = LocalDateTime.now();
    }
    
    /**
     * 
     * @param nameplace
     * @param url 
     */
    public Location(String nameplace, String url){
        this.nameplace = nameplace;
        this.urlImg = url;
        this.created = LocalDateTime.now();
    }
    
    /**
     * 
     * @param nameplace
     * @param url
     * @param content 
     */
    public Location(String nameplace, String url, String content){
        this.nameplace = nameplace;
        this.urlImg = url;
        this.content = content;
        this.created = LocalDateTime.now();
    }
   
    /**
     * 
     * @param content 
     */
    public void setContent(String content){
        this.content = content;
    }
    
    /**
     * 
     * @param name 
     */
    public void setNamePlace(String name){
        this.nameplace= name;
    }
    
    /**
     * 
     * @param url 
     */
    public void setImage(String url){
        this.urlImg = url;
    }
    
    /**
     * 
     * @return the content of the locations
     */
    public String getContent(){
        return this.content;
    }
    
    /**
     * 
     * @return the name of location
     */
    public String getNamePlace(){
        return this.nameplace;
    }
    
    /**
     * 
     * @return the url of the location's image 
     */
    public String getImage(){
        return this.urlImg;
    }
    
    /**
     * 
     */
    public void setFavorite(){
        isFavorite = true;
    }
    
    /**
     * change the location attribute
     */
    public void changeFavorite(){
        isFavorite =! isFavorite;
    }
}




