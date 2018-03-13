package DAO;

import java.util.ArrayList;

public class User{
	public String mail;
	public String password;
	public String username;
	public ArrayList<String> friendList; //Liste d'username
	public ArrayList<String> mapList; // Liste de nom des maps (parce que y'a pas d'increment id)
    
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
    void addFriend(String user){
        this.friendList.add(user);
    }
    
    public ArrayList<String> getMaps(){
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
    public void addMap(String map){
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
