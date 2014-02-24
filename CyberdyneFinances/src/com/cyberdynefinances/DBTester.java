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
	private Button readButton, withdrawButton, depositButton;
//	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dbtester);
		dbHelper = new AccountDBHelper(MyApplication.getAppContext());
		tView = (TextView) findViewById(R.id.dbtest_textview);
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
	
	private void withdraw() {
	    DBHandler dbHandler = new DBHandler();
	    String account = (String) ((TextView) findViewById(R.id.dbtest_account_text)).getText();
        double amount = Double.parseDouble((String) ((TextView) findViewById(R.id.dbtest_account_text)).getText());	    
	    dbHandler.makeTransaction(account, amount, "WITHDRAW", "NONE");
	    
	}
	
	private void deposit() {
	    DBHandler dbHandler = new DBHandler();
        String account = (String) ((TextView) findViewById(R.id.dbtest_account_text)).getText();
        double amount = Double.parseDouble((String) ((TextView) findViewById(R.id.dbtest_account_text)).getText());     
        dbHandler.makeTransaction(account, amount, "DEPOSIT", "NONE");
	}

	private void addButtonListeners()
	{
		readButton = (Button) findViewById(R.id.dbtest_read_button);
		withdrawButton = (Button) findViewById(R.id.dbtest_withdraw_button);
		depositButton = (Button) findViewById(R.id.dbtest_deposit_button);
		
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
	}
}
