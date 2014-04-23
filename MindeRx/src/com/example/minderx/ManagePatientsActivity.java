package com.example.minderx;

import java.util.ArrayList;

import database.MindeRxDatabase;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ManagePatientsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_patients);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.manage_patients, menu);
        return true;
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
