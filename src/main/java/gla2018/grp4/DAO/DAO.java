package gla2018.grp4.DAO;

import java.util.ArrayList;

public class DAO {

	public static UserDAO getActionUser(){
		return new UserDAO();
	}

	public static MapDAO getActionMap()
	{
		return new MapDAO();
	}

	public static LocationDAO getActionDAO()
	{
		return new LocationDAO();
	}
}
