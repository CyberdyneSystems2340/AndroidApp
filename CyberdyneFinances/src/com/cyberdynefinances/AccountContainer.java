package com.cyberdynefinances;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

public class AccountContainer extends Activity
{

	@Override
    protected void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.frame_account);
        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.container_account, new Fragments.AccountCreationFragment())
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
	
	public void accountCreation(View view)
	{
		View v = (View)view.getParent();
		EditText name=(EditText)v.findViewById(R.id.account_name);
		EditText balance=(EditText)v.findViewById(R.id.balance);
		EditText interest=(EditText)v.findViewById(R.id.interest);
		String accountName=name.getText().toString();
		double balanceDouble=Double.parseDouble(balance.getText().toString());
		double interestDouble=Double.parseDouble(interest.getText().toString());
		Account account=new Account(accountName,balanceDouble,interestDouble);
		AccountManager.addAccount(account);
		Animation.fade(new Fragments.AccountFragment(), getFragmentManager(), R.id.container_account);
	}
	
	public void buttonWithdrawal(View view){
		AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(this);
	}
}
