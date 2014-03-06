package com.cyberdynefinances;

import com.cyberdynefinances.R;
import com.cyberdynefinances.dbManagement.DBHandler;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DBTester extends Activity {
	private TextView tView,transView;
	private Button readButton, withdrawButton, depositButton,addButton;
//	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dbtester);
		tView = (TextView) findViewById(R.id.dbtest_textview);
		transView = (TextView) findViewById(R.id.dbtest_transaction_textview);
		addButtonListeners();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dbtester_activity, menu);
		return true;
	}

	//This method tests reading all the user accounts stored.
	private void readFromDB() {
		String[][] users = DBHandler.getAllUsersInfo();
		String rows = "", accountStr = "";
		String[] accounts = null, accountInfo = null;
		if (null != users) {
    		for (String[] user : users) {
    		    rows += "\nName: " + user[0] + ", Password: " + user[1] + ", Accounts: " + user[2];
    		    
    		    accounts = DBHandler.getAccountsForUser(user[0]);
    		    if(null != accounts) {
        		    for (String account: accounts) {
        		        
        		        accountInfo = DBHandler.getAccountInfo(account);
        		    
        		        accountStr += "\n Account: " + accountInfo[0] + ", Owner: " + accountInfo[1] +
        		                ", Balance: " + accountInfo[2] + ", Interest: " + accountInfo[3]; 
        		    }
        		    rows += accountStr;
    		    }
    		}
		}
		tView.setText("\nRows: " + rows);
		readTransaction();
	}
	
	private void withdraw() {
	    String account = (String)((TextView) findViewById(R.id.dbtest_account_text)).getText().toString();
        double amount = Double.parseDouble((String)((TextView) findViewById(R.id.dbtest_amount_text)).getText().toString());	    
	    DBHandler.makeTransaction(account, amount, "WITHDRAW", "NONE");
	    readTransaction();
	}
	
	private void deposit() {
        String account = (String)((TextView) findViewById(R.id.dbtest_account_text)).getText().toString();
        double amount = Double.parseDouble((String)((TextView) findViewById(R.id.dbtest_amount_text)).getText().toString());     
        DBHandler.makeTransaction(account, amount, "DEPOSIT", "NONE");
        readTransaction();
	}
	
	private void addAccount(){
	    String userID = ((TextView) findViewById(R.id.dbtester_username_text)).getText().toString();
	    String account = (String)((TextView) findViewById(R.id.dbtest_account_text)).getText().toString();
	    DBHandler.addAccount(userID, account, 101, .03);
	    readFromDB();
	};
	
	private void readTransaction(){
	    String[][] transactions = DBHandler.getTransactionHistory((String)((TextView) findViewById(R.id.dbtest_account_text)).getText().toString());
	    String rows = "";
	    if (null != transactions) {
	        for (String[] transaction : transactions) {
	            rows += "\nAccount: " + transaction[0] + ", Amount: " + transaction[1] +
	                    ", Type: " + transaction[2] + ", Category: " + transaction[3] +
	                    ", Timestamp: " + transaction[4];
	        }
	    }
	    transView.setText("\nTransactions: " + rows);
	}

	private void addButtonListeners()
	{
		readButton = (Button) findViewById(R.id.dbtest_read_button);
		withdrawButton = (Button) findViewById(R.id.dbtest_withdraw_button);
		depositButton = (Button) findViewById(R.id.dbtest_deposit_button);
		addButton = (Button) findViewById(R.id.dbtest_add_button);
		
		readButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				readFromDB();
			}
		});
		withdrawButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                withdraw();                
            }
        });
		depositButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                deposit();
            }
        });
		addButton.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                addAccount();
                
            }
        });
	}
}
