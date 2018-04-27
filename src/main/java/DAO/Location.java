package DAO;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Location {
    public String nameplace;//C'est l'id : //C'est l'id : username_nomdemap_location
    public String mapName;
    public ArrayList<String> urlImg;
    public ArrayList<String> content;
    public LocalDateTime created;
    public float longitude;
    public float latitude;
    public boolean isFavorite = false;


    public void setNameplace(String nameplace) {
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

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLongitude() {
        return this.longitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLatitude() {
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
    public Location(String nameplace) {
        this.nameplace = nameplace;
        this.created = LocalDateTime.now();
    }

    public Location(String nameplace, float latitude, float longitude) {
        this(nameplace);
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * @param nameplace
     * @param url
     */
    public Location(String nameplace, ArrayList<String> url) {
        this.nameplace = nameplace;
        this.urlImg = url;
        this.created = LocalDateTime.now();
    }

    /**
     * @param nameplace
     * @param url
     * @param content
     */
    public Location(String nameplace, ArrayList<String> url, ArrayList<String> content) {
        this.nameplace = nameplace;
        this.urlImg = url;
        this.content = content;
        this.created = LocalDateTime.now();
    }


    /**
     * change the location attribute
     */
    public void changeFavorite() {
        isFavorite = !isFavorite;
    }
}




