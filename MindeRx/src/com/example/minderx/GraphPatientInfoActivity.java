package com.example.minderx;

import android.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class GraphPatientInfoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_patient_info);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.graph_patient_info, menu);
        return true;
    }
    
}
