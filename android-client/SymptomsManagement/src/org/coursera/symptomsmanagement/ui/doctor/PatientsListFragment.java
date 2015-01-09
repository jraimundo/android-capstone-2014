package org.coursera.symptomsmanagement.ui.doctor;

import java.util.ArrayList;
import java.util.List;

import org.coursera.androidcapstone.symptomsmanagement.R;
import org.coursera.symptomsmanagement.database.schema.Checkin;
import org.coursera.symptomsmanagement.database.schema.CheckinMedication;
import org.coursera.symptomsmanagement.database.schema.Doctor;
import org.coursera.symptomsmanagement.database.schema.Patient;
import org.coursera.symptomsmanagement.ui.SMAbstractFragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

public class PatientsListFragment extends SMAbstractFragment {

	private static final String LOG_TAG = PatientsListFragment.class.getSimpleName();
	
	private List<Patient> patientData;
	private PatientArrayAdapter arrayAdapter;
	private EditText patientsFilter;
	Integer doctorId; 
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		((PatientsListActivity) getActivity()).datasource = this.datasource;
		this.doctorId = getActivity().getIntent().getExtras().getInt(Doctor.ID);
		this.patientData = this.datasource.getDoctorPatients(this.doctorId);
		Log.d(LOG_TAG, "doctorId: " + this.doctorId);
		this.arrayAdapter = new PatientArrayAdapter(getActivity(), R.layout.patients_list_item,
				this.patientData);
		
		setListAdapter(this.arrayAdapter);
		
		ListView listView = getListView();
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent patientDetailIntent = new Intent(getActivity(), PatientDetailActivity.class);
				patientDetailIntent.putExtra(Patient.ID, Long.valueOf(id).intValue());
				patientDetailIntent.putExtra(Doctor.ID, doctorId);
				startActivity(patientDetailIntent);
			}
			
		});
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.patients_list_view, container, false);
		
		this.patientsFilter = (EditText) view.findViewById(R.id.patients_filter);
		this.patientsFilter.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				patientData.clear();
				
				if (s.toString().trim().equals("")) {
					patientData.addAll(datasource.getDoctorPatients(doctorId));
				} else {
					patientData.addAll(datasource.getDoctorPatients(doctorId, s.toString()));
				}
				
				arrayAdapter.notifyDataSetChanged();
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		
		return view;
		
	}

//	@Override
//	public void onDetach() {
//		Log.d(LOG_TAG, "entered onDetach()");
//		this.datasource.close();
//		
//		super.onDetach();
//	}
	
	
	
}
