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
     * This method checks to see if the user table contains the specified user. 
     * 
     * @return True if the user exists, false if not.
     */
    public boolean userExists(String userID){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + DBEntry.USER_TABLE_NAME + " WHERE " +
                                DBEntry.USER_COLUMN_NAME_ID + " = '" + userID + "'", null);
        return 0 < c.getCount();
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
		if (!userExists(userID) && isValid(userID) && isValid(password)) {
		    SQLiteDatabase db = dbHelper.getWritableDatabase();			
			ContentValues values = new ContentValues();
			values.put(DBEntry.USER_COLUMN_NAME_ID, userID);
			values.put(DBEntry.USER_COLUMN_NAME_PASSWORD, password);
			db.insert(DBEntry.USER_TABLE_NAME, DBEntry.USER_COLUMN_NAME_ACCOUNTS, values);
			return true;
		}
		return false;
	}
	
	/**
	 * This method retrieves a specified user's data from the users table.
	 * It returns the userID, Password, and the Accounts associated with this user.
	 * 
	 * @param userID - The id of the user that you wish to gain information about.
	 * @return An array containing the data about the user from the database, returns null if user does not exist.
	 * Array order is such: [0] = userID, [1] = password, [3] = accounts.
	 * */
	public String[] getUserInfo(String userID){
		String[] info = new String[3];
		try {
			SQLiteDatabase db = dbHelper.getReadableDatabase();
			Cursor c = db.rawQuery("SELECT * FROM " + DBEntry.USER_TABLE_NAME +
					" WHERE " + DBEntry.USER_COLUMN_NAME_ID + " = '" + userID + "'", null);		
			if (null != c && 0 != c.getCount()) {
				c.moveToFirst();
				info[0] = c.getString(0);
				info[1] = c.getString(1);
				info[2] = c.getString(2); 
			} 
		} catch (Exception e) {e.printStackTrace();}
		return info;
	}

	/**
	 * This method returns all the users currently stored in the database. 
	 * 
	 * @return An array filled with each user's info in the Users table.
	 * 		If not users are present, returns null.
	 *     The array is a two dimensional array. The user information in each row of the array, is an array.
	 *     Array order of each row is such:  [0] = userID, [1] = password, [3] = accounts.
	 */
	public String[][] getAllUsersInfo() {
		String[][] users = null;
		try {
			SQLiteDatabase db = dbHelper.getReadableDatabase();
			Cursor c = db.rawQuery("SELECT * FROM " + DBEntry.USER_TABLE_NAME, null);		
			if (null != c && 0 != c.getCount()) {
				c.moveToFirst();
				int i = 0;
                users = new String[c.getCount()][];
				do{
					users[i++]= getUserInfo(c.getString(0)); 
				} while(c.moveToNext());
			} 
		} catch (Exception e) {e.printStackTrace();}
		return users;
	}

	//TODO: Change the way the accounts are stored. DO NOT store the account numbers in the users table.
	//     Only store userID and passwords in the users table, then in the accounts table add a userID column.
	/**
	 * This method creates and associates a new account with a current user.
	 * userID provided MUST be valid, otherwise the account will not be created.
	 * 
	 * @param userID - The user to whom this account will belong.
	 * @param newAccount - The new account to add to the user.
	 * @return True if the account was added, false if an error occurred or the userID was invalid.
	 * */
	public boolean addAccount(String userID, String newAccount) { 
		String currentAccounts = "";
		try {
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			Cursor c = db.rawQuery("SELECT Accounts FROM " + DBEntry.USER_TABLE_NAME +
					" WHERE " + DBEntry.USER_COLUMN_NAME_ID + " = '" + userID + "'", null);		
			if (null != c && 0 != c.getCount()) {
				c.moveToFirst();
				if (isValid(c.getString(0))) {
					currentAccounts = c.getString(0) + "_";
				}
			}
			ContentValues values = new ContentValues();
			values.put(DBEntry.USER_COLUMN_NAME_ACCOUNTS, currentAccounts + newAccount);
			db.update(DBEntry.USER_TABLE_NAME, values, DBEntry.USER_COLUMN_NAME_ID + " = ?", new String[] {userID});
			//Change to this form of storing the accounts later
			/*
			ContentValues val = new ContentValues();
			val.put(DBEntry.ACCOUNT_COLUMN_NAME_ID, newAccount);
			val.put(DBEntry.USER_COLUMN_NAME_ID, userID);
			db.insert(DBEntry.ACCOUNT_TABLE_NAME, null, val);*/
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	//TODO:Write method to read accounts owned by user.
	
	//TODO:Write method to update account for user.
	
	//TODO:Write method to remove account from user.
	
	//TODO:Write a method to make a transaction for the user.
	
	//This method checks to see if a string is valid for being added to the db.
	private boolean isValid(String str){ return (null != str && !str.isEmpty());}
	
	//This method checks to see if this account has already been 
/*	private boolean accountAlreadyExists(String str) {
	    
	}*/
}