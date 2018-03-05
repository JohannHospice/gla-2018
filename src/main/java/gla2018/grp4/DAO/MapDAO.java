package gla2018.grp4.DAO;

import java.time.LocalDateTime;
import java.util.ArrayList;

public interface MapInterface {
    /**
     *
     * @return list of all maps
     */
    ArrayList<Map> getMaps();

    /**
     *
     * @param user
     * @return list of maps from a user
     */
    ArrayList<Map> getMaps(User user);

    /**
     *
     * @param date
     * @return list of maps created after a specific date
     */
    ArrayList<Map> getMaps(LocalDateTime date);

    /**
     *
     * @return list of public maps
     */
    ArrayList<Map> getPublicMaps();

    /**
     *
     * @return list of public maps from a user
     */
    ArrayList<Map> getPublicMaps(User user);

    /**
     *
     * @param user
     * @return list of maps from user's friends
     */
    ArrayList<Map> getFriendMaps(User user);

    /** TODO
	 *
     * create a new Map
     */
    void createMap();

    /**
     *
     * @param map
     */
    void deleteMap(Map map);
}
