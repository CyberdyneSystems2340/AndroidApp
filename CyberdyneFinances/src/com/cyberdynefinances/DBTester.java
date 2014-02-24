package com.cyberdynefinances;

import com.cyberdynefinances.R;
import com.cyberdynefinances.dbManagement.AccountDBHelper;
import com.cyberdynefinances.dbManagement.DBHandler;
import com.cyberdynefinances.dbManagement.DBReaderContract;

import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DBTester extends Activity {
	private AccountDBHelper dbHelper;
	private TextView tView;
	private Button readButton, transactionButton;//addAccountButton;
//	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dbtester);
		dbHelper = new AccountDBHelper(MyApplication.getAppContext());
		tView = (TextView) findViewById(R.id.dbtester_textview);
		addButtonListeners();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dbtester_activity, menu);
		return true;
	}
/*	
	//This method tests writing a new user.
	private void writeNewUserToDB(){
		String testID = ((TextView) findViewById(R.id.dbtest_account_text)).getText().toString(),
				testPass = ((TextView) findViewById(R.id.dbtest_pass_text)).getText().toString();

		new DBHandler().addUser(testID, testPass);
	}
*/
	//This method tests reading all the user accounts stored.
	private void readFromDB() {

		DBHandler dbHandler = new DBHandler();
		String[][] users = dbHandler.getAllUsersInfo();
		String rows = "", accountStr = "";
		String[] accounts = null, accountInfo = null;
		if (null != users) {
    		for (String[] user : users) {
    		    rows += "\nName: " + user[0] + ", Password: " + user[1] + ", Accounts: " + user[2];
    		    accounts = dbHandler.getAccountsForUser(user[0]);
    		    for (String account: accounts) {
    		        accountInfo = dbHandler.getAccountInfo(account);
    		        accountStr += "\n Account: " + accountInfo[0] + ", Owner: " + accountInfo[1] +
    		                ", Balance: " + accountInfo[2] + ", Interest: " + accountInfo[3]; 
    		    }
    		    rows += accountStr;
    		}
		}
		tView.setText("\n\nRows: " + rows);
	}
	
	private void transact() {
	    DBHandler dbHandler = new DBHandler();
	    
	    
	}
/*	
	private void addAccount() {
		String testID = ((TextView) findViewById(R.id.dbtest_account_text)).getText().toString(),
				testAccount = ((TextView) findViewById(R.id.dbtest_amount_text)).getText().toString();
		new DBHandler().addAccount(testID, testAccount, 0, 0);
	}
*/
	private void addButtonListeners() {
//		writeButton = (Button) findViewById(R.id.dbtest_write_button);
		readButton = (Button) findViewById(R.id.dbtest_read_button);
		transactionButton = (Button) findViewById(R.id.dbtest_transaction_button);
		
//		clearButton = (Button) findViewById(R.id.dbtest_clear_button);
		
//		addAccountButton = (Button) findViewById(R.id.dbtest_addAccount_button);
/*		
		writeButton.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				writeNewUserToDB();
				
			}
		});*/
		readButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				readFromDB();
			}
		});
		transactionButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                transact();                
            }
        });
		/*
		clearButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dbHelper.clearAllTables(new AccountDBHelper(MyApplication.getAppContext()).getReadableDatabase());
			}
		});
		*/
		/*
		addAccountButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				addAccount();
			}
		});*/
	}
}
