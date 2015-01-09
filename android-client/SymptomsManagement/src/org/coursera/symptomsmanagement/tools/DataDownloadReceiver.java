package org.coursera.symptomsmanagement.tools;

import org.coursera.symptomsmanagement.client.RestClient;
import org.coursera.symptomsmanagement.client.SMService;
import org.coursera.symptomsmanagement.database.SMDataSource;
import org.coursera.symptomsmanagement.database.schema.SMData;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class DataDownloadReceiver extends BroadcastReceiver {
	
	private static final String LOG_TAG = DataDownloadReceiver.class.getSimpleName();

	@Override
	public void onReceive(Context context, Intent intent) {
		ConnectivityManager cm =
		        (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		 
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		boolean isConnected = activeNetwork != null &&
		                      activeNetwork.isConnectedOrConnecting();
		
		if (isConnected && activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
			
			final Context context4Thread = context;
			Log.d(LOG_TAG, "Device is connected. Downloading data!");
			
			Thread thread = new Thread(new Runnable() {
				
				@Override
				public void run() {
					RestClient serviceClient = SMService.generateClient();
					SMData smData = serviceClient.getAllDataFromServer();
					
					SMDataSource datasource = new SMDataSource(context4Thread);
					datasource.open();
					datasource.truncateDatabase();
					datasource.inserSMData(smData);
					datasource.close();
				}
			});

			thread.start();
			
			Log.d(LOG_TAG, "Data Download completed!");
		}
	}

}
