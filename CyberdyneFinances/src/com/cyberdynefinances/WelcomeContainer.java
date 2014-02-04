package com.cyberdynefinances;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class WelcomeContainer extends Activity
{

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
    
    @Override
    public void onStop()
    {
    	super.onStop();
    	LoginHandler.writeTable(getSharedPreferences("CyberdynePrefsFile",0));
    }
    
    public void welcomeLoginClicked(View view)
    {
    	Animation.fade(new Fragments.LoginFragment(), getFragmentManager(), R.id.container_welcome);
    }
    
    public void welcomeRegisterClicked(View view)
    {
    	Animation.fade(new Fragments.RegisterFragment(), getFragmentManager(), R.id.container_welcome);
    }
}
