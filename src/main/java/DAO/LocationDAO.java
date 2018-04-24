package DAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class LocationDAO implements LocationInterface{

	public ArrayList<Location> getLocations(RestHighLevelClient client,  String map_name) throws IOException {
		ArrayList<Location> locations = new ArrayList<Location>();
		
		SearchRequest searchRequest = new SearchRequest("locations"); 
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder(); 
		searchSourceBuilder.query(QueryBuilders.termQuery("map_name", map_name)); 
		searchRequest.source(searchSourceBuilder);
		
		SearchResponse searchResponse = client.search(searchRequest);
		SearchHits hits = searchResponse.getHits();
		long totalHits = hits.getTotalHits();
		float maxScore = hits.getMaxScore();
		System.out.println("total : "+totalHits+", maxScore : "+maxScore);
		
		SearchHit[] searchHits = hits.getHits();
		for (SearchHit hit : searchHits) {
			String sourceAsString = hit.getSourceAsString();
			Location location= new ObjectMapper().readValue(sourceAsString, Location.class);
			
			locations.add(location);
		}
		
		return locations;

	}


	public boolean insertLocation(RestHighLevelClient client, Location location,Map map) throws IOException
	{	
		java.util.Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("nameplace", location.nameplace);
		jsonMap.put("mapName", location.mapName);
		jsonMap.put("urlImg", location.urlImg);
		jsonMap.put("content", location.content);
		jsonMap.put("created", location.created);
		jsonMap.put("longitude", location.longitude);
		jsonMap.put("latitude", location.latitude);
		jsonMap.put("isFavorite", location.isFavorite);
		
		IndexRequest indexRequest = new IndexRequest("locations", "doc",map.name)
		        .source(jsonMap);
		
		try {
			IndexResponse indexResponse = client.index(indexRequest);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean insertLocationAndUpdateMap(RestHighLevelClient client, Location location,String map_name) throws IOException
	{	
		java.util.Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("nameplace", location.nameplace);
		jsonMap.put("mapName", location.mapName);
		jsonMap.put("urlImg", location.urlImg);
		jsonMap.put("content", location.content);
		jsonMap.put("created", location.created);
		jsonMap.put("longitude", location.longitude);
		jsonMap.put("latitude", location.latitude);
		jsonMap.put("isFavorite", location.isFavorite);
		Map map = DAO.getActionMap().getOneMap(client, map_name);
		map.addLocation(location.nameplace);
		
		IndexRequest indexRequest = new IndexRequest("locations", "doc",location.nameplace)
		        .source(jsonMap);
		
		try {
			IndexResponse indexResponse = client.index(indexRequest);
			DAO.getActionMap().updateMap(client, map);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean updateLocation(RestHighLevelClient client, Location location) throws IOException{
		java.util.Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("urlImg", location.urlImg);
		jsonMap.put("content", location.content);
		jsonMap.put("isFavorite", location.isFavorite);
		UpdateRequest request = new UpdateRequest("maps", 
		        "doc",  
		        location.nameplace)
		        .doc(jsonMap);
		UpdateResponse updateResponse = client.update(request);
		request.docAsUpsert(false);
		
		if (updateResponse.getResult() == DocWriteResponse.Result.UPDATED) {
			System.out.println("La map "+location.nameplace+ " a été mis à jour");
		} else if (updateResponse.getResult() == DocWriteResponse.Result.DELETED) {
			System.out.println("La map "+location.nameplace+ " a été supprimé");
			return false;
		} else if (updateResponse.getResult() == DocWriteResponse.Result.NOOP) {
			System.out.println("La map "+location.nameplace+ " n'a pas pu être mis à jour");
			return false;
		}
		return true;
	}

	public void createIndexLocation() throws IOException {
		CreateIndexRequest request = new CreateIndexRequest("locations");
		
	}


}
