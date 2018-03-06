package com.example.jetty_jersey;

import java.io.IOException;
import java.net.InetAddress;
import java.sql.Date;
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


public class ElasticTest {
	public static void main(String args[]) throws IOException
	{
		/*RestClient restClient = RestClient.builder(
		        new HttpHost("localhost", 9200, "http"),
		        new HttpHost("localhost", 9201, "http")).build();
		
		RestClientBuilder builder = RestClient.builder(new HttpHost("localhost", 9200, "http"));
		Header[] defaultHeaders = new Header[]{new BasicHeader("header", "value")};
		builder.setDefaultHeaders(defaultHeaders);
		
		
		ResponseListener responseListener = new ResponseListener() {
		    public void onSuccess(Response response) {
		        System.out.println("success");
		    }

		    public void onFailure(Exception exception) {
		    	System.out.println("failure");
		    }
		};
		
		Response response = restClient.performRequest("GET", "/"); 
	
		
		
		
		restClient.close();
		*/
		RestHighLevelClient client = new RestHighLevelClient(
		        RestClient.builder(
		                new HttpHost("localhost", 9200, "http"),
		                new HttpHost("localhost", 9201, "http")));
		OpenIndexRequest request = new OpenIndexRequest("twitter");
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
		OpenIndexResponse openIndexResponse = client.indices().open(request);
		boolean acknowledged = openIndexResponse.isAcknowledged();
		System.out.println(acknowledged);
		
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
		
		Map<String, Object> jsonMap_update = new HashMap<String, Object>();
		jsonMap_update.put("message", "update !!!!cool");
		UpdateRequest request_update = new UpdateRequest("posts", "doc", "1")
		        .doc(jsonMap_update);
		
		UpdateResponse updateResponse = client.update(request_update);
		
		// GET _________________________________________
		
		GetRequest getRequest = new GetRequest(
		        "posts", 
		        "doc",  
		        "1"); 
		
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
