package com.base;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.client.RestClient;

public class TestServlet {
	
	private RestClient restClient = new RestClient();	
	private String sampleURL = "http://localhost:8080/ProjectA/api/getName/2038";
	
	public String getDataFromBackend() {
		ClientResponse clientResponse = restClient.resource(sampleURL).get();
		return clientResponse.toString();
	}

}
