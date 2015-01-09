package org.coursera.symptomsmanagement.ui.patient;

import org.coursera.androidcapstone.symptomsmanagement.R;
import org.coursera.symptomsmanagement.ui.SMAbstractActivity;

import android.app.AlarmManager;
import android.os.Bundle;

public class SettingsActivity extends SMAbstractActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setTitle("Settings");
		
		SettingsFragment fragment = new SettingsFragment();
		getFragmentManager().beginTransaction().add(R.id.main_fragment, fragment, "SettingsFragment").commit();
		fragment.mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		
	}
}
