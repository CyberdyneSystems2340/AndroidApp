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
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class AccountContainer extends Activity
{
	@Override
    protected void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.frame_account);
        if (savedInstanceState == null) 
        {
        	if(AccountManager.getActiveAccount()==null)
        	{
        		getFragmentManager()
                	.beginTransaction()
                	.add(R.id.container_account, new Fragments.AccountCreationFragment())
                	.commit();
        	}
        	else
        	{
        		getFragmentManager()
                    .beginTransaction()
                    .add(R.id.container_account, new Fragments.AccountHomeFragment())
                    .commit();
        	}
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
                NavUtils.navigateUpTo(this, new Intent(this, AccountContainer.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
	
	public void accountCreationClicked(View view)
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
		Animation.fade(new Fragments.AccountHomeFragment(), getFragmentManager(), R.id.container_account, true);
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
        				Toast.makeText(AccountContainer.this, "Enter a Category", Toast.LENGTH_LONG).show();
        				return;
        			}
        		}

        		if(amount!=0.0) //if amount is valid withdraw and notify user
        		{
        			if(AccountManager.withdraw(category, amount))
        				Toast.makeText(AccountContainer.this, "Withdrawal of "+NumberFormat.getCurrencyInstance().format(amount)+" Successful", Toast.LENGTH_LONG).show();
        			else
        			{
        				Toast.makeText(AccountContainer.this, "Amount of "+NumberFormat.getCurrencyInstance().format(amount)+" Exceds Current Balance", Toast.LENGTH_LONG).show();
        				return;
        			}
        		}
        		else //else alert user because they didnt type anything or 0
        		{
        			Toast.makeText(AccountContainer.this, "Enter an Amount", Toast.LENGTH_LONG).show();
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
        				Toast.makeText(AccountContainer.this, "Enter a Category", Toast.LENGTH_LONG).show();
        				return;
        			}
        		}
        		
        		if(amount!=0.0)
        		{
        			AccountManager.deposit(category, amount);
        			Toast.makeText(AccountContainer.this, "Deposit of "+NumberFormat.getCurrencyInstance().format(amount)+" Successful", Toast.LENGTH_LONG).show();
        		}
        		else
        		{
        			Toast.makeText(AccountContainer.this, "Enter an Amount", Toast.LENGTH_LONG).show();
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
}
