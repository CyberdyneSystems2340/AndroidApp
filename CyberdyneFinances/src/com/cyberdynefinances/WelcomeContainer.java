package com.cyberdynefinances;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

public class WelcomeContainer extends Activity
{

	//Called when this activity  is created sets up the fragmentManager and makes the Welcome Fragment the initial fragment to display
	@Override
    protected void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
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
    		//if admin display admin specific activity
    		if(username.equals("admin"))
    		{
	    		Intent intent = new Intent(this, AdminActivity.class);
	    		startActivity(intent);
    		}
    		//normal user load their accounts and display the normal account viewing activity
    		else
	    	{
	    		AccountManager.loadUser(username);
	    		Intent intent = new Intent(this, AccountContainer.class);
	    		startActivity(intent);
	    	}
    	}
    	else
    	{
    		Toast.makeText(this, "Incorrect username or password", Toast.LENGTH_LONG).show();
    	}
    }
    
    public void registerClicked(View view)
    {
    	View root2 = Fragments.RegisterFragment.root2;
    	EditText editText = (EditText) root2.findViewById(R.id.registerUsername);
    	String newName = editText.getText().toString();
    	editText = (EditText) root2.findViewById(R.id.registerPassword);
    	String newPassword = editText.getText().toString();
    	editText = (EditText) root2.findViewById(R.id.registerPasswordVerification);
    	String verifyPassword = editText.getText().toString();
    	
    	// Checks for blank username
    	if(newName.length() == 0)
    	{
    		Toast.makeText(this, "Invalid username", Toast.LENGTH_LONG).show();
    	}
    	// Check if the name exists or not
    	else if(LoginHandler.containsName(newName))
    	{
    		Toast.makeText(this, "Username already exists", Toast.LENGTH_LONG).show();
    	}
    	// Verify the password
    	else if(!(newPassword.equals(verifyPassword)))
    	{
    		Toast.makeText(this, "Retype Password", Toast.LENGTH_LONG).show();
    	}
    	// Then you only need to check the first password and see if it matches the given properties
    	// Then return back to the welcome screen
    	else if((newPassword.length() < 6) || !(newPassword.matches(".*\\d.*")))
    	{
    		Toast.makeText(this, "Password must contain 6 or more characters and a number",Toast.LENGTH_LONG).show();
    	}
    	else
    	{
    		LoginHandler.register(newName, newPassword); // Yay! you are a user now!!!!!
    		Toast.makeText(this, "Registration Successful",Toast.LENGTH_LONG).show();
    		Animation.fade(new Fragments.LoginFragment(), getFragmentManager(), R.id.container_welcome, true);
    	}
    }
}
