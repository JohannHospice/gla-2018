package DAO;

import java.util.ArrayList;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

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
	
	/*
	 * ip = "localhost" par défaut
	 * port = 9200 par défaut
	 * 
	 */
	public static RestHighLevelClient ClientConnection(String ip, int port)
	{
		RestHighLevelClient client = new RestHighLevelClient(
		        RestClient.builder(
		                new HttpHost(ip, port, "http")));
		
		return client;
	}
}
