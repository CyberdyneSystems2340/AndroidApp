package com.example.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.EditText;

import com.parse.ParseACL;
import com.parse.ParseUser;

public class AddAccountDialog extends Activity {
	private EditText _newAccountName;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addaccount);
		
		getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		
		_newAccountName = (EditText) findViewById(R.id.new_account_name);
	}
	
	public void createNewAccount(View v) {
		if (_newAccountName.getText().length() > 0) {
			UserAccount uac = new UserAccount();
			uac.setACL(new ParseACL(ParseUser.getCurrentUser()));
			uac.setUser(ParseUser.getCurrentUser());
			uac.setAccountName(_newAccountName.getText().toString());
			uac.saveEventually();
		}
		
		finish();
	}
}
