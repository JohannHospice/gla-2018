/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;

/**
 *
 * @author andylms
 */
public interface UserManip {
    /**
     * @return the list of user
     */
    ArrayList<User> getUsers();
    
    /**
     * @param user
     * @return the friendlist of the specific user
    */
    ArrayList<User> getFriends(User user);
    
    
    /**
     * 
     * @param user
     * @return all the user informations in a String
     */
    String getInfoUser(User user);
   
}
