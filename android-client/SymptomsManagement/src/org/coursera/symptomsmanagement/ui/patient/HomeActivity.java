package org.coursera.symptomsmanagement.ui.patient;

import org.coursera.androidcapstone.symptomsmanagement.R;
import org.coursera.symptomsmanagement.database.SMDataSource;
import org.coursera.symptomsmanagement.database.schema.Patient;
import org.coursera.symptomsmanagement.ui.SMAbstractActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class HomeActivity extends SMAbstractActivity {
	
	private SMDataSource datasource;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
//		this.setUserType(UserType.PATIENT);
//		this.userId = 3l;
		
		super.onCreate(savedInstanceState);
		
		HomeFragment fragment = new HomeFragment();
		getFragmentManager().beginTransaction().add(R.id.main_fragment, fragment, "HomeFragment").commit();
		
		this.datasource = new SMDataSource(this);
		this.datasource.open();
		
		setTitle(this.datasource.getPatientName(getIntent().getExtras().getInt(Patient.ID)));
		
		if (datasource.getCheckinsByPatient(3).size() > 0) {
			Log.d("tag", datasource.getCheckinsByPatient(3).get(0));
		} else {
			Log.d("tag", "problems with checkin data");
		}
	}

	@Override
	protected void onDestroy() {
		this.datasource.close();
		
		super.onDestroy();
	}
}
