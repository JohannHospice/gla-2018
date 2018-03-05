package gla2018.grp4.DAO;

import java.util.ArrayList;

public interface UserInterface {
    /**
	 *
     * @return list of users
     */
    ArrayList<User> getUsers();

    /**
	 *
     * @param user
     * @return list of user's friends
    */
    ArrayList<User> getFriends(User user);
}

