package com.example.minderx;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import database.MindeRxDatabase;

public class ManagePatientsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_patients);
        
		Bundle extras = getIntent().getExtras();
		String essn = extras.getString("ESSN");
		
		TextView staffName = (TextView) this.findViewById(R.id.staff_name_label_manage_patients);
		staffName.setText( queryForStaffName(essn) );
        
		Spinner patientsSpinner = (Spinner) findViewById(R.id.patientsSpinner);
		Spinner staffSpinner = (Spinner) findViewById(R.id.staffSpinner);
		
		ArrayList<String> patients = queryForPatientNames(essn);
		ArrayList<String> staff = queryForStaffNames();
		
		// Adapters are used to populate ListViews in Android
		ArrayAdapter<String> patientAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, patients);
		patientsSpinner.setAdapter(patientAdapter);    
		
		ArrayAdapter<String> staffAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, staff);
		staffSpinner.setAdapter(staffAdapter);    
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.manage_patients, menu);
        return true;
    }
	
    private String queryForStaffName(String essn) {
		MindeRxDatabase db = new MindeRxDatabase(this);
		
		return db.getStaffNameFromEssn(essn);	
	}
    
    private ArrayList<String> queryForPssns(String essn) {
		MindeRxDatabase db = new MindeRxDatabase(this);
		
		return db.getPssnsFromEssn(essn);	
	}
	
	private ArrayList<String> queryForPatientNames(String essn) {
		MindeRxDatabase db = new MindeRxDatabase(this);
		
		return db.getPatientNamesFromPssns(queryForPssns(essn));
	}
	
	private ArrayList<String> queryForEssns() {
		MindeRxDatabase db = new MindeRxDatabase(this);
		
		return db.getEssns();
	}
	
	private ArrayList<String> queryForStaffNames() {
		MindeRxDatabase db = new MindeRxDatabase(this);
		
		return db.getStaffNames( queryForEssns() );
	}
}
