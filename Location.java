/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gla.groupe4;

import java.time.LocalDateTime;

/**
 *
 * @author andylms
 */
public class Location {
    private Map map;
    private String nameplace;
    private String urlImg;
    private String content;
    private LocalDateTime created;
    private boolean isFavorite=false;

    /**
     * 
     * @param map 
     */
    public Location(Map map){
        this.map = map;
        this.created = LocalDateTime.now();
    }   
    
    /**
     * 
     * @param map
     * @param nameplace 
     */
    public Location(Map map,String nameplace){
        this.map = map;
        this.nameplace = nameplace;
        this.created = LocalDateTime.now();
    }
    
    /**
     * 
     * @param map
     * @param nameplace
     * @param url 
     */
    public Location(Map map,String nameplace, String url){
        this.map = map;
        this.nameplace = nameplace;
        this.urlImg = url;
        this.created = LocalDateTime.now();
    }
    
    /**
     * 
     * @param map
     * @param nameplace
     * @param url
     * @param content 
     */
    public Location(Map map,String nameplace, String url, String content){
        this.map = map;
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



