package org.coursera.symptomsmanagement.client;

import retrofit.RestAdapter;

public class SMService {
	
	public static RestClient generateClient() {
		RestClient clientService = new RestAdapter.Builder()
		.setEndpoint(RestClient.endpoint)
//		.setEndpoint(SMService.publicEndpoint).build()
//		.setClient(new OkClient(new OkHttpClient()))
		.setLogLevel(RestAdapter.LogLevel.FULL)
		.build().create(RestClient.class);
		
		return clientService;
	}
}
