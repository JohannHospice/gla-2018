package com.example.jetty_jersey;

import java.io.IOException;
import java.net.InetAddress;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.open.OpenIndexRequest;
import org.elasticsearch.action.admin.indices.open.OpenIndexResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.*;
import org.elasticsearch.client.transport.*;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;

import DAO.Location;
import DAO.User;


public class ElasticTest {
	public static void main(String args[]) throws IOException
	{
		
		RestHighLevelClient client = new RestHighLevelClient(
		        RestClient.builder(
		                new HttpHost("localhost", 9200, "http"),
		                new HttpHost("localhost", 9201, "http")));
		
		/*CreateIndexRequest request = new CreateIndexRequest("twitter");
		System.out.println("ah");
		request.mapping("tweet", 
			    "  {\n" +
			    "    \"tweet\": {\n" +
			    "      \"properties\": {\n" +
			    "        \"message\": {\n" +
			    "          \"type\": \"text\"\n" +
			    "        }\n" +
			    "      }\n" +
			    "    }\n" +
			    "  }", 
			    XContentType.JSON);*/
		CreateIndexRequest create_request = new CreateIndexRequest("user");
		OpenIndexRequest request = new OpenIndexRequest("user");
		//OpenIndexResponse openIndexResponse = client.indices().open(request);
		//boolean acknowledged = openIndexResponse.isAcknowledged();
		//System.out.println(acknowledged);
		
		// INDEX REQUEST_______________________________________
		/*
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("user", "kimchy");
		jsonMap.put("postDate", new Date(0));
		jsonMap.put("message", "trying out Elasticsearch");
		IndexRequest indexRequest = new IndexRequest("posts", "doc", "1")
		        .source(jsonMap);
		
		IndexResponse indexResponse = client.index(indexRequest);*/
		
		// UPDATE_______________________________________
		
		/*Map<String, Object> jsonMap_update = new HashMap<String, Object>();
		jsonMap_update.put("message", "update !!!!cool");
		UpdateRequest request_update = new UpdateRequest("posts", "doc", "1")
		        .doc(jsonMap_update);
		
		UpdateResponse updateResponse = client.update(request_update);*/
		
		
		User user2 = new User("joe@gmail.com", "123");
		user2.username = "joe";
		User user3 = new User("oh@gmail.com", "azae");
		user3.username = "ah";
		ArrayList<String> f = new ArrayList<String>();
		ArrayList<String> f2 = new ArrayList<String>();
		f.add(user3.username);
		f.add(user2.username);
		f2.add(user2.username);
		
		User user = new User("jean@gmail.com", "prout");
		user.username = "jean";
		user.friendList = f;
		user.mapList = new ArrayList<String>();
		user2.mapList = new ArrayList<String>();
		user3.mapList = new ArrayList<String>();
		user2.friendList =  new ArrayList<String>();
		user3.friendList = f2;                                                            
		
		InsertUser(user, client);
		
		// GET _________________________________________
		
		GetRequest getRequest = new GetRequest(
		        "posts", 
		        "doc",  
		        "2"); 
		
		try {
			
			GetResponse getResponse = client.get(getRequest);
			String index = getResponse.getIndex();
			String type = getResponse.getType();
			String id = getResponse.getId();
			if (getResponse.isExists()) {
			    long version = getResponse.getVersion();
			    String sourceAsString = getResponse.getSourceAsString();        
			    Map<String, Object> sourceAsMap = getResponse.getSourceAsMap(); 
			    byte[] sourceAsBytes = getResponse.getSourceAsBytes();    
			    System.out.println(sourceAsString+"\n"+sourceAsMap.toString());
			} else {
			    
			}
		} catch (ElasticsearchException e) {
		    if (e.status() == RestStatus.NOT_FOUND) {
		        
		    }
		}
		
		client.close();
	
		
	}
	
	
	
	
	public static void InsertUser(User user, RestHighLevelClient client)
	{
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("username", user.username);
		jsonMap.put("password", user.password);
		jsonMap.put("mail", user.mail);
		jsonMap.put("friends", user.friendList);
		jsonMap.put("maps", user.mapList);
		
		
		IndexRequest indexRequest = new IndexRequest("posts", "doc")
		        .source(jsonMap);
		
		try {
			IndexResponse indexResponse = client.index(indexRequest);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void InsertMap(DAO.Map map, RestHighLevelClient client)
	{
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		//jsonMap.put("username", user.username);
		
		IndexRequest indexRequest = new IndexRequest("posts", "doc", "2")
		        .source(jsonMap);
		
		try {
			IndexResponse indexResponse = client.index(indexRequest);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void InsertLocation(Location location, RestHighLevelClient client)
	{
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		//jsonMap.put("username", user.username);
		
		IndexRequest indexRequest = new IndexRequest("posts", "doc", "2")
		        .source(jsonMap);
		
		try {
			IndexResponse indexResponse = client.index(indexRequest);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
