package com.cyberdynefinances;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class AdminActivity extends Activity
{
	Button del;
	
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
		s.setOnItemSelectedListener(new OnClick());
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
    
    class OnClick implements OnItemSelectedListener
    {
		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) 
		{
			TextView s = (TextView)arg1;
			Log.e("tag", s.getText().toString());
			
		}
		@Override
		public void onNothingSelected(AdapterView<?> arg0) 
		{
			// TODO Auto-generated method stub
			
		}
    	
    }
    

	public void delete(View view) 
	{
		Button b = (Button)view;
		View v = (View) view.getParent();
		Spinner s = (Spinner) v.findViewById(R.id.spinner);
		String name = s.getSelectedItem().toString();
		LoginHandler.remove(name);
		AdminActivity.this.updateView();
	}
	
	public void reset(View view)
	{
		Button b = (Button)view;
		View v = (View) view.getParent();
		Spinner s = (Spinner) v.findViewById(R.id.spinner);
		String name = s.getSelectedItem().toString();
		LoginHandler.remove(name);
		LoginHandler.register(name, "pass123");
		AdminActivity.this.updateView();
	}
    
    
	
}
