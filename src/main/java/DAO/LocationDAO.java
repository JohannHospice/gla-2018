package DAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
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


	public boolean createLocation(RestHighLevelClient client, Location location,Map map) throws IOException
	{
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(map);
		
		java.util.Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("location", json);
		
		
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


}
