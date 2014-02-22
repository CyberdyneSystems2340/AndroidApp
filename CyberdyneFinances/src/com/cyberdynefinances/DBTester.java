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
	private Button writeButton,readButton,clearButton,addAccountButton;
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
	
	//This method tests writing a new user.
	private void writeNewUserToDB(){
		String testID = ((TextView) findViewById(R.id.dbtest_userid_text)).getText().toString(),
				testPass = ((TextView) findViewById(R.id.dbtest_pass_text)).getText().toString();

		new DBHandler().addUser(testID, testPass);
	}
	
	//This method tests reading all the user accounts stored.
	private void readFromDB() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM " + DBReaderContract.DBEntry.USER_TABLE_NAME, null);
		String dbName = dbHelper.getDatabaseName();
		String tableName = DBReaderContract.DBEntry.USER_TABLE_NAME;

		String[][] users = new DBHandler().getAllUsersInfo();
		String rows = "";
		if (null != users) {
    		for (String[] user : users) {
    		    rows += "\nName: " + user[0] + ", Password: " + user[1] + ", Accounts: " + user[2];
    		}
		}
		tView.setText("DbName: " + dbName + "\n\nTableName: " + tableName +
					  "\n\nColumns: " + c.getColumnName(0) + ", " + c.getColumnName(1) + ", Accounts" +
					  "\n\nRows: " + rows);
	}
	
	private void addAccount() {
		String testID = ((TextView) findViewById(R.id.dbtest_userid_text)).getText().toString(),
				testAccount = ((TextView) findViewById(R.id.dbtest_accounts_text)).getText().toString();
		new DBHandler().addAccount(testID, testAccount);
		//System.out.println("addAccount got run!");
	}
	
	private void addButtonListeners() {
		writeButton = (Button) findViewById(R.id.dbtest_write_button);
		readButton = (Button) findViewById(R.id.dbtest_read_button);
		clearButton = (Button) findViewById(R.id.dbtest_clear_button);
		addAccountButton = (Button) findViewById(R.id.dbtest_addAccount_button);
		
		writeButton.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				writeNewUserToDB();
				
			}
		});
		readButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				readFromDB();
			}
		});
		clearButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dbHelper.clearUserTable(dbHelper.getWritableDatabase());
			}
		});
		addAccountButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				addAccount();
				//System.out.println("I got clicked!");
			}
		});
	}
}
