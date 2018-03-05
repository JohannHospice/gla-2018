package gla2018.grp4.DAO;

import java.util.ArrayList;

public interface UserInterface {
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

