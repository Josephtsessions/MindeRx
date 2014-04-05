package com.example.minderx;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class VitalsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.x_activity_vitals_relink);

		register_btnListener(R.id.blood_pressure_bn, PatientBloodPressureActivity.class);
		register_btnListener(R.id.heart_rate_bn    , PatientHeartRateActivity.class);
		register_btnListener(R.id.o2_sat_lvl_bn    , PatientSaLevelActivity.class);
		register_btnListener(R.id.temperature_bn   , PatientTemperatureActivity.class);	

		}

	  private void register_btnListener(int rID, final Class activity){
		final Button b = (Button) this.findViewById(rID);
		b.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				Intent ntent = new Intent(v.getContext(), activity);
				startActivityForResult(ntent, 0);
	
				}
			});	
	  	}
	
	}

