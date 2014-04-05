package com.example.minderx;

import com.example.minderx.util.SystemUiHider;




import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.x_activity_login_relink_javafile_and_delete);

		if( in_connection_range() )
			start_login_listner();
		}


	/**
	 * The app checks to see if it is in range of the Hospitial
	 */

	private boolean in_connection_range(){
		return true;
		}

	private boolean is_authenticated(String password){
		return password.equals("o");
		}

	private void start_login_listner(){
		final Button btnLogin =  (Button) this.findViewById(R.id.login_button);
		final EditText edtLogin = (EditText) this.findViewById(R.id.password_txt);
		btnLogin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
	            imm.hideSoftInputFromWindow(btnLogin.getWindowToken(), 
	                                      InputMethodManager.RESULT_UNCHANGED_SHOWN);
				
				String password = edtLogin.getText().toString();

				if(is_authenticated(password)) {
					Intent patientListActivity = new Intent(v.getContext(), PatientListActivity.class);
					startActivityForResult(patientListActivity, 0);
					}
				else
				  Toast.makeText(v.getContext(), "Login Failed", Toast.LENGTH_LONG).show();

				// finish(); // on back, app will close, maybe consider saveing state, and running in background (in top bar?)
				}
			});			
		}
	

	}
