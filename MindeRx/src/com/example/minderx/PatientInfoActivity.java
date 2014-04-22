package com.example.minderx;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class PatientInfoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_patient_info);

		setupListeners(R.id.blood_pressure_bn, PatientBloodPressureActivity.class);
		setupListeners(R.id.heart_rate_bn    , PatientHeartRateActivity.class);
		setupListeners(R.id.o2_sat_lvl_bn    , PatientSaLevelActivity.class);
		setupListeners(R.id.temperature_bn   , PatientTemperatureActivity.class);	

	}

	private void setupListeners(int rID, final Class<?> activity) {
		final Button b = (Button) this.findViewById(rID);
		b.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(v.getContext(), activity);
				startActivityForResult(intent, 0);
	
			}
		});	
	}
	
}

