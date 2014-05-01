package com.example.minderx;

import java.text.ParseException;
import java.util.ArrayList;

import database.MindeRxDatabase;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class GraphPatientInfoActivity extends Activity {
	CheckBox cbHeartRate, cbTemp, cbBp,cbPulse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_patient_info);
        
		Bundle extras = getIntent().getExtras();
		String essn = extras.getString("ESSN");
		String pssn = extras.getString("PSSN");
		
		TextView staffName = (TextView) this.findViewById(R.id.staff_name_label_graph_patient_info);
		TextView patientName = (TextView) this.findViewById(R.id.patient_name_label_graph_patient_info);
		staffName.setText( queryForStaffName(essn) );
		patientName.setText( queryForPatientName(pssn) );

		setupListeners();
		
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.graph_patient_info, menu);
        return true;
    }
    
    public void lineGraphHandler (View view) throws ParseException{
    	ArrayList<int[]> data = new ArrayList<int[]>();
    	cbHeartRate = (CheckBox)findViewById(R.id.heart_rate_checkBox);
    	cbPulse = (CheckBox)findViewById(R.id.pulse_checkBox);
    	cbTemp = (CheckBox)findViewById(R.id.temp_checkBox);
    	cbBp = (CheckBox)findViewById(R.id.bp_checkBox);
    	
    	if (cbHeartRate.isChecked()) {
    		int[] ax= {11,22,33,44,55,66,77,88,99,22};
    		int[] ay ={44,55,77,88,99,23,46,79,65,36};
    		data.add(ax);
    		data.add(ay);
    	}
    	if (cbPulse.isChecked()) {
    		int[] bx= {11,22,33,44,55,66,77,88,99,110};
    		int[] by ={11,22,33,44,55,66,77,88,99,110};
    		data.add(bx);
    		data.add(by);
    	}
    	if (cbTemp.isChecked()) {
    		int[] cx= {11,76,88,77,66,75,44,33,52,11};
    		int[] cy ={11,99,88,90,66,55,44,23,22,81};
    		data.add(cx);
    		data.add(cy);
    	}
    	if (cbBp.isChecked()) {
    		int[] dx= {11,22,33,44,55,66,77,88,99,22};
    		int[] dy ={110,99,88,77,66,55,44,33,22,11};
    		data.add(dx);
    		data.add(dy);
    	}


		LineGraph line = new LineGraph();
		Intent lineIntent = line.getIntent(this,data);
		startActivity(lineIntent);		
	}
    
	private String queryForPatientName(String pssn) {
		MindeRxDatabase db = new MindeRxDatabase(this);
		
		return db.getPatientNameFromPssn(pssn);	
	}
	
	private String queryForStaffName(String essn) {
		MindeRxDatabase db = new MindeRxDatabase(this);
		
		return db.getStaffNameFromEssn(essn);	
	}
	
	private void setupListeners() {
		
		final Button logoutButton = (Button) this.findViewById(R.id.logout_bn);
		logoutButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(v.getContext(), LoginActivity.class);
				intent.setFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				
			}
		});
		
	}
    
}
