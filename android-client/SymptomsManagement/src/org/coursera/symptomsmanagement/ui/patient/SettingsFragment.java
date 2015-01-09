package org.coursera.symptomsmanagement.ui.patient;

import java.util.Calendar;

import org.coursera.androidcapstone.symptomsmanagement.R;
import org.coursera.symptomsmanagement.database.schema.Patient;
import org.coursera.symptomsmanagement.tools.AlarmNotificationReceiver1;
import org.coursera.symptomsmanagement.tools.AlarmNotificationReceiver2;
import org.coursera.symptomsmanagement.tools.AlarmNotificationReceiver3;
import org.coursera.symptomsmanagement.tools.AlarmNotificationReceiver4;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;

public class SettingsFragment extends Fragment {
	
	private Button buttonAlarm1;
	private Button buttonAlarm2;
	private Button buttonAlarm3;
	private Button buttonAlarm4;
	private Intent mNotificationReceiverIntent1, mNotificationReceiverIntent2,
	mNotificationReceiverIntent3, mNotificationReceiverIntent4;
	private PendingIntent mNotificationReceiverPendingIntent1, mNotificationReceiverPendingIntent2,
			mNotificationReceiverPendingIntent3, mNotificationReceiverPendingIntent4, currentPendingIntent;
	AlarmManager mAlarmManager;
	private Calendar timestamp;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.patient_settings, container, false);
		
		this.buttonAlarm1 = (Button) view.findViewById(R.id.button_alarm1);
		this.buttonAlarm2 = (Button) view.findViewById(R.id.button_alarm2);
		this.buttonAlarm3 = (Button) view.findViewById(R.id.button_alarm3);
		this.buttonAlarm4 = (Button) view.findViewById(R.id.button_alarm4);
		
		this.timestamp = Calendar.getInstance();
		
		mNotificationReceiverIntent1 = new Intent(getActivity(), AlarmNotificationReceiver1.class);
		mNotificationReceiverIntent1.putExtra(Patient.ID, getActivity().getIntent().getExtras().getInt(Patient.ID));
		mNotificationReceiverIntent2 = new Intent(getActivity(), AlarmNotificationReceiver2.class);
		mNotificationReceiverIntent2.putExtra(Patient.ID, getActivity().getIntent().getExtras().getInt(Patient.ID));
		mNotificationReceiverIntent3 = new Intent(getActivity(), AlarmNotificationReceiver3.class);
		mNotificationReceiverIntent3.putExtra(Patient.ID, getActivity().getIntent().getExtras().getInt(Patient.ID));
		mNotificationReceiverIntent4 = new Intent(getActivity(), AlarmNotificationReceiver4.class);
		mNotificationReceiverIntent4.putExtra(Patient.ID, getActivity().getIntent().getExtras().getInt(Patient.ID));
		mNotificationReceiverPendingIntent1 = PendingIntent.getBroadcast(getActivity(), 0, mNotificationReceiverIntent1, 0);
		mNotificationReceiverPendingIntent2 = PendingIntent.getBroadcast(getActivity(), 0, mNotificationReceiverIntent2, 0);
		mNotificationReceiverPendingIntent3 = PendingIntent.getBroadcast(getActivity(), 0, mNotificationReceiverIntent3, 0);
		mNotificationReceiverPendingIntent4 = PendingIntent.getBroadcast(getActivity(), 0, mNotificationReceiverIntent4, 0);
		
		this.buttonAlarm1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				currentPendingIntent = mNotificationReceiverPendingIntent1;
				DialogFragment timePickerFragment = new TimePickerFragment();
				timePickerFragment.show(getFragmentManager(), "TimePickerFragment");
			}
		});
		
		this.buttonAlarm2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				currentPendingIntent = mNotificationReceiverPendingIntent2;
				DialogFragment timePickerFragment = new TimePickerFragment();
				timePickerFragment.show(getFragmentManager(), "TimePickerFragment");
			}
		});
		
		this.buttonAlarm3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				currentPendingIntent = mNotificationReceiverPendingIntent3;
				DialogFragment timePickerFragment = new TimePickerFragment();
				timePickerFragment.show(getFragmentManager(), "TimePickerFragment");
			}
		});
		
		this.buttonAlarm4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				currentPendingIntent = mNotificationReceiverPendingIntent4;
				DialogFragment timePickerFragment = new TimePickerFragment();
				timePickerFragment.show(getFragmentManager(), "TimePickerFragment");
			}
		});
		
		return view;
	}
	
	private class TimePickerFragment extends DialogFragment implements
			TimePickerDialog.OnTimeSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current time as the default values for the picker
			final Calendar c = Calendar.getInstance();
			int hour = c.get(Calendar.HOUR_OF_DAY);
			int minute = c.get(Calendar.MINUTE);

			// Create a new instance of TimePickerDialog and return it
			return new TimePickerDialog(getActivity(), this, hour, minute,
					DateFormat.is24HourFormat(getActivity()));
		}

		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			timestamp.set(Calendar.HOUR_OF_DAY, hourOfDay);
			timestamp.set(Calendar.MINUTE, minute);
			timestamp.set(Calendar.SECOND, 0);
			
			mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, timestamp.getTimeInMillis(),
					AlarmManager.INTERVAL_DAY, currentPendingIntent);
//			mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 5000,
//					AlarmManager.INTERVAL_FIFTEEN_MINUTES, currentPendingIntent);
		}
	}
	
}
