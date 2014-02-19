package com.cyberdynefinances;

import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;

public class Tester {
	
	public static void main(String[] args){
		AccountDBHelper dbHelper = new AccountDBHelper(MyApplication.getAppContext());
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(DBReaderContract.DBEntry.USER_COLUMN_NAME_ID, "TestID");
		
		long newRowId;
		newRowId = db.insert(
				DBReaderContract.DBEntry.USER_TABLE_NAME,
				DBReaderContract.DBEntry.USER_COLUMN_NAME_ID,
				values);
		
		
	}
}