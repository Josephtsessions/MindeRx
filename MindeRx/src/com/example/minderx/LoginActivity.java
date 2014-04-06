package com.example.minderx;






import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.x_activity_login_relink_javafile_and_delete);

		setupListeners();
	}

	private boolean validLogin(String username, String password){
		return password.equals("o");
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
					Intent patientListActivity = new Intent(v.getContext(), PatientListActivity.class);
					startActivityForResult(patientListActivity, 0);
				} else {
					Toast.makeText(v.getContext(), "Login Failed", Toast.LENGTH_LONG).show();
				}
			}
		});		
	
	}

}