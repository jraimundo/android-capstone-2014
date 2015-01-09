package org.coursera.symptomsmanagement.ui.patient;

import org.coursera.androidcapstone.symptomsmanagement.R;
import org.coursera.symptomsmanagement.database.schema.Patient;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class HomeFragment extends Fragment {

	private Button checkInButton;
	private Button settingsButton;
	Integer patientId;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		this.patientId = getActivity().getIntent().getExtras().getInt(Patient.ID);
		
		View view = inflater.inflate(R.layout.patient_home, container, false);
		
		this.checkInButton = (Button) view.findViewById(R.id.patient_checkin_button);
		this.settingsButton = (Button) view.findViewById(R.id.patient_settings_button);
		
		this.checkInButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent checkInIntent = new Intent(getActivity(), CheckInActivity.class);
				checkInIntent.putExtra(Patient.ID, patientId);
				startActivity(checkInIntent);
			}
		});
		
		this.settingsButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent settingsInIntent = new Intent(getActivity(), SettingsActivity.class);
				settingsInIntent.putExtra(Patient.ID, patientId);
				startActivity(settingsInIntent);
			}
		});
		
		return view;
	}
}
