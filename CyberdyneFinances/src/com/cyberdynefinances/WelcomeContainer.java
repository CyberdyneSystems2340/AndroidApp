package com.cyberdynefinances;

import java.text.NumberFormat;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void buttonWithdrawal(View view)
    {
		final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		final View dialog_layout = getLayoutInflater().inflate(R.layout.withdrawal_dialog_layout, null);
		final EditText text = (EditText) dialog_layout.findViewById(R.id.withdrawal_dialog_text);
		final Spinner menu = (Spinner) dialog_layout.findViewById(R.id.withdrawal_dialog_menu);    
		final EditText other = (EditText) dialog_layout.findViewById(R.id.withdraw_dialog_other);
		
		ArrayList<String> cat = AccountManager.getActiveAccount().getCategoriseWithdraw();
		String[] s = new String[cat.size()+1];
		for(int i=0;i<cat.size();i++)
			s[i]=cat.get(i);
		s[cat.size()]="Other";
		ArrayAdapter a = new ArrayAdapter(this, R.layout.layout_category_spinner, s);
		menu.setAdapter(a);
		menu.setOnItemSelectedListener(new OnItemSelectedListener() //Called when an item in the spinner is clicked
		{
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) 
			{
				if(menu.getSelectedItem().toString().equals("Other") && other.getVisibility()==View.GONE) //if other is selected and the other category text field isnt visible, make it visible and remake the dialog box
				{
					other.setVisibility(View.VISIBLE);
					alertDialog.dismiss();
					alertDialog.setView(dialog_layout);
					alertDialog.show();
				}
				else if(!menu.getSelectedItem().toString().equals("Other") && other.getVisibility()==View.VISIBLE) //if other isnt selected and the other category text field is visible, make it gone and remake the dialog box
				{
					other.setVisibility(View.GONE);
					alertDialog.dismiss();
					alertDialog.setView(dialog_layout);
					alertDialog.show();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		
		Button confirm = (Button) dialog_layout.findViewById(R.id.withdrawal_dialog_button);
		confirm.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
        		double amount = 0.0;
        		if(!text.getText().toString().equals("")) //check to make sure if there is something in the text field
        			amount = Double.parseDouble(text.getText().toString());
        		
        		String category = menu.getSelectedItem().toString();
        		if(category.equals("Other")) //if other is selected but not category has been typed in alert user
        		{
        			category = other.getText().toString();
        			if(category.equals(""))
        			{
        				Toast.makeText(WelcomeContainer.this, "Enter a Category", Toast.LENGTH_LONG).show();
        				return;
        			}
        		}

        		if(amount!=0.0) //if amount is valid withdraw and notify user
        		{
        			if(AccountManager.withdraw(category, amount))
        				Toast.makeText(WelcomeContainer.this, "Withdrawal of "+NumberFormat.getCurrencyInstance().format(amount)+" Successful", Toast.LENGTH_LONG).show();
        			else
        			{
        				Toast.makeText(WelcomeContainer.this, "Amount of "+NumberFormat.getCurrencyInstance().format(amount)+" Exceds Current Balance", Toast.LENGTH_LONG).show();
        				return;
        			}
        		}
        		else //else alert user because they didnt type anything or 0
        		{
        			Toast.makeText(WelcomeContainer.this, "Enter an Amount", Toast.LENGTH_LONG).show();
    				return;
        		}
        		updateBalance();
        		alertDialog.dismiss();
            }
        });
		
		alertDialog.setView(dialog_layout);
		alertDialog.show();
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public void buttonDeposit(View view)
    {
		final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		final View dialog_layout = getLayoutInflater().inflate(R.layout.deposit_dialog_layout, null);
		final EditText text = (EditText) dialog_layout.findViewById(R.id.deposit_dialog_text);
		final Spinner menu = (Spinner) dialog_layout.findViewById(R.id.deposit_dialog_menu);     
		final EditText other = (EditText) dialog_layout.findViewById(R.id.deposit_dialog_other);
		
		ArrayList<String> cat = AccountManager.getActiveAccount().getCategoriesDeposit();
		String[] s = new String[cat.size()+1];
		for(int i=0;i<cat.size();i++)
			s[i]=cat.get(i);
		s[cat.size()]="Other";
		ArrayAdapter a = new ArrayAdapter(this, R.layout.layout_category_spinner, s);
		menu.setAdapter(a);
		menu.setOnItemSelectedListener(new OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) 
			{
				if(menu.getSelectedItem().toString().equals("Other") && other.getVisibility()==View.GONE)
				{
					other.setVisibility(View.VISIBLE);
					alertDialog.dismiss();
					alertDialog.setView(dialog_layout);
					alertDialog.show();
				}
				else if(!menu.getSelectedItem().toString().equals("Other") && other.getVisibility()==View.VISIBLE)
				{
					other.setVisibility(View.GONE);
					alertDialog.dismiss();
					alertDialog.setView(dialog_layout);
					alertDialog.show();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		
		Button confirm = (Button) dialog_layout.findViewById(R.id.deposit_dialog_button);
		confirm.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
        		double amount = 0.0;
        		if(!text.getText().toString().equals(""))
        			amount = Double.parseDouble(text.getText().toString());
        		
        		String category = menu.getSelectedItem().toString();
        		if(category.equals("Other"))
        		{
        			category = other.getText().toString();
        			if(category.equals(""))
        			{
        				Toast.makeText(WelcomeContainer.this, "Enter a Category", Toast.LENGTH_LONG).show();
        				return;
        			}
        		}
        		
        		if(amount!=0.0)
        		{
        			AccountManager.deposit(category, amount);
        			Toast.makeText(WelcomeContainer.this, "Deposit of "+NumberFormat.getCurrencyInstance().format(amount)+" Successful", Toast.LENGTH_LONG).show();
        		}
        		else
        		{
        			Toast.makeText(WelcomeContainer.this, "Enter an Amount", Toast.LENGTH_LONG).show();
    				return;
        		}
        		updateBalance();
    			alertDialog.dismiss();
            }
        });

		alertDialog.setView(dialog_layout);
		alertDialog.show();
    }
    
    private void updateBalance()
    {
    	TextView text = (TextView) this.findViewById(R.id.account_balance);
    	text.setText("Balance: "+NumberFormat.getCurrencyInstance().format(AccountManager.getActiveAccount().getBalance()));
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
    		Animation.fade(new Fragments.LoginFragment(), getFragmentManager(), R.id.container_welcome);
    	}
    }
    
    //opens the account home page for testing
    public void test(View view)
    {
    	Animation.fade(new Fragments.AccountHomeFragment(), getFragmentManager(), R.id.container_welcome);
    }
}
