package org.coursera.symptomsmanagement.ui.patient;

import java.util.List;

import org.coursera.androidcapstone.symptomsmanagement.R;
import org.coursera.symptomsmanagement.database.schema.Medication;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Switch;

public class MedicationSwitchArrayAdapter extends ArrayAdapter<Medication> {
	
	int resource;

	public MedicationSwitchArrayAdapter(Context context, int resource, List<Medication> objects) {
		super(context, resource, objects);
		
		this.resource = resource;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LinearLayout view = null;
		
		Medication medication = getItem(position);
		
		if (convertView == null) {
			view = new LinearLayout(getContext());
			LayoutInflater layoutInflater = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			layoutInflater.inflate(this.resource, view, true);
		} else {
			view = (LinearLayout) convertView;
		}
		
		Switch medicationSwitch = (Switch) view.findViewById(R.id.patient_medicaton_checkin_switch);
		medicationSwitch.setText(medication.getMedicationName());
		medicationSwitch.getInputExtras(true).putInt(Medication.ID, medication.getMedicationId());
		
		return view;
	}
	
//	@Override
//	public long getItemId(int position) {
//		return getItem(position).getMedicationId();
//	}

}
