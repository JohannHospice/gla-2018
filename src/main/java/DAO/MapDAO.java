package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class MapDAO implements MapInterface{

	@Override
	public ArrayList<Map> getMaps() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map getMapById(long id) {
		// TODO Auto-generated method stub
		//return null;
		/**TEST **/
		Map map = new Map();
		return map;
	}

	@Override
	public ArrayList<Map> getMapsByUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Map> getMapsByName(String tokken) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Map> getMapsByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Map> getMapsByTime(LocalDateTime date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Map> getPublicMaps() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Map> getPublicMapsByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Map> getPublicMapsByUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Map> getFriendsMapByUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Map> getFriendsMapByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map createMap(String mapname) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteMap(long id) {
		// TODO Auto-generated method stub
		
	}

}