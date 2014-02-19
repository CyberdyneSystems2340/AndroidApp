package com.cyberdynefinances;

import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;

public class Tester {
	
	public static void main(String[] args){
		AccountDBHelper dbHelper = new AccountDBHelper(MyApplication.getAppContext());
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(DBReaderContract.DBEntry.COLUMN_NAME_ID, "TestID");
		values.put(DBReaderContract.DBEntry.COLUMN_NAME_BALANCE, "$$TestBalance$$");
		values.put(DBReaderContract.DBEntry.COLUMN_NAME_WITHDRAWAL, ":(");
		values.put(DBReaderContract.DBEntry.COLUMN_NAME_DEPOSIT, ":D");
		values.put(DBReaderContract.DBEntry.COLUMN_NAME_TIMESTAMP, "TestTime");
		
		long newRowId;
		newRowId = db.insert(
				DBReaderContract.DBEntry.TABLE_NAME,
				DBReaderContract.DBEntry.COLUMN_NAME_ID,
				values);
		
		
	}
}