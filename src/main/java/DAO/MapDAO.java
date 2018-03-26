package DAO;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

/*
 * Index name : "maps"
 * 
 */

public class MapDAO implements MapInterface{

	public ArrayList<Map> getMaps(RestHighLevelClient client) throws IOException {
		ArrayList<Map> maps = new ArrayList<Map>();
		
		SearchRequest searchRequest = new SearchRequest("maps"); 
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder(); 
		searchSourceBuilder.query(QueryBuilders.matchAllQuery()); 
		searchRequest.source(searchSourceBuilder);
		
		SearchResponse searchResponse = client.search(searchRequest);
		SearchHits hits = searchResponse.getHits();
		long totalHits = hits.getTotalHits();
		float maxScore = hits.getMaxScore();
		System.out.println("total : "+totalHits+", maxScore : "+maxScore);
		
		SearchHit[] searchHits = hits.getHits();
		for (SearchHit hit : searchHits) {
		    // do something with the SearchHit
			//String index = hit.getId();
			
			String sourceAsString = hit.getSourceAsString();
			Map map = new ObjectMapper().readValue(sourceAsString, Map.class);
			
			maps.add(map);
		}
		
		return maps;
	}

	public Map getOneMap(RestHighLevelClient client, String name) throws IOException {
		GetRequest getRequest = new GetRequest(
		        "maps", 
		        "doc",  
		        name); 
		String sourceAsString = "";
		try {
			
			GetResponse getResponse = client.get(getRequest);

			if (getResponse.isExists()) {
			    sourceAsString = getResponse.getSourceAsString();        
			    System.out.println(sourceAsString+"\n");
			} else {
			    System.out.println("Impossible de trouver l'User "+name);
			    return null;
			}
		} catch (ElasticsearchException e) {
		    if (e.status() == RestStatus.NOT_FOUND) {
		        
		    }
		}

		return new ObjectMapper().readValue(sourceAsString, Map.class);
	}


	public ArrayList<Map> getMapsByTime(LocalDateTime date) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Map> getPublicMaps(RestHighLevelClient client) throws IOException{
		ArrayList<Map> maps = new ArrayList<Map>();
		
		SearchRequest searchRequest = new SearchRequest("maps"); 
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder(); 
		searchSourceBuilder.query(QueryBuilders.termQuery("isPublic", "true"));
		searchRequest.source(searchSourceBuilder);
		
		SearchResponse searchResponse = client.search(searchRequest);
		SearchHits hits = searchResponse.getHits();
		long totalHits = hits.getTotalHits();
		float maxScore = hits.getMaxScore();
		
		SearchHit[] searchHits = hits.getHits();
		for (SearchHit hit : searchHits) {
		    // do something with the SearchHit
			//String index = hit.getId();
			
			String sourceAsString = hit.getSourceAsString();
			Map map = new ObjectMapper().readValue(sourceAsString, Map.class);
			
			maps.add(map);
		}
		
		return maps;
	}

	public ArrayList<Map> getPublicMapsByUsername(RestHighLevelClient client, String username) throws IOException{
		ArrayList<Map> maps = new ArrayList<Map>();
		
		SearchRequest searchRequest = new SearchRequest("maps"); 
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder(); 
		searchSourceBuilder.query(QueryBuilders.termQuery("isPublic", "true"));
		searchRequest.source(searchSourceBuilder);
		
		SearchResponse searchResponse = client.search(searchRequest);
		SearchHits hits = searchResponse.getHits();
		long totalHits = hits.getTotalHits();
		float maxScore = hits.getMaxScore();
		
		SearchHit[] searchHits = hits.getHits();
		for (SearchHit hit : searchHits) {
		    // do something with the SearchHit
			//String index = hit.getId();
			
			String sourceAsString = hit.getSourceAsString();
			Map map = new ObjectMapper().readValue(sourceAsString, Map.class);
			if(compareNamesMapUser(map.name, username))
				maps.add(map);
		}
		
		return maps;
	}
	
	private boolean compareNamesMapUser(String map_name, String user_name)
	{
		boolean und = false;
		int c = 0;
		for(int i =0;i<map_name.length();i++)
		{
			if(und)
			{
				if(user_name.charAt(c) != map_name.charAt(i))
					return false;
			}
			
			if(map_name.charAt(i) == '_')
				und = true;
		}
		return true;
	}

	public ArrayList<ArrayList<Map>> getFriendsMapByUsername(RestHighLevelClient client, String username) throws IOException{
		ArrayList<String> friends = (new UserDAO()).getFriends(client, username);
		ArrayList<ArrayList<Map>> maps = new ArrayList<ArrayList<Map>>();
		for(String friend : friends)
		{
			maps.add(getPublicMapsByUsername(client, friend));
		}
		return maps;
	}

	public boolean createMap(RestHighLevelClient client, Map map) throws IOException{
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(map);
		
		java.util.Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("map", json);
		
		
		IndexRequest indexRequest = new IndexRequest("maps", "doc",map.name)
		        .source(jsonMap);
		
		try {
			IndexResponse indexResponse = client.index(indexRequest);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean deleteMap(RestHighLevelClient client, String map_name) throws IOException {
		DeleteRequest request = new DeleteRequest(
		        "maps",    
		        "doc",     
		        map_name);
		DeleteResponse deleteResponse = client.delete(request);
		ReplicationResponse.ShardInfo shardInfo = deleteResponse.getShardInfo();
		if (shardInfo.getFailed() > 0) {
		    for (ReplicationResponse.ShardInfo.Failure failure : shardInfo.getFailures()) {
		        String reason = failure.reason(); 
		        System.out.println(reason);
		        return false;
		    }
		}
		return true;		
	}

	

}