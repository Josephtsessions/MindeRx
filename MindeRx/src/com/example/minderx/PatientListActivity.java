package com.example.minderx;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PatientListActivity extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.x_activity_patient_list_relink);

		setupListeners();	

		}

	private void setupListeners() {
		final Button manageButton = (Button) this.findViewById(R.id.ManagePatient_bn);
		manageButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(v.getContext(), PatientInfoActivity.class);
				startActivityForResult(intent, 0);

			}
		});	
	}
}
