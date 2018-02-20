/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gla.groupe4;

import java.util.ArrayList;

/**
 *
 * @author andylms
 */
public class User {
    private String mail;
    private String password;
    private String username;
    private ArrayList<User> friendList;
    private ArrayList<Map> mapList;
    
    /**
     * 
     * @param mail
     * @param password
     */
    public User(String mail,String password){
        this.mail = mail;
        this.password = password;
    }
    
    /**
     * 
     * @param username 
     */
    public void setUsername(String username){
        this.username = username;
    }
    
    /**
     * @param user
     */
    void addFriend(User user){
        this.friendList.add(user);
    }
    
    public ArrayList<Map> getMaps(){
        return this.mapList;
    }
    
    /**
     * @param user 
     */
    void deleteFriend(User user){
        this.friendList.remove(user);
    }
    
    @Override
    public String toString(){
        return ""+this.mail+""+this.username;
    }
    
    /**
     * add a new Map
     * @param map 
     */
    public void addMap(Map map){
        if(mapList.contains(map))
            return;
        this.mapList.add(map);
    }
    
    /**
     * 
     * @param map 
     */
    public void deleteMap(Map map){
        this.mapList.remove(map);
    }
}
