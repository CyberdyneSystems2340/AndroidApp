package com.cyberdynefinances;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.support.v4.app.NavUtils;

public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);	//disables the action bar
		setContentView(R.layout.activity_login);
		// Show the Up button in the action bar.
		//setupActionBar();		Not needed if there isnt going to be an action bar also gives nullPointerError is called when action bar is disabled
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	@SuppressWarnings("unused")
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}
	
	/**
	 * Created method when the login button is clicked from activity_login. This 
	 * will send you to the xml file: activity_test. (K3n0b1)
	 * 
	 * @param v
	 */
    public void loginClicked(View v){
    	Intent intent = new Intent(this, TestActivity.class);
    	startActivity(intent);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}