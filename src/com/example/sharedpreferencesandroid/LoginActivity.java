package com.example.sharedpreferencesandroid;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sharedPrefExtra.AlertDialogManager;
import com.example.sharedPrefExtra.SessionManager;

public class LoginActivity extends Activity {

	// Email, password edittext
    EditText txtUsername, txtPassword;
    
    static Map<String, String> mapOfUsers = new HashMap<String, String>();
     
    // login button
    Button btnLogin, btnSignUp;
     
    // Alert Dialog Manager
    AlertDialogManager alert = new AlertDialogManager();
     
    // Session Manager Class
    SessionManager session;
    
    //Context
    final Context context = this;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_point);
		
		mapOfUsers.put("a", "a");
		// Session Manager
        session = new SessionManager(getApplicationContext());                
         
        // Email, Password input text
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword); 
         
        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();
         
         
        // Login button
        btnLogin = (Button) findViewById(R.id.btnLogin);
        
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
         
         
        // Login button click event
        btnLogin.setOnClickListener(new View.OnClickListener() {
             
            @Override
            public void onClick(View arg0) {
                // Get username, password from EditText
                String username = txtUsername.getText().toString();
                String password = txtPassword.getText().toString();
                 
                // Check if username, password is filled                
                if(username.trim().length() > 0 && password.trim().length() > 0) {
                    
                	// For testing puspose username, password is checked with sample data
                    // username = test
                    // password = test
                    if(mapOfUsers.containsKey(username) && password.equals(mapOfUsers.get(username))) {
                         
                        // Creating user login session
                        // For testing i am stroing name, email as follow
                        // Use user real data
                        session.createLoginSession(username, username + "@gmail.com");
                         
                        // Staring MainActivity
                        Intent i = new Intent(getApplicationContext(), LoggedInActivity.class);
                        startActivity(i);
                        finish();
                         
                    } else if (!mapOfUsers.containsKey(username)) {
                        // username / password doesn't match
                        alert.showAlertDialog(LoginActivity.this, "Login failed..", "Username is incorrect", false);
                    } else if (!password.equals(mapOfUsers.get(username))) {
                    	alert.showAlertDialog(LoginActivity.this, "Login failed..", "Password is incorrect", false);
                    }              
                } else {
                    // user didn't entered username or password
                    // Show alert asking him to enter the details
                    alert.showAlertDialog(LoginActivity.this, "Login failed..", "Please enter username and password", false);
                }
                 
            }
        });    
        
        // Sign up button click event
        btnSignUp.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				// custom dialog
				final Dialog dialog = new Dialog(context);
				dialog.setContentView(R.layout.sign_up_dialog);
				dialog.setTitle("Sign up screen");
	 
		        txtUsername = (EditText) dialog.findViewById(R.id.eTSignUpUsername);
		        txtPassword = (EditText) dialog.findViewById(R.id.eTSignUpPassword); 
	 
				Button signUpButton = (Button) dialog.findViewById(R.id.btnSignUpCheck);
				// if button is clicked, close the custom dialog
				signUpButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						
				        String username = txtUsername.getText().toString();
		                String password = txtPassword.getText().toString();
						
						if (mapOfUsers.containsKey(username) || !(username.trim().length() > 0 && password.trim().length() > 0)) {
							
							 alert.showAlertDialog(LoginActivity.this, "Sign up failed..", "Enter username", false);
							
						} else {
							mapOfUsers.put(username, password);
							
							session.createLoginSession(username, username + "@gmail.com");
							
	                        // Staring MainActivity
	                        Intent i = new Intent(getApplicationContext(), LoggedInActivity.class);
	                        startActivity(i);
	                        finish();
	                        dialog.dismiss();
						}
							
					}
				});
				
				Button cancel = (Button) dialog.findViewById(R.id.btnCancel);
				// if button is clicked, close the custom dialog
				cancel.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
	 
				dialog.show();
				
			}
        });
        
	}

	@Override
	protected void onResume() {
		super.onResume();
		Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();
		
		if(session.isLoggedIn()) {
            Intent i = new Intent(getApplicationContext(), LoggedInActivity.class);
            startActivity(i);
		}
		
	}
	
}