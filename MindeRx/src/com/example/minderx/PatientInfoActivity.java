package com.example.minderx;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import database.MindeRxDatabase;

public class PatientInfoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_patient_info);
		
		Bundle extras = getIntent().getExtras();
		String essn = extras.getString("ESSN");
		String pssn = extras.getString("PSSN");
		
		TextView staffName = (TextView) this.findViewById(R.id.staff_name_label_patient_info);
		TextView patientName = (TextView) this.findViewById(R.id.patient_name_label_patient_info);
		staffName.setText( queryForStaffName(essn) );
		patientName.setText( queryForPatientName(pssn) );

		setupListeners(R.id.blood_pressure_bn, PatientBloodPressureActivity.class, essn, pssn);
		setupListeners(R.id.heart_rate_bn    , PatientHeartRateActivity.class, essn, pssn);
		setupListeners(R.id.o2_sat_lvl_bn    , PatientSaLevelActivity.class, essn, pssn);
		setupListeners(R.id.temperature_bn   , PatientTemperatureActivity.class, essn, pssn);	

	}

	private String queryForPatientName(String pssn) {
		MindeRxDatabase db = new MindeRxDatabase(this);
		
		return db.getPatientNameFromPssn(pssn);	
	}
	
	private String queryForStaffName(String essn) {
		MindeRxDatabase db = new MindeRxDatabase(this);
		
		return db.getStaffNameFromEssn(essn);	
	}
	
	private void setupListeners(int rID, final Class<?> activity, final String essn, final String pssn) {
		final Button b = (Button) this.findViewById(rID);
		b.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(v.getContext(), activity);
				intent.putExtra("ESSN", essn);
				intent.putExtra("PSSN", pssn);
				startActivityForResult(intent, 0);
	
			}
		});	
	}
	
}