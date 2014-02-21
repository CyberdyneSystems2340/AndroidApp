package com.cyberdynefinances;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.widget.TextView;

public class DBTester2 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dbtester2);
		
		TextView tView = (TextView) findViewById(R.id.dbtester2_textview);
		AccountDBHelper dbHelper = new AccountDBHelper(MyApplication.getAppContext());

		writeToDB(dbHelper);
		tView.setText(readFromDB(dbHelper));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dbtester_activity, menu);
		return true;
	}
	
	private void writeToDB(AccountDBHelper dbHelper){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		String testID = "TestID3";
		values.put(DBReaderContract.DBEntry.USER_COLUMN_NAME_ID, testID);
		values.put(DBReaderContract.DBEntry.USER_COLUMN_NAME_PASSWORD, "123456");
		values.put(DBReaderContract.DBEntry.USER_COLUMN_NAME_ACCOUNTS, "None");
		SQLiteDatabase db2 = dbHelper.getReadableDatabase();
		Cursor c = db2.rawQuery("SELECT * FROM " + DBReaderContract.DBEntry.USER_TABLE_NAME, null);
		boolean userIDTaken = false;/*
		c.moveToFirst();
		if (null != c) {
			for (int i = c.getCount(); i > 0; i--){
				userIDTaken = c.getString(c.getColumnIndex("UserID")) == testID;
				c.moveToNext();
			}
			if (!userIDTaken) {
				db.insert(DBReaderContract.DBEntry.USER_TABLE_NAME,
					  DBReaderContract.DBEntry.USER_COLUMN_NAME_ACCOUNTS,
					  values);
			}
		}*/
//		((TextView) findViewById(R.id.dbtester2_textview)).setText("Success!");
	}
	
	private String readFromDB(AccountDBHelper dbHelper) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM " + DBReaderContract.DBEntry.USER_TABLE_NAME, null);
		String dbName = dbHelper.getDatabaseName();
		String tableName = DBReaderContract.DBEntry.USER_TABLE_NAME;

		int col0 = c.getColumnIndex("UserID");
		int col1 = c.getColumnIndex("Password");
		int col2 = c.getColumnIndex("Accounts");
		String rows = "";
		if (null != c && 0 != c.getCount()) {
			c.moveToNext();
			do{
				rows += "\nUserID: " + c.getString(col0) + ", Pass: " + c.getString(col1) + ", Accounts: " + c.getString(col2); 
			} while(c.moveToNext());
		}

		return "DbName: " + dbName + "\n\nTableName: " + tableName +
					  "\n\nColumns: " + c.getColumnName(col0) + ", " + c.getColumnName(col1) + ", " + c.getColumnName(col2) +
					  "\n\nRows: " + rows;
	}


}
