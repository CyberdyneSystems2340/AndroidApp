package com.example.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.media.MediaPlayer;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends Activity {
	private EditText username;
	private EditText password;
    private MediaPlayer mp;

    /**
     * Activity Creation
     * @param savedInstanceState buffered information bundle from the last activity
     */
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		username = (EditText) findViewById(R.id.register_username);
		password = (EditText) findViewById(R.id.register_password);

        mp = MediaPlayer.create(this.getApplicationContext(), R.raw.b17);
	}

    /**
     *
     * @param menu
     * @return
     */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void login(final View view) {
        view.setEnabled(false);
		ParseUser.logInInBackground(username.getText().toString(), password.getText().toString(), new LogInCallback() {
			@Override
			public void done(ParseUser user, ParseException e) {
				if (user != null && e == null) {
                    Intent intent = new Intent(LoginActivity.this, UserAccountsActivity.class);
                    // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    mp.start();

					startActivity(intent);
					finish();
				}
				else {
                    view.setEnabled(true);
                    Log.e("LoginActivity", "Login failed!");
                    // TODO catch all of these errors
					switch(e.getCode()) {
						case ParseException.USERNAME_TAKEN:
                            Toast.makeText(getApplicationContext(), "Login Failed\nUsername Taken", Toast.LENGTH_SHORT).show();
							break;
						default:
                            Toast.makeText(getApplicationContext(), "Login Failed\nIncorrect Password", Toast.LENGTH_SHORT).show();
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
