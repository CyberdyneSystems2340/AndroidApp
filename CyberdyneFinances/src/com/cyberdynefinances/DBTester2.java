package com.cyberdynefinances;

import android.os.Bundle;
import android.app.Activity;
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
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM " + DBReaderContract.DBEntry.USER_TABLE_NAME, null);
		String dbName = dbHelper.getDatabaseName();
		String tableName = DBReaderContract.DBEntry.USER_TABLE_NAME;

		int col0 = c.getColumnIndex("UserID");
		int col1 = c.getColumnIndex("Password");
		int col2 = c.getColumnIndex("Accounts");
		c.moveToLast();
		String rows = "";
/*		if (c != null) {
			do {
				rows = "UserID: " + c.getString(col0) + ", Pass: " + c.getString(col1) + ", Accounts: " + c.getString(col2); 
			} while (c.moveToNext());
		}
*/
//		writeToDB(dbHelper);
//		String itemID = readFromDB(dbHelper);		

		tView.setText("DbName: " + dbName + "\n\nTableName: " + tableName +
					  "\n\nColumns: " + c.getColumnName(col0) + ", " + c.getColumnName(col1) + ", " + c.getColumnName(col2) +
					  "\n\nRows: " +rows);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dbtester_activity, menu);
		return true;
	}
/*	
	private void writeToDB(AccountDBHelper dbHelper){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(DBReaderContract.DBEntry.USER_COLUMN_NAME_ID, "TestID");
		
		db.insert(DBReaderContract.DBEntry.USER_TABLE_NAME,
				  DBReaderContract.DBEntry.USER_COLUMN_NAME_ACCOUNTS,
				  values);
	}
	
	private String readFromDB(AccountDBHelper dbHelper) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();

		String[] projection = {DBEntry.USER_COLUMN_NAME_ID};
		
		String sortOrder = DBEntry.USER_COLUMN_NAME_ID + " DESC";
		String selection = "UserID";
		String[] selectionArgs = {"TestID"};
		Cursor c = db.query(DBEntry.USER_TABLE_NAME,
				projection,selection,selectionArgs, null, null, sortOrder);
		c.moveToFirst();
		return c.getString(c.getColumnIndex(DBEntry.USER_COLUMN_NAME_ID));
	}
*/

}
