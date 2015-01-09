package org.coursera.symptomsmanagement.ui.doctor;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.coursera.androidcapstone.symptomsmanagement.R;
import org.coursera.symptomsmanagement.client.RestClient;
import org.coursera.symptomsmanagement.client.SMService;
import org.coursera.symptomsmanagement.database.SMDataSource;
import org.coursera.symptomsmanagement.database.schema.Alerts;
import org.coursera.symptomsmanagement.database.schema.Checkin;
import org.coursera.symptomsmanagement.database.schema.Doctor;
import org.coursera.symptomsmanagement.database.schema.Patient;
import org.coursera.symptomsmanagement.ui.SMAbstractActivity;
import org.coursera.symptomsmanagement.ui.patient.CheckInActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class PatientsListActivity extends SMAbstractActivity { //implements ListSelectionListener {
	
	private static final String LOG_TAG = PatientsListActivity.class.getSimpleName();
	
	private MenuItem menuItem;
	SMDataSource datasource;
	private Integer doctorId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
//		Log.d(LOG_TAG, "entered onCreate()");

//		this.setUserType(UserType.DOCTOR);
//		this.userId = 1l;
		super.onCreate(savedInstanceState);
		
		PatientsListFragment fragment = new PatientsListFragment();
		
		getFragmentManager().beginTransaction().add(R.id.main_fragment, fragment, "PatientsListFragment").commit();
		
		this.doctorId = getIntent().getExtras().getInt(Doctor.ID);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.patients_list, menu);
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
		case R.id.action_refresh:
			this.menuItem = item;
			this.menuItem.setActionView(R.layout.progress_bar);
			this.menuItem.expandActionView();
			try {
				List<Alerts> alerts = new RefreshDataTask().execute().get();
				for (Alerts alert : alerts) {
					Intent mNotificationIntent = new Intent(PatientsListActivity.this, PatientDetailActivity.class);
					mNotificationIntent.putExtra(Patient.ID, alert.getPatientId());
					mNotificationIntent.putExtra(Alerts.ID, alert.getAlertId());
					PendingIntent mContentIntent = PendingIntent.getActivity(PatientsListActivity.this, 0,
							mNotificationIntent, Intent.FLAG_ACTIVITY_NEW_TASK);
					long[] mVibratePattern = { 0, 200, 200, 300 };
					
					Notification.Builder notificationBuilder = new Notification.Builder(
							PatientsListActivity.this).setTicker("A Patient is having problems!")
							.setSmallIcon(R.drawable.ic_stat_name)
//							.setSmallIcon(android.R.drawable.stat_sys_warning)
							.setAutoCancel(true).setContentTitle("A Patient is having problems!")
							.setContentIntent(mContentIntent)
							.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
							.setVibrate(mVibratePattern);
					
					NotificationManager mNotificationManager = (NotificationManager) PatientsListActivity.this
							.getSystemService(Context.NOTIFICATION_SERVICE);
					mNotificationManager.notify(1, notificationBuilder.build());
				}
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
			break;
		default:
			break;	
		}
		
		return true;
	}
	
	private class RefreshDataTask extends AsyncTask<Void, Void, List<Alerts>> {

		@Override
		protected List<Alerts> doInBackground(Void... params) {
			
			Long timestampInt = datasource.getLatestTimestampByDoctor(doctorId).longValue() + 1;
			Long timestamp = timestampInt * 1000L;
			
			RestClient clientService = SMService.generateClient();
			List<Checkin> checkins = clientService.getCheckins(doctorId, timestamp);
			datasource.insertCheckins(checkins);
			
			List<Alerts> alerts = clientService.getAlerts(doctorId);
			
			return alerts;
		}

		@Override
		protected void onPostExecute(List<Alerts> result) {
			menuItem.collapseActionView();
			menuItem.setActionView(null);
			Toast.makeText(getApplication(), "Patients data updated", Toast.LENGTH_SHORT).show();
		}
		
		
	}
	
//	@Override
//	public void onAttachFragment(Fragment fragment) {
//		super.onAttachFragment(fragment);
//		
//		PatientsListFragment patientsListFragment = (PatientsListFragment) fragment;
//		
//		patientsListFragment.doctorId = this.userId;
//		patientsListFragment.datasource = this.datasource;
//	}
//	@Override
//	public void onListSelection(int index) {
//		Log.d(LOG_TAG, "entered onListSelection()");
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		// Handle action bar item clicks here. The action bar will
//		// automatically handle clicks on the Home/Up button, so int
//		// as you specify a parent activity in AndroidManifest.xml.
//		int id = item.getItemId();
//		if (id == R.id.action_settings) {
//			return true;
//		}
//		return super.onOptionsItemSelected(item);
//	}
}
