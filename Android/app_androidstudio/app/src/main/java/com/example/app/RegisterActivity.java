package com.example.app;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class RegisterActivity extends Activity {
	private EditText username;
	private EditText password;
	private TextView error;
    private MediaPlayer mp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		username = (EditText) findViewById(R.id.register_username);
		password = (EditText) findViewById(R.id.register_password);
		error = (TextView) findViewById(R.id.error_message);

        mp = MediaPlayer.create(this.getApplicationContext(), R.raw.b17);
	}
	
	public void register(final View v) {
		if (username.getText().length() == 0 || password.getText().length() == 0) {
			return;
		}
		
		v.setEnabled(false);
		ParseUser user = new ParseUser();
		user.setUsername(username.getText().toString());
		user.setPassword(password.getText().toString());
	
		user.signUpInBackground(new SignUpCallback() {
			@Override
			public void done(ParseException e) {
				if (e == null) {
					Intent intent = new Intent(RegisterActivity.this, UserAccountsActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    mp.start();
					startActivity(intent);
					finish();
				}
				else {
					switch(e.getCode()) {
					case ParseException.USERNAME_TAKEN:
						error.setText("Sorry, this username has already been taken.");
						break;
					case ParseException.USERNAME_MISSING:
						error.setText("Sorry, you must supply a username to register.");
						break;
					case ParseException.PASSWORD_MISSING:
						error.setText("Sorry, you must supply a password to register.");
						break;
					default:
						error.setText(e.getLocalizedMessage());
					}
					
					v.setEnabled(true);
				}
			}
		});
	}
	
	public void showLogin(View v) {
		startActivity(new Intent(this, LoginActivity.class));
		finish();
	}
}
