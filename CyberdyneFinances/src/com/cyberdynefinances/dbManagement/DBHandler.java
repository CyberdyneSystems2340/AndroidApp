package com.cyberdynefinances.dbManagement;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.cyberdynefinances.MyApplication;

public class DBHandler {
	AccountDBHelper dbHelper;
	public DBHandler() {
		dbHelper = new AccountDBHelper(MyApplication.getAppContext());
	}

	/**
	 * This method adds a user to the db.
	 * Both a user id and a password MUST be supplied for a new user to be added.
	 * 
	 * @param userID - The new user ID.
	 * @param password - The new users password.
	 * 
	 * @return True if user was added successfully, false if user already exists or an error occurred.
	 */
	public boolean addUser(String userID, String password){
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM " + DBReaderContract.DBEntry.USER_TABLE_NAME, null);
		c.moveToFirst();
		boolean idTaken = false;
		if (null != c || c.getCount() != 0) {
			for(int i = 0; i < c.getCount(); i++){
				if (c.getString(0).equals(userID)) {
					idTaken = true;
				}
				c.moveToNext();
			}
		}
		//Write new row to DB if UserID not already taken.
		if (!idTaken && isValid(userID) && isValid(password)) {
			db = dbHelper.getWritableDatabase();
			
			ContentValues values = new ContentValues();
			values.put(DBReaderContract.DBEntry.USER_COLUMN_NAME_ID, userID);
			values.put(DBReaderContract.DBEntry.USER_COLUMN_NAME_PASSWORD, password);
			db.insert(DBReaderContract.DBEntry.USER_TABLE_NAME,
					  DBReaderContract.DBEntry.USER_COLUMN_NAME_ACCOUNTS,
					  values);
			return true;
		}
		return false;
	}
	
	//TODO:Write method to handle retrieving user info from db.
	public String getUser(String userID){
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM " + DBReaderContract.DBEntry.USER_TABLE_NAME , null);
		String rows ="";
		if (null != c && 0 != c.getCount()) {
			c.moveToFirst();
			do{
				rows += "\nUserID: " + c.getString(0) + ", Pass: " + c.getString(1) + ", Accounts: " + c.getString(2); 
			} while(c.moveToNext());
		}
		return rows;
	}
	
	//TODO:Write method to add account for user.
	
	//TODO:Write method to read accounts owned by user.
	
	//TODO:Write method to update account for user.
	
	//TODO:Write method to remove account from user.
	
	//TODO:Write a method to make a transaction for the user.
	
	private boolean isValid(String str){
		return (null != str && !str.isEmpty());
	}
}