package com.cyberdynefinances;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {
//	public final static String INPUT_USERNAME = "com.cyberdynefinances.MESSAGE";
//	public final static String INPUT_PASSWORD = "com.cyberdynefinances.MESSAGE";

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
    
    public void loginButtonClicked(View V){
    	Intent intent = new Intent(this, LoginClickedActivity.class);
    	EditText usernameText = (EditText) findViewById(R.id.username_input);
    	EditText passwordText = (EditText) findViewById(R.id.password_input);
    	String un = usernameText.getText().toString();
    	String pw = passwordText.getText().toString();
    	intent.putExtra("username", un);
    	intent.putExtra("password", pw);
    	startActivity(intent);
    }
}
