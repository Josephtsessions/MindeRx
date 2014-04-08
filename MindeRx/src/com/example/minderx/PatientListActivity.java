package com.example.minderx;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class PatientListActivity extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.x_activity_patient_list_relink);

		setupListeners();	
		
		ListView patientsListView = (ListView) findViewById(R.id.patients_listView);
		
		List<String> patients = new ArrayList<String>();
		patients.add("Michael Jackson");
		patients.add("Billy Mays");
		patients.add("Steve Jobs");
		
		// Adapters are used to populate ListViews in Android
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, patients);
		
		patientsListView.setAdapter(arrayAdapter);

	}

	private void setupListeners() {
		
		ListView patientsListView = (ListView) findViewById(R.id.patients_listView);
		
		patientsListView.setOnItemClickListener(new OnItemClickListener() {
			
			public void onItemClick(AdapterView parentView, View childView, int position, long id) {
				
				Intent intent = new Intent(childView.getContext(), PatientInfoActivity.class);
				startActivityForResult(intent, 0);
			}

		});
		
		final Button manageButton = (Button) this.findViewById(R.id.ManagePatient_bn);
		manageButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				// Pass a new intent to ManagePatientsActivity once it's been written.
				
			}
		});	
	}
}
