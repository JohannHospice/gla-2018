package gla2018.grp4.DAO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;


public class Map {
    public String name;
    public User user;
    public ArrayList<Location> locations;
    public LocalDateTime created;
    public ArrayList<User> users;
    public boolean published;
    public boolean favorite;
}
