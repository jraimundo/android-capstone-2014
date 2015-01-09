package org.coursera.symptomsmanagement.ui.doctor;

import java.util.List;

import org.coursera.androidcapstone.symptomsmanagement.R;
import org.coursera.symptomsmanagement.database.schema.Patient;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PatientArrayAdapter extends ArrayAdapter<Patient> {
	
	private static final String LOG_TAG = PatientArrayAdapter.class.getSimpleName();
	
	int resource;

	public PatientArrayAdapter(Context context, int resource, List<Patient> objects) {
		super(context, resource, objects);
		
		this.resource = resource;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LinearLayout view = null;
		
		Patient item = getItem(position);
		
		if (convertView == null) {
			view = new LinearLayout(getContext());
			LayoutInflater layoutInflater = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			layoutInflater.inflate(this.resource, view, true);
		} else {
			view = (LinearLayout) convertView;
		}
		
		TextView medicalRecordNumber = (TextView) view.findViewById(R.id.patients_list_item_medical_record_number);
		TextView patientName = (TextView) view.findViewById(R.id.patients_list_item_name);
		
		medicalRecordNumber.setText(item.getMedicalRecordNumber());
		patientName.setText(getPatientFullName(item));
		
		return view;
	}
	
	@Override
	public long getItemId(int position) {
		return getItem(position).getPatientId();
	}

	private String getPatientFullName(Patient patient) {
		StringBuffer sb = new StringBuffer();
		
		sb.append(patient.getFirstName());
		sb.append(" ");
		sb.append(patient.getLastName());
		
		return sb.toString();
	}
}
