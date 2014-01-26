package com.cyberdynefinances;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    
    /**
     * This method is on call from the activity_main button. TestActivity was removed
     * and now lies within LoginActivity which activity_login will now link to 
     * activity_test. (K3n0b1)
     * 
     * @param v
     */
    public void welcomeLoginClicked(View v){
    	// Removed TestActivity and replace LoginActivity
    	Intent intent = new Intent(this, LoginActivity.class); 
    	startActivity(intent);
    }
    
    public void welcomeRegisterClicked(View v) {
    	
    }
    
}
