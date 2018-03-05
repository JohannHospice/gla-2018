package gla2018.grp4.DAO;

import java.util.ArrayList;

public class DAO {

	public static UserDAO getActionUser(){
		return new UserDAODummy();
	}

	public static MapDAO getActionMap()
	{
		return new MapDAODummy();
	}

	public static LocationDAO getActionDAO()
	{
		return new LocationDAODummy();
	}
}
