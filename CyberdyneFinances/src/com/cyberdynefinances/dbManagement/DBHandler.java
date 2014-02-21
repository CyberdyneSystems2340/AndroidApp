package com.cyberdynefinances.dbManagement;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.cyberdynefinances.MyApplication;
import com.cyberdynefinances.dbManagement.DBReaderContract.DBEntry;

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
			values.put(DBEntry.USER_COLUMN_NAME_ID, userID);
			values.put(DBEntry.USER_COLUMN_NAME_PASSWORD, password);
			db.insert(DBEntry.USER_TABLE_NAME,
					  DBEntry.USER_COLUMN_NAME_ACCOUNTS,
					  values);
			return true;
		}
		return false;
	}
	
	/**
	 * This method retrieves a specified user's data from the users table.
	 * It returns the userID, Password, and the Accounts associated with this user.
	 * 
	 * @param userID - The id of the user that you wish to gain information about.
	 * @return The data about the user, returns an empty string if userID provided is invalid.
	 * */
	public String getUser(String userID){
		String rows ="";
		try {
			SQLiteDatabase db = dbHelper.getReadableDatabase();
			Cursor c = db.rawQuery("SELECT * FROM " + DBEntry.USER_TABLE_NAME +
					" WHERE " + DBEntry.USER_COLUMN_NAME_ID + " = '" + userID + "'", null);		
			if (null != c && 0 != c.getCount()) {
				c.moveToFirst();
				do{
					rows += "\nUserID: " + c.getString(0) + ", Pass: " +
							c.getString(1) + ", Accounts: " + c.getString(2); 
				} while(c.moveToNext());
			} 
		} catch (Exception e) {e.printStackTrace();}
		return rows;
	}
	
	/**
	 * This method creates and associates a new account with a current user.
	 * userID provided MUST be valid, otherwise the account will not be created.
	 * 
	 * @param userID - The user to whom this account will belong.
	 * @param newAccount - The new account to add to the user.
	 * @return True if the account was added, false if an error occurred or the userID was invalid.
	 * */
	public boolean addAccount(String userID, String newAccount) { 
		//String currentAccounts = "";
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(DBEntry.USER_COLUMN_NAME_ACCOUNTS, newAccount);
		db.update(DBEntry.USER_TABLE_NAME, values, DBEntry.USER_COLUMN_NAME_ID + " = ?", new String[] {userID});
		//System.out.println("Updated Successfully!");
		/*
		try {
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			db.rawQuery("INSERT INTO " + DBEntry.USER_TABLE_NAME + " SET " +
					DBEntry.USER_COLUMN_NAME_ACCOUNTS +
					" = " + newAccount + " WHERE "+DBEntry.USER_COLUMN_NAME_ID+" = '"+userID+"';", null);
			System.out.println("Updated Successfully!");
			return true;
		} catch(Exception e) { e.printStackTrace();}*/
		return false;
	}
	
	//TODO:Write method to read accounts owned by user.
	
	//TODO:Write method to update account for user.
	
	//TODO:Write method to remove account from user.
	
	//TODO:Write a method to make a transaction for the user.
	
	//This method checks to see if a string is valid for being added to the db.
	private boolean isValid(String str){ return (null != str && !str.isEmpty());}
}