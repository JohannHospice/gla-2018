package gla2018.grp4.DAO;

import java.util.ArrayList;
import java.util.Date;

public interface LocationInterface {
    /**
     *
     * @return list of all locations
     */
    ArrayList<Location> getLocations();

    /**
     *
     * @param map
     * @return list of locations of a specific map
     */
    ArrayList<Location> getLocations(Map map);

    /**
     *
     * @param map
     * @param user
     * @return list of locations created by a user
     */
    ArrayList<Location> getUserLocations(Map map, User user);

    /**
     * @param date
     * @param map
     * @return list of locations created after a date on a specific map
     */
    ArrayList<Location> getRecentLocations(Map map, Date date);

    /**
     *
     * @param map
     * @return list of favorite locations on a map
     */
    ArrayList<Location> getUserFavoriteLocations(Map map, User user);
}
