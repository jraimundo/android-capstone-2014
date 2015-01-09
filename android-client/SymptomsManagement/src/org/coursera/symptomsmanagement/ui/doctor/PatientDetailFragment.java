package org.coursera.symptomsmanagement.ui.doctor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.coursera.androidcapstone.symptomsmanagement.R;
import org.coursera.symptomsmanagement.client.RestClient;
import org.coursera.symptomsmanagement.client.SMService;
import org.coursera.symptomsmanagement.database.schema.Checkin;
import org.coursera.symptomsmanagement.database.schema.CheckinMedication;
import org.coursera.symptomsmanagement.database.schema.Medication;
import org.coursera.symptomsmanagement.database.schema.Patient;
import org.coursera.symptomsmanagement.database.schema.PatientMedication;
import org.coursera.symptomsmanagement.ui.SMAbstractFragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class PatientDetailFragment extends SMAbstractFragment {

	private static final String LOG_TAG = PatientDetailFragment.class.getSimpleName();
	
	private MedicationArrayAdapter arrayAdapter;
	private ArrayAdapter<String> checkinsArrayAdapter;
	Integer patientId;
	private TextView textView;
	private List<Medication> medicationsList;
	private ListView checkinsListView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.patient_detail, container, false);
		this.textView = (TextView) view.findViewById(R.id.patient_detail_info);
		this.patientId = getActivity().getIntent().getExtras().getInt(Patient.ID);
//		Log.d(LOG_TAG, String.valueOf(this.patientId));
		
		checkinsListView = (ListView) view.findViewById(R.id.listview_patient_checkin);
		
		return view;
	}
	
	@Override
	public void onAttach(Activity activity) {
		Log.d(LOG_TAG, "entered onAttach()");
		super.onAttach(activity);
		
//		this.patientId = getActivity().getIntent().getExtras().getInteger(Patient.ID);
//		this.datasource = new SMDataSource(activity);
//		this.datasource.open();
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.d(LOG_TAG, "entered onActivityCreated()");
		super.onActivityCreated(savedInstanceState);

		((PatientDetailActivity) getActivity()).datasource = this.datasource;
		Patient patient = this.datasource.getPatient(patientId);
		String patient_info = patient.getMedicalRecordNumber() +
				" - " + patient.getFirstName() + " " + patient.getLastName() + 
				" - Birthdate: " + patient.getBirthdate();
		this.textView.setText(patient_info);
		
		this.medicationsList = this.datasource.getPatientMedications(patientId); 
		
		this.arrayAdapter = new MedicationArrayAdapter(getActivity(), R.layout.patient_medication_item,
				this.medicationsList);
		setListAdapter(this.arrayAdapter);
		
		ListView listView = getListView();
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Medication medication = datasource.getMedicationDescription((int) id);
				
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setTitle(medication.getMedicationName());
				builder.setMessage(medication.getDescription());
				builder.setPositiveButton("Ok", null);
				builder.create().show();
			}
			
		});
		
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				final Integer medicationId = Long.valueOf(id).intValue();
				
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setTitle("Remove Medication?");
				builder.setNegativeButton("Cancel", null);
				builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						try {
							Boolean result = new RemovePatientMedicationTask().execute(medicationId).get();
						} catch (InterruptedException | ExecutionException e) {
							e.printStackTrace();
						}
					}
				});
				builder.create().show();
				
				return true;
			}
		});
		
		List<String> checkinsList = new ArrayList<String>();
		checkinsList = this.datasource.getCheckinsByPatient(3);
		this.checkinsArrayAdapter = new ArrayAdapter<String>(getActivity(),
				R.layout.patient_checkin_item, checkinsList);
		this.checkinsListView.setAdapter(checkinsArrayAdapter);
		
//		Log.d(LOG_TAG, this.datasource.getCheckinsByPatient(3).get(0));
	}
	
	void updateListAdapter() {
		this.medicationsList.clear();
		this.medicationsList.addAll(this.datasource.getPatientMedications(patientId));
		this.arrayAdapter.notifyDataSetChanged();
	}
	
	private class RemovePatientMedicationTask extends AsyncTask<Integer, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Integer... params) {
			PatientMedication patientMedication = new PatientMedication(patientId, params[0]);
			
			RestClient clientService = SMService.generateClient();
			clientService.removePatientMedication(patientMedication);
			
			datasource.deletePatientMedication(patientMedication);
			
			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			updateListAdapter();
		}
	}

}
