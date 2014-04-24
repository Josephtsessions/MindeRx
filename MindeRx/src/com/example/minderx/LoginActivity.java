package com.example.minderx;


import com.example.minderx.PatientListActivity;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import database.MindeRxDatabase;

public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		setupListeners();
	}

	private void setupListeners() {
		final Button btnLogin =  (Button) this.findViewById(R.id.login_button);
		final EditText usernameField = (EditText) this.findViewById(R.id.username_txt);
		final EditText passwordField = (EditText) this.findViewById(R.id.password_txt);
		
		btnLogin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
	            imm.hideSoftInputFromWindow(btnLogin.getWindowToken(), 
	                                      InputMethodManager.RESULT_UNCHANGED_SHOWN);
				
	            String username = usernameField.getText().toString();
				String password = passwordField.getText().toString();

				if (validLogin(username, password)) {
					Intent intent = new Intent(v.getContext(), PatientListActivity.class);
					
					String userESSN = queryForEssn(username);
					intent.putExtra("ESSN", userESSN);
					
					startActivityForResult(intent, 0);
				} else {
					Toast.makeText(v.getContext(), "Login Failed", Toast.LENGTH_LONG).show();
				}
			}
		});		
	
	}

	private boolean validLogin(String username, String password){
		
		if (username.equals("") || password.equals("")) {return false;}
		
		MindeRxDatabase db = new MindeRxDatabase(this);
		
		String correctPassword = db.getPasswordFromUsername(username);
		
		return password.equals(correctPassword);
	}

	private String queryForEssn(String username) {
		MindeRxDatabase db = new MindeRxDatabase(this);
		
		return db.getESSNFromUsername(username);
	}

}