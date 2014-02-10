package com.cyberdynefinances;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
<<<<<<< HEAD
//import android.widget.EditText;
=======
import android.widget.EditText;
import android.widget.Toast;
>>>>>>> 91da6ac8069a625985486fe712b4afe3dadd61f8

public class WelcomeContainer extends Activity
{

	//Called when this activity  is created sets up the fragmentManager and makes the Welcome Fragment the initial fragment to display
	@Override
    protected void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        LoginHandler.readTable(getSharedPreferences("CyberdynePrefsFile",0));
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.frame_welcome);
        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.container_welcome, new Fragments.WelcomeFragment())
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
        switch (item.getItemId()) 
        {
            case android.R.id.home:
                // Navigate "up" the demo structure to the launchpad activity.
                // See http://developer.android.com/design/patterns/navigation.html for more.
                NavUtils.navigateUpTo(this, new Intent(this, WelcomeContainer.class));
                return true;

        }

        return super.onOptionsItemSelected(item);
    }
    
    //Called when the app is closed to write out data that needs to be stored
    @Override
    public void onStop()
    {
    	super.onStop();
    	LoginHandler.writeTable(getSharedPreferences("CyberdynePrefsFile",0));
    }
    
    //Login button on the welcome screen
    public void welcomeLoginClicked(View view)
    {
    	Animation.fade(new Fragments.LoginFragment(), getFragmentManager(), R.id.container_welcome);
    }
    
    //Register button on the welcome screen
    public void welcomeRegisterClicked(View view)
    {
    	Animation.fade(new Fragments.RegisterFragment(), getFragmentManager(), R.id.container_welcome);
    }
    
<<<<<<< HEAD
    public void loginClicked(View view)
    {
    	//EditText editText = (EditText) view.findViewById(R.id.usernameEditText);
    	//were username and password would be retrieved from the text fields and passed to a verifing class
    	
    	Animation.fade(new Fragments.TestFragment(), getFragmentManager(), R.id.container_welcome);
=======
    //Login button on the login screen
    //Gets the text inside the text fields and calls a validation method in LoginHandler to then transition to the next screen or display an error notification
    public void loginClicked(View view)
    {
    	View root = Fragments.LoginFragment.root;
    	EditText editText = (EditText) root.findViewById(R.id.usernameEditText);
    	String username = editText.getText().toString();
    	editText = (EditText) root.findViewById(R.id.passwordEditText);
    	String password = editText.getText().toString();
    	if(LoginHandler.isValidLogin(username, password))
    	{
    		Animation.fade(new Fragments.TestFragment(), getFragmentManager(), R.id.container_welcome);
    	}
    	else
    	{
    		Toast.makeText(this, "Incorrect username or password", Toast.LENGTH_LONG).show();
    	}
>>>>>>> 91da6ac8069a625985486fe712b4afe3dadd61f8
    }
}
