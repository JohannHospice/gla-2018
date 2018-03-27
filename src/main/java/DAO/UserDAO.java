package DAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
/*
 * Index name : "users"
 * 
 */
public class UserDAO implements UserInterface{

	public ArrayList<User> getUsers(RestHighLevelClient client) throws IOException {
		ArrayList<User> users = new ArrayList<User>();
		
		SearchRequest searchRequest = new SearchRequest("users"); 
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
			User user = new ObjectMapper().readValue(sourceAsString, User.class);
			
			users.add(user);
		}
		
		return users;
		
	}
	
	public ArrayList<User> getUsers(RestHighLevelClient client, int from, int size) throws IOException {
		ArrayList<User> users = new ArrayList<User>();
		
		SearchRequest searchRequest = new SearchRequest("users"); 
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder(); 
		searchSourceBuilder.query(QueryBuilders.matchAllQuery()); 
		searchSourceBuilder.from(from); 
		searchSourceBuilder.size(size);
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
			User user = new ObjectMapper().readValue(sourceAsString, User.class);
			
			users.add(user);
		}
		
		return users;
		
	}

	
	public User getOneUser(RestHighLevelClient client, String username) throws IOException{
		GetRequest getRequest = new GetRequest(
		        "users", 
		        "doc",  
		        username); 
		String sourceAsString = "";
		try {
			
			GetResponse getResponse = client.get(getRequest);

			if (getResponse.isExists()) {
			    sourceAsString = getResponse.getSourceAsString();        
			    System.out.println(sourceAsString+"\n");
			} else {
			    System.out.println("Impossible de trouver l'User "+username);
			    return null;
			}
		} catch (ElasticsearchException e) {
		    if (e.status() == RestStatus.NOT_FOUND) {
		        
		    }
		}

		return new ObjectMapper().readValue(sourceAsString, User.class);
	}
	
	public ArrayList<User> getFriends(RestHighLevelClient client, String username) throws IOException{
		ArrayList<String> friends_name = getOneUser(client,username).friendList;
		ArrayList<User> user = new ArrayList<User>();
		for(String name: friends_name)
		{
			
			user.add(getOneUser(client, name));
		}
		
		return user;
	}


	public ArrayList<Map> getMapsOfUser(RestHighLevelClient client, String username) throws IOException{
		ArrayList<String> maps_name = getOneUser(client,username).mapList;
		ArrayList<Map> maps = new ArrayList<Map>();
		for(String name: maps_name)
		{
			maps.add(DAO.getActionMap().getOneMap(client, name));
		}
		return maps;
	}
	
	public boolean insertUser(RestHighLevelClient client, User user) throws IOException
	{
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(user);
		java.util.Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("username", json);
		
		IndexRequest indexRequest = new IndexRequest("users", "doc",user.username)
		        .source(jsonMap);
		try {
			IndexResponse indexResponse = client.index(indexRequest);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean updateUser(RestHighLevelClient client, User user) throws IOException
	{	
		java.util.Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("mail", user.mail);
		jsonMap.put("friendList", user.friendList);
		jsonMap.put("mapList", user.mapList);
		UpdateRequest request = new UpdateRequest("users", 
		        "doc",  
		        user.username)
		        .doc(jsonMap);
		UpdateResponse updateResponse = client.update(request);
		request.docAsUpsert(false);
		
		if (updateResponse.getResult() == DocWriteResponse.Result.UPDATED) {
			System.out.println("l'utilisateur "+user.username+ " a été mis à jour");
		} else if (updateResponse.getResult() == DocWriteResponse.Result.DELETED) {
			System.out.println("l'utilisateur "+user.username+ " a été supprimé");
			return false;
		} else if (updateResponse.getResult() == DocWriteResponse.Result.NOOP) {
			System.out.println("l'utilisateur "+user.username+ " n'a pas pu être mis à jour");
			return false;
		}
		
		return true;
	}

	public ArrayList<User> searchUser(RestHighLevelClient client, String username, int from, int size) throws IOException {
		ArrayList<User> users = new ArrayList<User>();
		
		SearchRequest searchRequest = new SearchRequest("users"); 
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder(); 
		MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("username", username);
		matchQueryBuilder.fuzziness(Fuzziness.AUTO); //Pour chercher un username proche
		searchSourceBuilder.query(matchQueryBuilder); 
		searchSourceBuilder.from(from); 
		searchSourceBuilder.size(size);
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
			User user = new ObjectMapper().readValue(sourceAsString, User.class);
			
			users.add(user);
		}
		
		return users;
	}

}
