package com.example.sharedpreferencesandroid;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sharedPrefExtra.AlertDialogManager;
import com.example.sharedPrefExtra.SessionManager;

public class LoginActivity extends Activity {

	// Email, password edittext
    EditText txtUsername, txtPassword;
    
    static Map<String, String> login = new HashMap<String, String>();
     
    // login button
    Button btnLogin;
     
    // Alert Dialog Manager
    AlertDialogManager alert = new AlertDialogManager();
     
    // Session Manager Class
    SessionManager session;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_point);
		
		login.put("a", "a");
		// Session Manager
        session = new SessionManager(getApplicationContext());                
         
        // Email, Password input text
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword); 
         
        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();
         
         
        // Login button
        btnLogin = (Button) findViewById(R.id.btnLogin);
         
         
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
                    if(login.containsKey(username) && password.equals(login.get(username))) {
                         
                        // Creating user login session
                        // For testing i am stroing name, email as follow
                        // Use user real data
                        session.createLoginSession("Goran Colovic", "gorancolovic10@gmail.com");
                         
                        // Staring MainActivity
                        Intent i = new Intent(getApplicationContext(), LoggedInActivity.class);
                        startActivity(i);
                        finish();
                         
                    } else if (!login.containsKey(username)) {
                        // username / password doesn't match
                        alert.showAlertDialog(LoginActivity.this, "Login failed..", "Username is incorrect", false);
                    } else if (!password.equals(login.get(username))) {
                    	alert.showAlertDialog(LoginActivity.this, "Login failed..", "Password is incorrect", false);
                    }              
                } else {
                    // user didn't entered username or password
                    // Show alert asking him to enter the details
                    alert.showAlertDialog(LoginActivity.this, "Login failed..", "Please enter username and password", false);
                }
                 
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