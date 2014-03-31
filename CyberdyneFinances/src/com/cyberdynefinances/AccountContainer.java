package com.cyberdynefinances;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.cyberdynefinances.dbManagement.DBHandler;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.format.Time;
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

/**
 * @author Cyberdyne Finances
 * Container for all fragments relating to account management and viewing
 */
//CHECKSTYLE:OFF    suppresses error of Class Fan-Out Complexity
public class AccountContainer extends Activity
//CHECKSTYLE:ON
{
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.frame_account);
        if (savedInstanceState == null) 
        {
            if (AccountManager.getActiveAccount() == null)
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
	
    /**
     * Called when the create account button is clicked.
     * @param view The create account button's view
     */
    //CHECKSTYLE:OFF    suppresses error of Cyclomatic Complexity
    public void accountCreationClicked(View view)
    //CHECKSTYLE:ON
    {
        View v = (View) view.getParent();
        EditText name = (EditText) v.findViewById(R.id.account_name);
        EditText balance = (EditText) v.findViewById(R.id.balance);
        EditText interest = (EditText) v.findViewById(R.id.interest);
        if (null == name || name.getText().toString().equals("")) 
        {
            Toast.makeText(this, "Incorrect Account Name ", Toast.LENGTH_LONG).show();
            return;
        }
        //CHECKSTYLE:OFF    suppresses error of String "." appears 4 times in this file
        else if (null == balance || balance.getText().toString().equals("") || balance.getText().toString().equals(".")) 
        //CHECKSTYLE:ON 
        {
            //CHECKSTYLE:OFF    suppresses error of String "Incorrect Balance" appears 2 times in this file
            Toast.makeText(this, "Incorrect Balance ", Toast.LENGTH_LONG).show();
            //CHECKSTYLE:ON
            return;
        } 
        else if (null == interest || interest.getText().toString().equals("") || interest.getText().toString().equals(".")) 
        {
            //CHECKSTYLE:OFF    suppresses error of String "Incorrect Interest" appears 2 times in this file
            Toast.makeText(this, "Incorrect Interest ", Toast.LENGTH_LONG).show();
            //CHECKSTYLE:ON
            return;
        } 

        String accountName = name.getText().toString();
        ArrayList<Account> accounts = AccountManager.getAccountList();
        for (Account account:accounts) {
            if (account.getName().equals(accountName)) {
                Toast.makeText(this, "Account Already Exists", Toast.LENGTH_LONG).show();
                return;
            }
        }            
        double balanceDouble = Double.parseDouble(balance.getText().toString());
        if (balanceDouble < 0.01)
        {
            Toast.makeText(this, "Incorrect Balance ", Toast.LENGTH_LONG).show();
            return;
        }
        double interestDouble = Double.parseDouble(interest.getText().toString());
        if (interestDouble < 0.01)
        {
            Toast.makeText(this, "Incorrect Interest ", Toast.LENGTH_LONG).show();
            return;
        }
        Account account = new Account(accountName, balanceDouble, interestDouble);
        if (AccountManager.addAccount(account)) 
        {
            Toast.makeText(this, "Account Creation Successful", Toast.LENGTH_LONG).show();
		    
		    /** This is a quick and easy way to create sound effects for our program.
		    Not much to it, just place and .wav sound in the res/raw folder and
		    R will create a variable for it in its class once you refresh it.
		    Then you change the R in these four lines and mPlayer.start() it when
		    you are ready to play it */
            SoundPool sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
            int iTmp = sp.load(AccountContainer.this, R.raw.chaching, 1);
            sp.play(iTmp, 1, 1, 0, 0, 1);
            MediaPlayer mPlayer = MediaPlayer.create(AccountContainer.this, R.raw.chaching); // in 2nd param u have to pass your desired ringtone
		    
            Animation.fade(new Fragments.AccountHomeFragment(), getFragmentManager(), R.id.container_account, true);
		    
            mPlayer.start(); // I had to make the player start after you fade or else it will interrupt the sound
        } 
        else 
        {
            Toast.makeText(this, "Account Creation Failed", Toast.LENGTH_LONG).show();
        }
    }

	/**
	 * Creates a new page that will graph all the transactions made on a line graph.
	 * If no transactions were made, then Toast will notify the user. The average of
	 * the transactions made will be the y-axis midpoint.
	 *
	 * @param View object
	 * @author Clayton Pierce
	 */
    /*
	public void buttonLineGraph(View view)
	{
		String[][] transactions = DBHandler.getTransactionHistory(AccountManager.getActiveAccount().getName());
		if(transactions == null) // Check if a transaction is made
		{
			Toast.makeText(this, "No transactions made", Toast.LENGTH_LONG).show();
		}
		else
		{
			Animation.fade(new Fragments.GraphLayoutFragment(), getFragmentManager(), R.id.container_account, true);
			//setContentView(R.id.graph1);
			
			
			/**
			GraphView.GraphViewData[] data = new GraphView.GraphViewData[50];
			String[] horzArr = new String[] {"0", "3pi/2", "5pi/2", "7pi/2", "9pi/2", "11pi/2", "13pi/2", 
											"15pi/2", "17/2", "19/2", "21/2"};
			for(int i = 0; i < 50; i++)
			{
				data[i] = new GraphView.GraphViewData(i, Math.sin(i));
			}
			GraphViewSeries exampleSeries = new GraphViewSeries(data);
			 
			GraphView graphView = new LineGraphView(
			      this // context
			      , "Pi And Stuff" // heading
			);
			
			graphView.setHorizontalLabels(horzArr);
			graphView.setVerticalLabels(new String[] {"1", "0", "-1"});
			
			graphView.addSeries(exampleSeries); // data
			 
			LinearLayout layout = (LinearLayout) findViewById(R.id.graph1);
			Toast.makeText(this, "success", Toast.LENGTH_LONG).show();
			layout.addView(graphView); 
		}
	}*/
	
    /**
     * Called when the withdraw button is clicked. Displays a dialog box to input withdraw information.
     * @param view The withdraw button's view
     */
    //CHECKSTYLE:OFF    suppresses error of NCSS for this method is 79
    public void buttonWithdrawal(View view)
    //CHECKSTYLE:ON
    {
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        final View dialogLayout = getLayoutInflater().inflate(R.layout.withdrawal_dialog_layout, null);
        final EditText text = (EditText) dialogLayout.findViewById(R.id.withdrawal_dialog_text);
        final Spinner menu = (Spinner) dialogLayout.findViewById(R.id.withdrawal_dialog_menu);    
        final EditText other = (EditText) dialogLayout.findViewById(R.id.withdraw_dialog_other);
		
        ArrayList<String> cat = AccountManager.getActiveAccount().getCategoriseWithdraw();
        String[] s = new String[cat.size() + 1];
        for (int i = 0; i < cat.size(); i++)
        {
            s[i] = cat.get(i);
        }
        //CHECKSTYLE:OFF    suppresses error of String "Other" appears 8 times in this file
        s[cat.size()] = "Other";
        //CHECKSTYLE:ON
        ArrayAdapter<?> a = new ArrayAdapter<Object>(this, R.layout.layout_category_spinner, s);
        menu.setAdapter(a);
		
        final Spinner monthW = (Spinner) dialogLayout.findViewById(R.id.month_spinner_w);
        final Spinner dayW = (Spinner) dialogLayout.findViewById(R.id.day_spinner_w);
        final Spinner yearW = (Spinner) dialogLayout.findViewById(R.id.year_spinner_w);
        //CHECKSTYLE:OFF    suppresses error of Strings occur multiple times in this file
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
        String[] days = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
        String[] years = {"2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014"};
        //CHECKSTYLE:ON 
        ArrayAdapter<?> month = new ArrayAdapter<Object>(this, R.layout.layout_date_spinner, months); 
        ArrayAdapter<?> day = new ArrayAdapter<Object>(this, R.layout.layout_date_spinner, days); 
        ArrayAdapter<?> year = new ArrayAdapter<Object>(this, R.layout.layout_date_spinner, years);
        monthW.setAdapter(month);
        dayW.setAdapter(day);
        yearW.setAdapter(year);
        
        menu.setOnItemSelectedListener(new OnItemSelectedListener() //Called when an item in the spinner is clicked
        {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) 
            {
                if (menu.getSelectedItem().toString().equals("Other") && other.getVisibility() == View.GONE) //if other is selected and the other category text field isnt visible, make it visible and remake the dialog box
                {
                    other.setVisibility(View.VISIBLE);
                    alertDialog.dismiss();
                    alertDialog.setView(dialogLayout);
                    alertDialog.show();
                }
                else if (!menu.getSelectedItem().toString().equals("Other") && other.getVisibility() == View.VISIBLE) //if other isnt selected and the other category text field is visible, make it gone and remake the dialog box
                {
                    other.setVisibility(View.GONE);
                    alertDialog.dismiss();
                    alertDialog.setView(dialogLayout);
                    alertDialog.show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) { }
        });
		
        Button confirm = (Button) dialogLayout.findViewById(R.id.withdrawal_dialog_button);
        confirm.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                double amount = 0.0;
                if (!text.getText().toString().equals("") && !text.getText().toString().equals(".")) //check to make sure if there is something in the text field
                {
                    amount = Double.parseDouble(text.getText().toString());
                }
        		
                String category = menu.getSelectedItem().toString();
                if (category.equals("Other")) //if other is selected but not category has been typed in alert user
                {
                    category = other.getText().toString();
                    if (category.equals(""))
                    {
                        //CHECKSTYLE:OFF    suppresses error of String "Enter a Category appears 2 times
                        Toast.makeText(AccountContainer.this, "Enter a Category", Toast.LENGTH_LONG).show();
                        //CHECKSTYLE:ON
                        return;
                    }
                }

                if (amount >= 0.01) //if amount is valid withdraw and notify user
                {
                    Map<String, Integer> monthMap = new HashMap<String, Integer>();
                    String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
                    int i = 0;
                    for (String m:months)
                    {
                        monthMap.put(m, i++);
                    }
                    String m = monthW.getSelectedItem().toString();
                    String d = dayW.getSelectedItem().toString();
                    String y = yearW.getSelectedItem().toString();
                    Time date = new Time();
                    //timestamp = time.format("%d.%m.%Y %H:%M:%S");
                    date.setToNow();
                    int sec = date.second;
                    int min = date.minute;
                    int hour = date.hour;
                    date.set(sec, min, hour, Integer.parseInt(d), monthMap.get(m), Integer.parseInt(y));
                    //CHECKSTYLE:OFF    suppresses error of Strings appear multiple times
                    if (AccountManager.withdrawWithDate(category, amount, date.format("%d.%m.%Y %H:%M:%S")))
                    {
                        Toast.makeText(AccountContainer.this, "Withdrawal of " + NumberFormat.getCurrencyInstance().format(amount) + " Successful", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(AccountContainer.this, "Amount of " + NumberFormat.getCurrencyInstance().format(amount) + " Exceeds Current Balance", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                else //else alert user because they didnt type anything or 0
                {
                    Toast.makeText(AccountContainer.this, "Enter an Amount", Toast.LENGTH_LONG).show();
                    //CHECKSTYLE:ON
                    return;
                }
                updateBalance();
                updateTransactionHistory();
                alertDialog.dismiss();
            }
        });
		
        alertDialog.setView(dialogLayout);
        alertDialog.show();
    }
    
    /**
     * Called when the deposit button is clicked. Displays a dialog box to enter deposit information.
     * @param view The deposit button's view.
     */
    //CHECKSTYLE:OFF    suppresses error of NCSS for this method is 79
    public void buttonDeposit(View view)
    //CHECKSTYLE:ON
    {
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        final View dialogLayout = getLayoutInflater().inflate(R.layout.deposit_dialog_layout, null);
        final EditText text = (EditText) dialogLayout.findViewById(R.id.deposit_dialog_text);
        final Spinner menu = (Spinner) dialogLayout.findViewById(R.id.deposit_dialog_menu);     
        final EditText other = (EditText) dialogLayout.findViewById(R.id.deposit_dialog_other);
		
        ArrayList<String> cat = AccountManager.getActiveAccount().getCategoriesDeposit();
        String[] s = new String[cat.size() + 1];
        for (int i = 0; i < cat.size(); i++)
        {
            s[i] = cat.get(i);
        }
        s[cat.size()] = "Other";
        ArrayAdapter<?> a = new ArrayAdapter<Object>(this, R.layout.layout_category_spinner, s);
        menu.setAdapter(a);
		
        final Spinner monthD = (Spinner) dialogLayout.findViewById(R.id.month_spinner_deposit);
        final Spinner dayD = (Spinner) dialogLayout.findViewById(R.id.day_spinner_deposit);
        final Spinner yearD = (Spinner) dialogLayout.findViewById(R.id.year_spinner_deposit);
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
        String[] days = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
        String[] years = {"2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014"};
        ArrayAdapter<?> month = new ArrayAdapter<Object>(this, R.layout.layout_date_spinner, months); 
        ArrayAdapter<?> day = new ArrayAdapter<Object>(this, R.layout.layout_date_spinner, days); 
        ArrayAdapter<?> year = new ArrayAdapter<Object>(this, R.layout.layout_date_spinner, years);
        monthD.setAdapter(month);
        dayD.setAdapter(day);
        yearD.setAdapter(year);

        menu.setOnItemSelectedListener(new OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) 
            {
                if (menu.getSelectedItem().toString().equals("Other") && other.getVisibility() == View.GONE)
                {
                    other.setVisibility(View.VISIBLE);
                    alertDialog.dismiss();
                    alertDialog.setView(dialogLayout);
                    alertDialog.show();
                }
                else if (!menu.getSelectedItem().toString().equals("Other") && other.getVisibility() == View.VISIBLE)
                {
                    other.setVisibility(View.GONE);
                    alertDialog.dismiss();
                    alertDialog.setView(dialogLayout);
                    alertDialog.show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) { }
        });
		
        Button confirm = (Button) dialogLayout.findViewById(R.id.deposit_dialog_button);
        confirm.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                double amount = 0.0;
                if (!text.getText().toString().equals("") && !text.getText().toString().equals("."))
                {
                    amount = Double.parseDouble(text.getText().toString());
                }
        		
                String category = menu.getSelectedItem().toString();
                if (category.equals("Other"))
                {
                    category = other.getText().toString();
                    if (category.equals(""))
                    {
                        Toast.makeText(AccountContainer.this, "Enter a Category", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
        		
                if (amount >= 0.01)
                {
                    Map<String, Integer> monthMap = new HashMap<String, Integer>();
                    String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
                    int i = 0;
                    for (String m:months)
                    {
                        monthMap.put(m, i++);
                    }
                    String m = monthD.getSelectedItem().toString();
                    String d = dayD.getSelectedItem().toString();
                    String y = yearD.getSelectedItem().toString();
                    Time date = new Time();
        		    //timestamp = time.format("%d.%m.%Y %H:%M:%S");
                    date.setToNow();
                    int sec = date.second;
                    int min = date.minute;
                    int hour = date.hour;
                    date.set(sec, min, hour, Integer.parseInt(d), monthMap.get(m), Integer.parseInt(y));
        		    
                    AccountManager.depositWithDate(category, amount, date.format("%d.%m.%Y %H:%M:%S"));
                    Toast.makeText(AccountContainer.this, "Deposit of " + NumberFormat.getCurrencyInstance().format(amount) + " Successful", Toast.LENGTH_LONG).show();
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

        alertDialog.setView(dialogLayout);
        alertDialog.show();
    }
    
    /**
     * Updates the balance textfield with the active account's balance.
     */
    private void updateBalance()
    {
    	TextView text = (TextView) this.findViewById(R.id.account_balance);
    	if (AccountManager.getActiveAccount().getBalance() >= 1000000000f)
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
    
    /**
     * Updates the transaction history textfield with active account's transaction history.
     */
    private void updateTransactionHistory() {
        
        String[][] transactions = DBHandler.getTransactionHistory(AccountManager.getActiveAccount().getName());
        String rows = "";
        if (null != transactions) {
            for (String[] transaction : transactions) {
                rows += "\n\nAccount: " + transaction[0] + ", Amount: " + transaction[1] 
                        + ", Type: " + transaction[2] + ", Category: " + transaction[3]
                        + ", Timestamp: " + transaction[4];
            }
        }
        TextView reportText = (TextView) this.findViewById(R.id.report_text_view);
        reportText.setText(rows);
    }
    
    /**
     * Updates the report textfield with the given text.
     * @param text The text to update the report textfield to display.
     */
    private void updateReportText(String text)
    {
        TextView reportText = (TextView) this.findViewById(R.id.report_text_view);
        reportText.setText(text);
    }
    
    /**
     * Called when the add new account button is pressed.
     * @param view The add new account button's view
     */
    public void addAccountButton(View view)
    {
    	Animation.fade(new Fragments.AccountCreationFragment(), getFragmentManager(), R.id.container_account);
    }
    
    /**
     * Called when the generate report button is clicked, constructs Time objects based on the dates selected in the drop downs.
     * @param view The generate report button's view
     */
    //CHECKSTYLE:OFF    suppresses error of Cyclomatic Complexity
    public void dateButtonClicked(View view)
    //CHECKSTYLE:ON
    {
        View root = (View) view.getParent();
        String dayBegin = ((Spinner) root.findViewById(R.id.date_day_begin)).getSelectedItem().toString();
        String monthBegin = ((Spinner) root.findViewById(R.id.date_month_begin)).getSelectedItem().toString();
        String yearBegin = ((Spinner) root.findViewById(R.id.date_year_begin)).getSelectedItem().toString();
        String dayEnd = ((Spinner) root.findViewById(R.id.date_day_end)).getSelectedItem().toString();
        String monthEnd = ((Spinner) root.findViewById(R.id.date_month_end)).getSelectedItem().toString();
        String yearEnd = ((Spinner) root.findViewById(R.id.date_year_end)).getSelectedItem().toString();
        Map<String, Integer> monthMap = new HashMap<String, Integer>();
        
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
        int i = 0;
        for (String m:months)
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
        for (String m: lm)
        {
            longMonths.add(m);
        }
        if (!longMonths.contains(monthBegin) && db > 30)
        {
            db = 30;
        }
        if (!longMonths.contains(monthEnd) && de > 30)
        {
            de = 30;
        }
        if (monthBegin.equals("Feb") && db > 28)
        {
            db = 28;
        }
        if (monthEnd.equals("Feb") && de > 28)
        {
            de = 28;
        }
        
        Time begin = new Time();
        begin.set(0, 0, 0, db, mb, yb);
        Time end = new Time();
        end.set(59, 59, 23, de, me, ye);
        
        if (begin.after(end))
        {
            Toast.makeText(AccountContainer.this, "Invalid Date", Toast.LENGTH_LONG).show();
            return;
        }    
        
        String report = ((Spinner) root.findViewById(R.id.report_spinner)).getSelectedItem().toString();
        String text = AccountManager.getReport(report, begin, end);
        updateReportText(text);
    }
}
