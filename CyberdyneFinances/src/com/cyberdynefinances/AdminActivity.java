package com.cyberdynefinances;

import java.util.ArrayList;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class AdminActivity extends Activity
{
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    protected void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_admin);

        ArrayList<String> names = LoginHandler.getUsernames();
        Spinner s = (Spinner) this.findViewById(R.id.spinner);
		String[] spinnerArray = new String[names.size()];
		int i=0;
		for(String name: names)
		{
			spinnerArray[i++] = name;
		}
    	ArrayAdapter a = new ArrayAdapter(this, R.layout.layout_spinner, spinnerArray);
    	s.setAdapter(a);
    }
	
    //Called when the app is closed to write out data that needs to be stored
    @Override
    public void onStop()
    {
    	super.onStop();
    	LoginHandler.writeTable(getSharedPreferences("CyberdynePrefsFile",0));
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
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	private void updateView()
    {
    	ArrayList<String> names = LoginHandler.getUsernames();
        Spinner s = (Spinner) this.findViewById(R.id.spinner);
		String[] spinnerArray = new String[names.size()];
		int i=0;
		for(String name: names)
		{
			spinnerArray[i++] = name;
		}
    	ArrayAdapter a = new ArrayAdapter(this, R.layout.layout_spinner, spinnerArray);
    	s.setAdapter(a);
    }
    
	public void delete(View view) 
	{
		final View v = (View) view.getParent();
		new AlertDialog.Builder(this)
        .setIcon(android.R.drawable.ic_dialog_alert)
        .setTitle("Delete User")
        .setMessage("Are you sure you want to delete this user?")
        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
        	@Override
        	public void onClick(DialogInterface dialog, int which) 
        	{
        		Spinner s = (Spinner) v.findViewById(R.id.spinner);
        		String name = s.getSelectedItem().toString();
        		LoginHandler.remove(name);
        		AdminActivity.this.updateView();
        	}
        })
        .setNegativeButton("No", null)
        .show();
	}
	
	public void reset(View view)
	{
		final View v = (View) view.getParent();
		new AlertDialog.Builder(this)
        .setIcon(android.R.drawable.ic_dialog_alert)
        .setTitle("Reset Password")
        .setMessage("Are you sure you want to reset this user's password?")
        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
        	@Override
        	public void onClick(DialogInterface dialog, int which) 
        	{
        		Spinner s = (Spinner) v.findViewById(R.id.spinner);
        		String name = s.getSelectedItem().toString();
        		LoginHandler.remove(name);
        		LoginHandler.register(name, "pass123");
        		AdminActivity.this.updateView();
        	}
        })
        .setNegativeButton("No", null)
        .show();
	}
}
