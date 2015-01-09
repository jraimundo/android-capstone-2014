package org.coursera.symptomsmanagement.ui.doctor;

import java.util.List;

import org.coursera.androidcapstone.symptomsmanagement.R;
import org.coursera.symptomsmanagement.database.schema.Medication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MedicationArrayAdapter extends ArrayAdapter<Medication> {
	
	private static final String LOG_TAG = MedicationArrayAdapter.class.getSimpleName();
	
	private int resource;

	public MedicationArrayAdapter(Context context, int resource, List<Medication> objects) {
		super(context, resource, objects);
		
		this.resource = resource;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LinearLayout view = null;
		
		Medication item = getItem(position);
		
		if (convertView == null) {
			view = new LinearLayout(getContext());
			LayoutInflater layoutInflater = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			layoutInflater.inflate(this.resource, view, true);
		} else {
			view = (LinearLayout) convertView;
		}
		
		TextView medicationName = (TextView) view.findViewById(R.id.patient_medication_item_text_view);
		medicationName.setText(item.getMedicationName());
		
		return view;
	}

	@Override
	public long getItemId(int position) {
		return getItem(position).getMedicationId();
	}
	
	

}
