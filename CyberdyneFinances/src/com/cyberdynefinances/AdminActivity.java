package com.cyberdynefinances;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class AdminActivity extends Activity
{
	@Override
    protected void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_admin);

        populateView();
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
    
    private void populateView()
    {
    	OnClick l = new OnClick();
    	ArrayList<String> names = LoginHandler.getUsernames();
    	int prevId = 1;
    	ScrollView sv = (ScrollView)this.findViewById(R.id.admin_scroll);
    	RelativeLayout rl = (RelativeLayout)this.findViewById(R.id.admin_relative);
    	for(String name : names)
    	{
    		Button b = new Button(this);
    		b.setText(name);
    		int id = findId(prevId);
    		b.setId(id);
    		
    		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    		if(prevId!=1)
    			lp.addRule(RelativeLayout.BELOW, prevId);
    		lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
    		rl.addView(b, lp);
    		b.setOnClickListener(l);
    		
    		prevId = id;
    	}
    }
    
    private int findId(int id)
    {  
        View v = findViewById(id);  
        while (v != null){  
            v = findViewById(++id);  
        }  
        return ++id;  
    }
    
    class OnClick implements OnClickListener
    {
    	Button prevButton;
		@Override
		public void onClick(View view) 
		{
			Button b = (Button)view;
			String name = (String) b.getText();
			b.setClickable(false);
			if(prevButton!=null)
			{
				prevButton.setClickable(true);
			}
			Log.e("tag", name);
			prevButton = b;
		}
    	
    }
	
}
