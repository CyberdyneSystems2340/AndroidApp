package com.example.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends Activity {
	private EditText username;
	private EditText password;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		username = (EditText) findViewById(R.id.register_username);
		password = (EditText) findViewById(R.id.register_password);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void login() {
		ParseUser.logInInBackground(username.toString(), password.toString(), new LogInCallback() {
			@Override
			public void done(ParseUser user, ParseException e) {
				if (user != null && e == null) {
					startActivity(new Intent(LoginActivity.this, AccountsActivity.class));
					finish();
				}
				else {
					switch(e.getCode()) {
						case ParseException.USERNAME_TAKEN:
							break;
						default:
							break;
					}
				}
			}
			
		});
	}
	
	public void showRegister(View v) {
		startActivity(new Intent(this, RegisterActivity.class));
		finish();
	}
}
