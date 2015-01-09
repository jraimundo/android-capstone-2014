package org.coursera.symptomsmanagement.ui;

import org.coursera.androidcapstone.symptomsmanagement.R;
import org.coursera.symptomsmanagement.database.SMDataSource;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public abstract class SMAbstractActivity extends Activity {
	
	private static final String LOG_TAG = SMAbstractActivity.class.getSimpleName();

//	protected SMDataSource datasource;
//	private UserType userType;
//	protected Integer userId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		this.datasource = new SMDataSource(this);
//		this.datasource.open();
		
//		if (this.userType == UserType.PATIENT) {
//			setTitle(this.datasource.getPatientName(this.userId));
//		} else {
//			setTitle(this.datasource.getDoctorName(this.userId));
//		}
		
		setContentView(R.layout.main);
	}

//	@Override
//	protected void onDestroy() {
//		this.datasource.close();
//		super.onDestroy();
//	}

//	public void setUserType(UserType userType) {
//		this.userType = userType;
//	}
}
