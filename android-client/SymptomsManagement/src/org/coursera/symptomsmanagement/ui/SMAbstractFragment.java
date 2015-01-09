package org.coursera.symptomsmanagement.ui;

import org.coursera.symptomsmanagement.database.SMDataSource;

import android.app.Activity;
import android.app.ListFragment;

public class SMAbstractFragment extends ListFragment{

	protected SMDataSource datasource;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		this.datasource = new SMDataSource(getActivity());
		this.datasource.open();
	}

	@Override
	public void onDetach() {
		this.datasource.close();
		
		super.onDetach();
	}
	
	
}

