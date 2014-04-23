package com.example.minderx;

import database.MindeRxDatabase;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

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
    
}
