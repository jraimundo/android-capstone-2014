package org.coursera.symptomsmanagement.ui.doctor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.coursera.androidcapstone.symptomsmanagement.R;
import org.coursera.symptomsmanagement.client.RestClient;
import org.coursera.symptomsmanagement.client.SMService;
import org.coursera.symptomsmanagement.database.SMDataSource;
import org.coursera.symptomsmanagement.database.schema.Alerts;
import org.coursera.symptomsmanagement.database.schema.Medication;
import org.coursera.symptomsmanagement.database.schema.Patient;
import org.coursera.symptomsmanagement.database.schema.PatientMedication;
import org.coursera.symptomsmanagement.ui.SMAbstractActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

public class PatientDetailActivity extends SMAbstractActivity {

	private static final String LOG_TAG =  PatientDetailActivity.class.getSimpleName();
	
	SMDataSource datasource;
	private MenuItem menuItem;
	private List<Medication> medicationsForAddition;
	private Integer patientId;
	PatientDetailFragment mFragment;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d(LOG_TAG, "entered onCreate()");
		
//		this.setUserType(UserType.DOCTOR);
//		this.userId = getIntent().getExtras().getInteger(Doctor.ID);
		super.onCreate(savedInstanceState);
		this.patientId = getIntent().getExtras().getInt(Patient.ID);
		Log.d(LOG_TAG, "Alert Id: " + getIntent().getExtras().getInt(Alerts.ID));
		
		if (getIntent().getExtras().getInt(Alerts.ID) > 0) {
			new AcknowledgeAlertTask().execute(getIntent().getExtras().getInt(Alerts.ID));
		}
		
		this.mFragment = new PatientDetailFragment();
//		fragment.datasource = this.datasource;
//		fragment.patientId = getIntent().getExtras().getInteger(Patient.ID);
		
		getFragmentManager().beginTransaction().add(R.id.main_fragment, this.mFragment, "PatientDetailFragment").commit();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.patients_detail, menu);
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
		case R.id.action_add_medication:
			this.menuItem = item;
			
			this.medicationsForAddition = this.datasource.getMedicationsPatientNotHave(this.patientId);
			
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Add Medication")
			.setSingleChoiceItems(this.getMedicationNames(this.medicationsForAddition), -1,
					new DialogInterface.OnClickListener() {
				
						@Override
						public void onClick(DialogInterface dialog, int which) {
							ListView listView = ((AlertDialog) dialog).getListView();
		//					String itemSelected = ((TextView) listView.getSelectedItem()).getText().toString();
							Log.d(LOG_TAG, "Medication selected: " + listView.getTag());
						}
					}).setPositiveButton("Add", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							ListView listView = ((AlertDialog) dialog).getListView();
//							String medicationSelected = medicationsForAddition
//									.get(listView.getCheckedItemPosition()).getMedicationName();
							Integer medicationSelected = medicationsForAddition
									.get(listView.getCheckedItemPosition()).getMedicationId();
//							Log.d(LOG_TAG, "Medication selected: " + medicationSelected);
							try {
								boolean taskExecuted = new AddPatientMedicationTask().execute(medicationSelected).get();
							} catch (InterruptedException | ExecutionException e) {
								e.printStackTrace();
							}
						}
					})
					.setNegativeButton("Cancel", null);
			builder.create().show();
			
			break;
		default:
			break;	
		}
		
		return true;
	}
	
	private String[] getMedicationNames(List<Medication> medications) {
		List<String> medicationNames = new ArrayList<>(medications.size());
		
		for (Medication medication : medications) {
			medicationNames.add(medication.getMedicationName());
		}
		
		String[] MedicationNamesStrArr = medicationNames.toArray(new String[medications.size()]);
		
		return MedicationNamesStrArr;
	}
	
	private class AddPatientMedicationTask extends AsyncTask<Integer, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Integer... params) {
			Integer medicationId = params[0];
			
			RestClient restClient = SMService.generateClient();
			PatientMedication patientMedication = new PatientMedication(patientId, medicationId);
			restClient.addPatientMedication(patientMedication);
			
			datasource.insertPatientMedication(patientMedication);
			
			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			mFragment.updateListAdapter();
		}
		
	}
	
	private class AcknowledgeAlertTask extends AsyncTask<Integer, Void, Void> {

		@Override
		protected Void doInBackground(Integer... params) {
			Integer alertId = params[0];
			
			RestClient restClient = SMService.generateClient();
			restClient.acknowledgeAlert(alertId);
			
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			Toast.makeText(getApplication(), "Alert Acknowledged", Toast.LENGTH_SHORT).show();
		}
		
	}
	
//	private Integer getMedicationIdByName() {
//		
//	}
	
}
