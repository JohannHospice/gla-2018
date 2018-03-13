package DAO;

import java.util.ArrayList;

public interface UserInterface {
    /**
     * cc
     * @return the list of user
     */
    ArrayList<User> getUsers();
    
    /**
     * @param username
     * @return the friendlist of the specific user
    */
    ArrayList<String> getFriends(String username);
    
    
    /**
     * 
     * @param username
     * @return all the user informations
     */
    User getInfoUser(String username);
    
    /**
     * 
     * 
     * @param username
     * @return all the user maps
     */
    ArrayList<Map> getMapsOfUser(String username);
}

