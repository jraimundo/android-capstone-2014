package org.coursera.symptomsmanagement.ui;

import java.util.concurrent.ExecutionException;

import org.coursera.androidcapstone.symptomsmanagement.R;
import org.coursera.symptomsmanagement.client.RestClient;
import org.coursera.symptomsmanagement.client.SMService;
import org.coursera.symptomsmanagement.database.SMDataSource;
import org.coursera.symptomsmanagement.database.schema.Doctor;
import org.coursera.symptomsmanagement.database.schema.Patient;
import org.coursera.symptomsmanagement.database.schema.SMData;
import org.coursera.symptomsmanagement.ui.doctor.PatientsListActivity;
import org.coursera.symptomsmanagement.ui.patient.HomeActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class MainActivity extends Activity {
	
	private static final String LOG_TAG =  MainActivity.class.getSimpleName();

	private Button patientButton;
	private EditText patientNumber;
	private Button doctorButton;
	private EditText doctorNumber;
	private Button getAllDataButton;
	private ProgressBar progressBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		this.patientButton = (Button) findViewById(R.id.button_patient);
		this.patientNumber = (EditText) findViewById(R.id.editText_patient_number);
		this.doctorButton = (Button) findViewById(R.id.button_doctor);
		this.doctorNumber = (EditText) findViewById(R.id.editText_doctor_number);
		this.getAllDataButton = (Button) findViewById(R.id.button_get_data_from_server);
		this.progressBar = (ProgressBar) findViewById(R.id.progress_bar_main);
		
		this.patientNumber.setText("3");
		this.doctorNumber.setText("1");
		
		this.patientButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent patientIntent = new Intent(getApplicationContext(), HomeActivity.class);
				patientIntent.putExtra(Patient.ID, Integer.valueOf(patientNumber.getText().toString()));
				startActivity(patientIntent);
			}
		});
		
		this.doctorButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent patientIntent = new Intent(getApplicationContext(), PatientsListActivity.class);
				patientIntent.putExtra(Doctor.ID, Integer.valueOf(doctorNumber.getText().toString()));
				startActivity(patientIntent);
			}
		});
		
		this.getAllDataButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				progressBar.setVisibility(ProgressBar.VISIBLE);
				new GetDataFromServerTask().execute();
			}
		});
	}
	
//	@Override
//	protected void onDestroy() {
//		this.datasource.close();
//		
//		super.onDestroy();
//	}
	
	

	@Override
	protected void onResume() {
		super.onResume();
		
		SMDataSource datasource = new SMDataSource(MainActivity.this);
		datasource.open();
		
		datasource.close();
	}

	private class GetDataFromServerTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			
			RestClient serviceClient = SMService.generateClient();
			SMData smData = serviceClient.getAllDataFromServer();
			
			SMDataSource datasource = new SMDataSource(MainActivity.this);
			datasource.open();
			
			datasource.truncateDatabase();
			datasource.inserSMData(smData);
			
			datasource.close();
			
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			progressBar.setVisibility(ProgressBar.GONE);
		}
	}

}
