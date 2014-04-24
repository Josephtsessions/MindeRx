package com.example.minderx;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import database.MindeRxDatabase;

public class PatientHeartRateActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_heart_rate);
        
		Bundle extras = getIntent().getExtras();
		String essn = extras.getString("ESSN");
		String pssn = extras.getString("PSSN");
        
		TextView staffName = (TextView) this.findViewById(R.id.staff_name_label_patient_heart_rate);
		TextView patientName = (TextView) this.findViewById(R.id.patient_name_label_patient_heart_rate);
		staffName.setText( queryForStaffName(essn) );
		patientName.setText( queryForPatientName(pssn) );

		setupListeners(essn, pssn);
    }

	private String queryForPatientName(String pssn) {
		MindeRxDatabase db = new MindeRxDatabase(this);
		
		return db.getPatientNameFromPssn(pssn);	
	}
	
	private String queryForStaffName(String essn) {
		MindeRxDatabase db = new MindeRxDatabase(this);
		
		return db.getStaffNameFromEssn(essn);	
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.patient_heart_rate, menu);
        return true;
    }
    
	private void setupListeners(final String essn, final String pssn) {
		final Button submitBn =  (Button) this.findViewById(R.id.submit_bn);
		final EditText temperatureField = (EditText) this.findViewById(R.id.Temperature_Num); // Temperature num needs to be changed - Doesn't match HeartRateActivity
		
		submitBn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
	            imm.hideSoftInputFromWindow(submitBn.getWindowToken(), 
	                                      InputMethodManager.RESULT_UNCHANGED_SHOWN);
				
	            String bpmFieldText = temperatureField.getText().toString();
	            Integer bpm =  Integer.valueOf( bpmFieldText );
	            
	            recordHeartRate(bpm, essn, pssn);
				Toast.makeText(v.getContext(), "Recorded!", Toast.LENGTH_LONG).show();
			}
		});		
	
	}
	
	private void recordHeartRate(Integer bpm, String essn, String pssn) {
		MindeRxDatabase db = new MindeRxDatabase(this);
		
		db.recordHeartRate(bpm, essn, pssn);
	}
    
}
