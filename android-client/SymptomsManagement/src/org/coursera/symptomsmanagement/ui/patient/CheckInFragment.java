package org.coursera.symptomsmanagement.ui.patient;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.coursera.androidcapstone.symptomsmanagement.R;
import org.coursera.symptomsmanagement.client.RestClient;
import org.coursera.symptomsmanagement.client.SMService;
import org.coursera.symptomsmanagement.database.schema.Checkin;
import org.coursera.symptomsmanagement.database.schema.CheckinMedication;
import org.coursera.symptomsmanagement.database.schema.Doctor;
import org.coursera.symptomsmanagement.database.schema.Medication;
import org.coursera.symptomsmanagement.database.schema.Patient;
import org.coursera.symptomsmanagement.ui.SMAbstractFragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

public class CheckInFragment extends SMAbstractFragment {
	
	private static String LOG_TAG = CheckInFragment.class.getSimpleName();

	private RadioGroup painLevelRadioGroup;
	private RadioGroup eatLevelRadioGroup;
	private MedicationSwitchArrayAdapter arrayadapter;
	private Button setTimeButton;
	private Button saveButton;
	private Calendar checkinTimestamp;
	Integer patientId;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater
				.inflate(R.layout.patient_checkin, container, false);

		this.patientId = getActivity().getIntent().getExtras().getInt(Patient.ID);

		this.painLevelRadioGroup = (RadioGroup) view.findViewById(R.id.patient_checkin_pain_level_radio_group);
		this.eatLevelRadioGroup = (RadioGroup) view.findViewById(R.id.patient_checkin_eat_level_radio_group);

		this.arrayadapter = new MedicationSwitchArrayAdapter(getActivity(),
				R.layout.patient_checkin_medication_item, this.datasource.getPatientMedications(patientId));
		setListAdapter(this.arrayadapter);
		
		this.setTimeButton = (Button) view.findViewById(R.id.button_checkin_set_time);
		this.saveButton = (Button) view.findViewById(R.id.button_checkin_save_button);
		
		this.checkinTimestamp = Calendar.getInstance();
		

		return view;
	}
	
	

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		this.setTimeButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DialogFragment timePickerFragment = new TimePickerFragment();
				timePickerFragment.show(getFragmentManager(), "TimePickerFragment");
			}
		});
		
		this.saveButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Checkin checkin = new Checkin();
				checkin.setPatientId(patientId);
				
				int painLevelRadioId = painLevelRadioGroup.getCheckedRadioButtonId();
				
				Integer painLevel;
				switch (painLevelRadioId) {
					case R.id.patient_checkin_pain_level_controlled_radio:
						painLevel = 1;
						break;
					case R.id.patient_checkin_pain_level_moderate_radio:
						painLevel = 2;
						break;
					case R.id.patient_checkin_pain_level_severe_radio:
						painLevel = 3;
						break;
					default:
						painLevel = 1;
				}
				
				checkin.setThroatPainLevel(painLevel);
				
				int eatLevelRadioId = eatLevelRadioGroup.getCheckedRadioButtonId();
				
				Integer eatLevel;
				switch (eatLevelRadioId) {
					case R.id.patient_checkin_eat_level_no_radio:
						eatLevel = 1;
						break;
					case R.id.patient_checkin_eat_level_some_radio:
						eatLevel = 2;
						break;
					case R.id.patient_checkin_eat_level_cant_eat_radio:
						eatLevel = 3;
						break;
					default:
						eatLevel = 1;
				}
				
				checkin.setEatDificultyLevel(eatLevel);
				
				ListView listView = getListView();
				int medicationsCount = listView.getChildCount();
				List<CheckinMedication> medicationsList = new ArrayList<CheckinMedication>(medicationsCount);
				for (int i = 0; i < listView.getChildCount(); i++) {
					View view = listView.getChildAt(i);
					Switch medicationSwitch = (Switch) view.findViewById(R.id.patient_medicaton_checkin_switch);
//					Toast.makeText(getActivity(),
//							Integer.valueOf(medicationSwitch.getInputExtras(false).getInt(Medication.ID)).toString(),
//							Toast.LENGTH_SHORT).show();
					int medicationId = medicationSwitch.getInputExtras(false).getInt(Medication.ID);
					medicationsList.add(new CheckinMedication(medicationId, medicationSwitch.isChecked()));
				}
				checkin.setCheckinMedicationList(medicationsList);
				
//				checkin.setCheckinTimestamp(Long.valueOf(checkinTimestamp.getTimeInMillis()).intValue());
				checkin.setCheckinTimestamp(checkinTimestamp.getTimeInMillis());
				
//				Log.d(LOG_TAG, Long.valueOf(checkinTimestamp.getTimeInMillis()).toString());
				
				try {
					Integer newCheckinId = new addCheckin().execute(checkin).get();
					checkin.setCheckinId(newCheckinId);
					
					datasource.insertCheckin(checkin);
					
					Toast.makeText(getActivity(), "Check-In Successful", Toast.LENGTH_SHORT).show();
					
					getActivity().finish();
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
				
			}
		});
			
		
//		this.saveButton.setOnClickListener(new OnClickListener() {
//			
///*			@Override
//			public void onClick(View v) {
//				Checkin checkin = new Checkin();
//				
////				Toast.makeText(getActivity(), checkInHour + " : " + checkInMinute, Toast.LENGTH_SHORT).show();
//				ListView listView = getListView();
////				listView.getChildCount();
////				Toast.makeText(getActivity(), Integer.valueOf(listView.getChildCount()).toString(), Toast.LENGTH_SHORT).show();
//				int medicationsCount = listView.getChildCount();
//				List<CheckinMedication> medicationsList = new ArrayList<CheckinMedication>(medicationsCount);
//				for (int i = 0; i < listView.getChildCount(); i++) {
//					View view = listView.getChildAt(i);
//					Switch medicationSwitch = (Switch) view.findViewById(R.id.patient_medicaton_checkin_switch);
//					Toast.makeText(getActivity(),
//							Integer.valueOf(medicationSwitch.getInputExtras(false).getInt(Medication.ID)).toString(),
//							Toast.LENGTH_SHORT).show();
//					int medicationId = medicationSwitch.getInputExtras(false).getInt(Medication.ID);
//					medicationsList.add(new CheckinMedication(medicationId, medicationSwitch.isChecked()));
//				}
//				checkin.setMedications(medicationsList);
//			}*/
//			
////			@Override
////			public void onClick(View v) {
////				
////				try {
////					List<Doctor> doctors = new getDoctorsTask().execute().get();
////					
////					for (Doctor doctor : doctors) {
////						Toast.makeText(getActivity(), doctor.getDoctorId() + ": " + doctor.getFirstName() + " " + doctor.getLastName(),
////								Toast.LENGTH_LONG).show();
////					}
////				} catch (InterruptedException | ExecutionException e) {
////					e.printStackTrace();
////				}
////			}
//		});
	}
	
	private class addCheckin extends AsyncTask<Checkin, Void, Integer> {

		@Override
		protected Integer doInBackground(Checkin... params) {
			RestClient clientService = SMService.generateClient();
			
			Integer checkinId = clientService.addCheckin(params[0]);
			return checkinId;
		}

		
		
	}
	
//	private class getDoctorsTask extends AsyncTask<Void, Void, List<Doctor>> {
//
//		@Override
//		protected List<Doctor> doInBackground(Void... params) {
////			try {
//				RestClient clientService = SMService.generateClient();
//				
//				List<Doctor> doctors = clientService.getDoctors();
////			} catch (Exception e) {
////				e.printStackTrace();
////				Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
////			};
//			
//			return doctors;
//		}
//		
//	}

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
			checkinTimestamp.set(Calendar.HOUR_OF_DAY, hourOfDay);
			checkinTimestamp.set(Calendar.MINUTE, minute);
		}
	}

}

