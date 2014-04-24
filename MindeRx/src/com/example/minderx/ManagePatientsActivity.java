package com.example.minderx;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
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
		
		setupListeners(queryForPssns(essn), queryForEssns(), essn);
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
	
	private void setupListeners(final ArrayList<String> pssns, final ArrayList<String> essns, final String floorNurseEssn) {
		
		final String[] pssn = {"0"};
		final String[] essn = {"0"};
		
		final Spinner patientsSpinner = (Spinner) this.findViewById(R.id.patientsSpinner);
		patientsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		        pssn[0] = pssns.get(pos);
		    }
		    public void onNothingSelected(AdapterView<?> parent) {
		    }
		});
		
		final Spinner staffSpinner = (Spinner) this.findViewById(R.id.staffSpinner);
		staffSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		        essn[0] = essns.get(pos);
		    }
		    public void onNothingSelected(AdapterView<?> parent) {
		    }
		});
		
		final Button b = (Button) this.findViewById(R.id.submit_bn);
		b.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				RadioGroup rg=(RadioGroup)findViewById(R.id.radioOperation);
				
				if(rg.getCheckedRadioButtonId()!=-1){
				    int id= rg.getCheckedRadioButtonId();
				    View radioButton = rg.findViewById(id);
				    int radioId = rg.indexOfChild(radioButton);
				    RadioButton btn = (RadioButton) rg.getChildAt(radioId);
				    String selection = (String) btn.getText();
				    
				    if ( selection.equals("Assign To") ) {
				    	assignPatientToStaff(pssn[0], essn[0], floorNurseEssn);
						Toast.makeText(v.getContext(), "Assignment Saved!", Toast.LENGTH_LONG).show();									    	
				    } else if ( selection.equals("Unassign From") ) {
				    	unassignPatientFromStaff(pssn[0], essn[0]);
						Toast.makeText(v.getContext(), "Unassignment Saved!", Toast.LENGTH_LONG).show();									    	
				    }
				    
				} else {
					Toast.makeText(v.getContext(), "Please Select an Operation.", Toast.LENGTH_LONG).show();					
				}
			
			}
		});	
	}
	
	private void assignPatientToStaff(String pssn, String essn, String floorNurseEssn) {
		MindeRxDatabase db = new MindeRxDatabase(this);
		
		db.assignPatientToStaff(pssn, essn, floorNurseEssn);
	}
	
	private void unassignPatientFromStaff(String pssn, String essn) {
		MindeRxDatabase db = new MindeRxDatabase(this);
		
		db.unassignPatientFromStaff(pssn, essn);
	}
	
}
