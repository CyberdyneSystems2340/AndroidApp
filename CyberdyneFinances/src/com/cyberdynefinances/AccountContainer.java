package com.cyberdynefinances;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.cyberdynefinances.dbManagement.DBHandler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.format.Time;
import android.util.Log;
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
		if (null == name || name.getText().toString().equals("")) {
		    Toast.makeText(this, "Incorrect Account Name ", Toast.LENGTH_LONG).show();
		    return;
		} else if (null == balance || balance.getText().toString().equals("") || balance.getText().toString().equals(".")) {
		    Toast.makeText(this, "Incorrect Balance ", Toast.LENGTH_LONG).show();
            return;
		} else if (null == interest || interest.getText().toString().equals("") || interest.getText().toString().equals(".")) {
		    Toast.makeText(this, "Incorrect Interest ", Toast.LENGTH_LONG).show();
	        return;
		} else {
    		String accountName=name.getText().toString();
    		ArrayList<Account> accounts = AccountManager.getAccountList();
            for (Account account:accounts) {
                if (account.getName().equals(accountName)) {
                    Toast.makeText(this, "Account Already Exists", Toast.LENGTH_LONG).show();
                    return;
                }
            }            
    		double balanceDouble=Double.parseDouble(balance.getText().toString());
    		if(balanceDouble < 0.01)
    		{
    			Toast.makeText(this, "Incorrect Balance ", Toast.LENGTH_LONG).show();
                return;
    		}
    		double interestDouble=Double.parseDouble(interest.getText().toString());
    		if(interestDouble < 0.01)
    		{
    			Toast.makeText(this, "Incorrect Interest ", Toast.LENGTH_LONG).show();
                return;
    		}
    		Account account=new Account(accountName,balanceDouble,interestDouble);    		
    		if (AccountManager.addAccount(account)) {
    		    Toast.makeText(this, "Account Creation Successful", Toast.LENGTH_LONG).show();
    		    Animation.fade(new Fragments.AccountHomeFragment(), getFragmentManager(), R.id.container_account, true);
    		} else {
    		    Toast.makeText(this, "Account Creation Failed", Toast.LENGTH_LONG).show();
    		}
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
        		if(!text.getText().toString().equals("") && !text.getText().toString().equals(".")) //check to make sure if there is something in the text field
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

        		if(amount>=0.01) //if amount is valid withdraw and notify user
        		{
        			if(AccountManager.withdraw(category, amount))
        				Toast.makeText(AccountContainer.this, "Withdrawal of "+NumberFormat.getCurrencyInstance().format(amount)+" Successful", Toast.LENGTH_LONG).show();
        			else
        			{
        				Toast.makeText(AccountContainer.this, "Amount of "+NumberFormat.getCurrencyInstance().format(amount)+" Exceeds Current Balance", Toast.LENGTH_LONG).show();
        				return;
        			}
        		}
        		else //else alert user because they didnt type anything or 0
        		{
        			Toast.makeText(AccountContainer.this, "Enter an Amount", Toast.LENGTH_LONG).show();
    				return;
        		}
        		updateBalance();
        		updateTransactionHistory();
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
		
		Spinner monthW = (Spinner) dialog_layout.findViewById(R.id.month_spinner_deposit);
        Spinner dayW = (Spinner) dialog_layout.findViewById(R.id.day_spinner_deposit);
        Spinner yearW = (Spinner) dialog_layout.findViewById(R.id.year_spinner_deposit);
		String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
		String[] days = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
		String[] years = {"2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014"};
		ArrayAdapter month = new ArrayAdapter(this, R.layout.layout_date_spinner, months); 
		ArrayAdapter day = new ArrayAdapter(this, R.layout.layout_date_spinner, days); 
		ArrayAdapter year = new ArrayAdapter(this, R.layout.layout_date_spinner, years);
		monthW.setAdapter(month);
		dayW.setAdapter(day);
		yearW.setAdapter(year);
		
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
        		Log.e("tag", text.getText().toString()+"");
        		if(!text.getText().toString().equals("") && !text.getText().toString().equals("."))
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
        		
        		if(amount>=0.01)
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
        		updateTransactionHistory();
    			alertDialog.dismiss();
            }
        });

		alertDialog.setView(dialog_layout);
		alertDialog.show();
    }
    
    private void updateBalance()
    {
    	TextView text = (TextView) this.findViewById(R.id.account_balance);
    	if(AccountManager.getActiveAccount().getBalance()>=1000000000f)
    	{
    		text.setScaleX(0.8f);
    		text.setScaleY(0.8f);
    	}
    	else
    	{
    		text.setScaleX(1f);
    		text.setScaleY(1f);
    	}
    	text.setText(NumberFormat.getCurrencyInstance().format(AccountManager.getActiveAccount().getBalance()));
    }
    
    private void updateTransactionHistory() {
        
        String[][] transactions = DBHandler.getTransactionHistory(AccountManager.getActiveAccount().getName());
        String rows = "";
        if (null != transactions) {
            for (String[] transaction : transactions) {
                rows += "\n\nAccount: " + transaction[0] + ", Amount: " + transaction[1] +
                        ", Type: " + transaction[2] + ", Category: " + transaction[3] +
                        ", Timestamp: " + transaction[4];
            }
        }
        TextView reportText = (TextView) this.findViewById(R.id.report_text_view);
        reportText.setText(rows);
    }
    
    private void updateReportText(String text)
    {
        TextView reportText = (TextView) this.findViewById(R.id.report_text_view);
        reportText.setText(text);
    }
    
    public void addAccountButton(View view)
    {
    	Animation.fade(new Fragments.AccountCreationFragment(), getFragmentManager(), R.id.container_account);
    }
    
    public void dateButtonClicked(View view)
    {
        View root = (View) view.getParent();
        String dayBegin = ((Spinner) root.findViewById(R.id.date_day_begin)).getSelectedItem().toString();
        String monthBegin = ((Spinner) root.findViewById(R.id.date_month_begin)).getSelectedItem().toString();
        String yearBegin = ((Spinner) root.findViewById(R.id.date_year_begin)).getSelectedItem().toString();
        String dayEnd = ((Spinner) root.findViewById(R.id.date_day_end)).getSelectedItem().toString();
        String monthEnd = ((Spinner) root.findViewById(R.id.date_month_end)).getSelectedItem().toString();
        String yearEnd = ((Spinner) root.findViewById(R.id.date_year_end)).getSelectedItem().toString();
        Map<String, Integer> monthMap = new HashMap<String,Integer>();
        
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
        int i=0;
        for(String m:months)
        {
            monthMap.put(m, i++);
        }
        
        int db = Integer.parseInt(dayBegin);
        int mb = monthMap.get(monthBegin);
        int yb = Integer.parseInt(yearBegin);
        int de = Integer.parseInt(dayEnd);
        int me = monthMap.get(monthEnd);
        int ye = Integer.parseInt(yearEnd);
        
        ArrayList<String> longMonths = new ArrayList<String>();
        String[] lm = {"Jan", "Mar", "May", "Jul", "Aug", "Oct", "Dec" };
        for(String m: lm)
        {
            longMonths.add(m);
        }
        if(!longMonths.contains(monthBegin))
        {
            if(db > 30)
            {
                db = 30;
            }
        }
        if(!longMonths.contains(monthEnd))
        {
            if(de > 30)
            {
                de = 30;
            }
        }
        if(monthBegin.equals("Feb") && db > 28)
        {
            db = 28;
        }
        if(monthEnd.equals("Feb") && de > 28)
        {
            de = 28;
        }
        
        Time begin = new Time();
        begin.setToNow();
        int sec = begin.second;
        int min = begin.minute;
        int hour = begin.hour;
        begin.set(sec, min, hour, db, mb, yb);
        Time end = new Time();
        end.setToNow();
        sec = end.second;
        min = end.minute;
        hour = end.hour;
        end.set(sec, min, hour, de, me, ye);
        
        if(begin.after(end))
        {
            Toast.makeText(AccountContainer.this, "Invalid Date", Toast.LENGTH_LONG).show();
            return;
        }    
        
        String report = ((Spinner)root.findViewById(R.id.report_spinner)).getSelectedItem().toString();
        //String text = AccountManager.getReport(report, begin, end);
        //updateReportText(text)
        
    }
}
