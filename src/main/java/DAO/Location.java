package DAO;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Location {
    public String id;//C'est l'id : //C'est l'id : username_nomdemap_location
    public String nameplace;
    public String mapName;
    public ArrayList<String> urlImg;
    public ArrayList<String> content;
    public String longitude;
    public String latitude;
    public boolean isFavorite = false;

    
    public void setId(String map,String nameplace) {
        this.id = map+"_"+nameplace;
    }

    public String getId() {
        return this.id;
    }
    
    public void setNameplace(String map,String nameplace) {
        this.nameplace = nameplace;
    }

    public String getNameplace() {
        return this.nameplace;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public String getMapName() {
        return this.mapName;
    }

    public void setUrlImg(ArrayList<String> urlImg) {
        this.urlImg = urlImg;
    }

    public ArrayList<String> getUrlImg() {
        return this.urlImg;
    }

    public void setContent(ArrayList<String> content) {
        this.content = content;
    }

    public ArrayList<String> getContent() {
        return this.content;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLatitude() {
        return this.latitude;
    }

    public void setIsFavorite() {
        isFavorite = true;
    }

    public boolean getIsFavorite() {
        return this.isFavorite;
    }

    /**
     * @return the url of the location's image
     */
    public ArrayList<String> getImage() {
        return this.urlImg;
    }

    /**
     * @param nameplace
     */
    public Location(String map,String nameplace) {
        this.id = map+"_"+nameplace;
        this.nameplace = nameplace;
        this.mapName = map;
        urlImg = new ArrayList<String>();
        content = new ArrayList<String>();
    }
    
    public Location() {
        urlImg = new ArrayList<String>();
        content = new ArrayList<String>();
    }
    
    public Location(String map,String nameplace, String latitude, String longitude, ArrayList<String> url, ArrayList<String> content) {
        this(map,nameplace);
        this.nameplace = nameplace;
        this.mapName = map;
        this.latitude = latitude;
        this.longitude = longitude;
        this.urlImg = url;
        this.content = content;
    }


    /**
     * change the location attribute
     */
    public void changeFavorite() {
        isFavorite = !isFavorite;
    }
}




