package com.example.minderx;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import database.MindeRxDatabase;

public class PatientListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_patient_list);
		
		Bundle extras = getIntent().getExtras();
		String essn = extras.getString("ESSN");
		
		TextView staffName = (TextView) this.findViewById(R.id.staff_name_label_patient_list);
		staffName.setText( queryForStaffName(essn) );
		
		setupListeners(essn, queryForPssns(essn));	
		
		ListView patientsListView = (ListView) findViewById(R.id.patient_info_listView);
		
		ArrayList<String> patients = queryForPatients(essn);
		
		// Adapters are used to populate ListViews in Android
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, patients);
		
		patientsListView.setAdapter(arrayAdapter);    
	}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.patient_list, menu);
        return true;
    }
    
	private String queryForStaffName(String essn) {
		MindeRxDatabase db = new MindeRxDatabase(this);
		
		return db.getStaffNameFromEssn(essn);	
	}
    
    private void setupListeners(final String essn, final ArrayList<String> pssns) {
		
		ListView patientsListView = (ListView) findViewById(R.id.patient_info_listView);
		
		patientsListView.setOnItemClickListener(new OnItemClickListener() {
			
			public void onItemClick(AdapterView parentView, View childView, int position, long id) {
				
				Intent intent = new Intent(childView.getContext(), PatientInfoActivity.class);
				intent.putExtra("ESSN", essn);
				intent.putExtra("PSSN", pssns.get(position));
				
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
	
	private ArrayList<String> queryForPssns(String essn) {
		MindeRxDatabase db = new MindeRxDatabase(this);
		
		return db.getPssnsFromEssn(essn);	
	}
	
	private ArrayList<String> queryForPatients(String essn) {
		MindeRxDatabase db = new MindeRxDatabase(this);
		
		return db.getPatientNamesFromPssns(queryForPssns(essn));
	}
	
}
