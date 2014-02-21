package com.cyberdynefinances.dbmanagement;

import com.cyberdynefinances.MyApplication;
import com.cyberdynefinances.R;
import com.cyberdynefinances.R.id;
import com.cyberdynefinances.R.layout;
import com.cyberdynefinances.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DBTester2 extends Activity {
	private AccountDBHelper dbHelper;
	private TextView tView;
	private Button writeButton,readButton,clearButton;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dbtester2);
		dbHelper = new AccountDBHelper(MyApplication.getAppContext());
		tView = (TextView) findViewById(R.id.dbtester2_textview);
		addButtonListeners();
		//writeToDB();
		//tView.setText(readFromDB());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dbtester_activity, menu);
		return true;
	}
	
	private void writeToDB(){
		String testID = ((TextView) findViewById(R.id.dbtest_userid_text)).getText().toString(),
				testPass = ((TextView) findViewById(R.id.dbtest_pass_text)).getText().toString(),
				testAccounts = ((TextView) findViewById(R.id.dbtest_accounts_text)).getText().toString();
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM " + DBReaderContract.DBEntry.USER_TABLE_NAME, null);
		c.moveToFirst();
		boolean idTaken = false;
		if (null != c || c.getCount() != 0) {
			for(int i = 0; i < c.getCount(); i++){
				if (c.getString(0).equals(testID)) {
					idTaken = true;
				}
				c.moveToNext();
			}
		}
		
		//Write new row to DB if UserID not allready taken.
		TextView textView = (TextView) findViewById(R.id.dbtest_userid_text);
		String str = textView.getText().toString();
		if (!idTaken && null != str && !str.isEmpty()) {
			db = dbHelper.getWritableDatabase();
			
			ContentValues values = new ContentValues();
			values.put(DBReaderContract.DBEntry.USER_COLUMN_NAME_ID, testID);
			values.put(DBReaderContract.DBEntry.USER_COLUMN_NAME_PASSWORD, testPass);
			values.put(DBReaderContract.DBEntry.USER_COLUMN_NAME_ACCOUNTS, testAccounts);
			db.insert(DBReaderContract.DBEntry.USER_TABLE_NAME,
					  DBReaderContract.DBEntry.USER_COLUMN_NAME_ACCOUNTS,
					  values);
		}
//		((TextView) findViewById(R.id.dbtester2_textview)).setText("Success!");
	}
	
	private void readFromDB() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM " + DBReaderContract.DBEntry.USER_TABLE_NAME, null);
		String dbName = dbHelper.getDatabaseName();
		String tableName = DBReaderContract.DBEntry.USER_TABLE_NAME;

		int col0 = c.getColumnIndex("UserID");
		int col1 = c.getColumnIndex("Password");
		int col2 = c.getColumnIndex("Accounts");
		String rows = "";
		if (null != c && 0 != c.getCount()) {
			c.moveToFirst();
			do{
				rows += "\nUserID: " + c.getString(col0) + ", Pass: " + c.getString(col1) + ", Accounts: " + c.getString(col2); 
			} while(c.moveToNext());
		}

		tView.setText("DbName: " + dbName + "\n\nTableName: " + tableName +
					  "\n\nColumns: " + c.getColumnName(col0) + ", " + c.getColumnName(col1) + ", " + c.getColumnName(col2) +
					  "\n\nRows: " + rows);
	}
	
	private void addButtonListeners() {
		writeButton = (Button) findViewById(R.id.dbtest_write_button);
		readButton = (Button) findViewById(R.id.dbtest_read_button);
		clearButton = (Button) findViewById(R.id.dbtest_clear_button);
		
		writeButton.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				writeToDB();
				
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
	}
}
