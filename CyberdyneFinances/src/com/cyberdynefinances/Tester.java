package com.cyberdynefinances;

import com.cyberdynefinances.DBReaderContract.DBEntry;

import android.database.Cursor;
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
				DBReaderContract.DBEntry.USER_COLUMN_NAME_ACCOUNTS,
				values);
		
		db = dbHelper.getReadableDatabase();
		
		String[] projection = {
				DBEntry.USER_COLUMN_NAME_ID
		};
		
		String sortOrder = DBEntry.USER_COLUMN_NAME_ID + " DESC";
		String selection = "UserID";
		String[] selectionArgs = {"TestID"};
		Cursor c = db.query(DBEntry.USER_TABLE_NAME,
				projection,selection,selectionArgs, null, null, sortOrder);
		c.moveToFirst();
		String itemID = c.getString(c.getColumnIndex(DBEntry.USER_COLUMN_NAME_ID));
		System.out.println(itemID);
	}
}