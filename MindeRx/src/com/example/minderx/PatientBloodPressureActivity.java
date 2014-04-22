package com.example.minderx;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class PatientBloodPressureActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_blood_pressure);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.patient_blood_pressure, menu);
        return true;
    }
    
}
