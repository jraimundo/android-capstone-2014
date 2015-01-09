package org.coursera.symptomsmanagement.ui.patient;

import org.coursera.androidcapstone.symptomsmanagement.R;
import org.coursera.symptomsmanagement.database.schema.Patient;
import org.coursera.symptomsmanagement.ui.SMAbstractActivity;

import android.os.Bundle;

public class CheckInActivity extends SMAbstractActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		CheckInFragment fragment = new CheckInFragment();
//		fragment.datasource = this.datasource;
//		fragment.patientId = getIntent().getExtras().getInteger(Patient.ID);
		
		getFragmentManager().beginTransaction().add(R.id.main_fragment, fragment, "CheckInFragment").commit();
	}
}
