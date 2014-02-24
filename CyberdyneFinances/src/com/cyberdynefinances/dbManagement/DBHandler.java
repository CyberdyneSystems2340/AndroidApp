package com.cyberdynefinances.dbManagement;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.Time;

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
    public boolean containsUser(String userID) {
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
	public boolean addUser(String userID, String password) {
		if (!containsUser(userID) && isValid(userID) && isValid(password)) {
		    SQLiteDatabase db = dbHelper.getWritableDatabase();			
			ContentValues values = new ContentValues();
			values.put(DBEntry.USER_COLUMN_NAME_ID, userID);
			values.put(DBEntry.USER_COLUMN_NAME_PASSWORD, password);
			db.insert(DBEntry.USER_TABLE_NAME, null, values);
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
	 * Array order is such: [0] = userID, [1] = password, [2] = accounts.
	 * */
	public String[] getUserInfo(String userID) {
		String[] info = new String[3];
		try {
			SQLiteDatabase db = dbHelper.getReadableDatabase();
			Cursor c = db.rawQuery("SELECT * FROM " + DBEntry.USER_TABLE_NAME +
					" WHERE " + DBEntry.USER_COLUMN_NAME_ID + " = '" + userID + "'", null);		
			if (null != c && 0 != c.getCount()) {
				c.moveToFirst();
				info[0] = c.getString(0);
				info[1] = c.getString(1); 
			}
			String[] accounts = getAccountsForUser(userID);
			info[2] = accounts[0];
			for(int i = 1; 1 < accounts.length; i++){
			    info[2] += "_" + accounts[i];
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

	/**
	 * This method creates and associates a new account with a current user.
	 * userID provided MUST be valid, otherwise the account will not be created.
	 * 
	 * @param userID - The user to whom this account will belong.
	 * @param newAccount - The new account to add to the user.
	 * @return True if the account was added, false if an error occurred, the account was already taken, or the userID was invalid.
	 * */
	public boolean addAccount(String userID, String newAccount, double balance, double interest) {
		if (!containsAccount(newAccount)) {
			try {
				SQLiteDatabase db = dbHelper.getWritableDatabase();
				ContentValues val = new ContentValues();
				val.put(DBEntry.ACCOUNT_COLUMN_NAME_ID, newAccount);
				val.put(DBEntry.USER_COLUMN_NAME_ID, userID);
				val.put(DBEntry.ACCOUNT_COLUMN_NAME_BALANCE, balance);
				val.put(DBEntry.ACCOUNT_COLUMN_NAME_INTEREST, interest);
				db.insert(DBEntry.ACCOUNT_TABLE_NAME, null, val);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * This method returns all the accounts associated with a supplied user id.
	 * 
	 * @param userID The user id to check for accounts.
	 * @return An array that holds the accounts for a specified user.
	 * 
	 */
	public String[] getAccountsForUser(String userID) {
		String[] accounts = new String[]{"None"};
		try {
			SQLiteDatabase db = dbHelper.getReadableDatabase();
			Cursor c = db.rawQuery("SELECT "+DBEntry.ACCOUNT_COLUMN_NAME_ID+" FROM " + DBEntry.ACCOUNT_TABLE_NAME +
					" WHERE " + DBEntry.USER_COLUMN_NAME_ID + " = '" + userID + "'", null);
			
			if (null != c && 0 != c.getCount()) {
				c.moveToFirst();
				accounts = new String[c.getCount()];
				for (int i = 0; i < c.getCount(); i++) {
				    accounts[i] = c.getString(0);
				    c.moveToNext();
				}
			}
		} catch (Exception e) {e.printStackTrace();}
		return accounts;
	}
	
	/**
	 * This method returns all the basic info for the specified account.
	 * 
	 * @param account - The account to get the information for.
	 * @return String array as such: [0] - account, [1] - owner, [2] - balance, [3] - interest.
	 *         Null if no account exists.
	 * */
	public String[] getAccountInfo(String account){
	    String[] info = null;
	    try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor c = db.rawQuery("SELECT * FROM " + DBEntry.ACCOUNT_TABLE_NAME +
                    " WHERE " + DBEntry.ACCOUNT_COLUMN_NAME_ID + " = '" + account + "'", null);
            
            if (null != c) {
                c.moveToFirst();
                info = new String[4];
                info[0] = c.getString(0);
                info[1] = c.getString(1);
                info[2] = c.getString(2);
                info[3] = c.getString(3);
             }
        } catch (Exception e) {e.printStackTrace();}
	    return info;
	}

	
	/**
	 * Deletes a specified account from the  database.
	 * Also, deletes all transaction history for that account.
	 * 
	 * @param account - The account to delete.
	 * @return True if account deleted successfully, false if an error occurred.
	 */
	public boolean deleteAccount(String account) {
    	try {
    	    SQLiteDatabase db = dbHelper.getWritableDatabase();
    	    db.delete(DBEntry.TRANSACTION_TABLE_NAME, DBEntry.ACCOUNT_COLUMN_NAME_ID + " = '" + account + "'", null);
    	    db.delete(DBEntry.ACCOUNT_TABLE_NAME, DBEntry.ACCOUNT_COLUMN_NAME_ID + " = '" + account +"'", null);
    	    return true;
	    } catch(Exception e) { e.printStackTrace();}
	    return false;
	}

	/**
	 * This method deletes a specified user from the database.
	 * It also removes all the accounts associated to that user.
	 * 
	 * @param userID - The user to delete.
	 * @return True if user was deleted, or was never present. False if an error occurred.
	 */
    public boolean deleteUser(String userID) {
        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String[] accounts = getAccountsForUser(userID);
            for (String account : accounts) {
                deleteAccount(account);
            }
            db.delete(DBEntry.USER_TABLE_NAME,
                    DBEntry.USER_COLUMN_NAME_ID + " = '" + userID + "'", null);
            return true;
        } catch (Exception e) { e.printStackTrace();}
        return false;
    }

    /**
     * This method makes a transaction with a specified account.
     * The amount will be either deducted or deposited to the account.
     * The transactionType will dictate whether the money should be deducted or deposited.
     * The category will show what category this transaction fits under.
     * 
     * @param account - The account to make a transaction with.
     * @param amount - The amount of money to deposit or withdraw.
     * @param transactionType - The amount type, 'Deposit' or 'Withdraw', case doesn't matter.
     * @param category - The category this transaction is associated with.
     * @return True, if transaction was made, false if invalid transaction or an error occurred.
     */
    public boolean makeTransaction(String account, double amount, String transactionType, String category) {
        double balance = Double.parseDouble(getAccountInfo(account)[2]);
        if (transactionType.equalsIgnoreCase("DEPOSIT")) {
            return updateBalance(dbHelper.getWritableDatabase(),new ContentValues(),account,balance,amount,"DEPOSIT",category);
        } 
        if (transactionType.equalsIgnoreCase("WITHDRAW")) {    
            return updateBalance(dbHelper.getWritableDatabase(),new ContentValues(),account,balance,-amount,"WITHDRAW",category);
        }       
        return false;
    }
    
	//This method checks to see if a string is valid for being added to the db.
	private boolean isValid(String str) { return (null != str && !str.isEmpty());}
	
	//This method checks to see if this account has already been 
	private boolean containsAccount(String str) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM " + DBEntry.ACCOUNT_TABLE_NAME + " WHERE " +
                DBEntry.ACCOUNT_COLUMN_NAME_ID + " = '" + str + "'", null);
		return 0 < c.getCount();
	}
	
	//This method adds an amount to an account in the db.
	private boolean updateBalance(SQLiteDatabase db, ContentValues cv,
	        String account, double balance, double amount, String type, String category) {
	    try {
    	    cv.put(DBEntry.ACCOUNT_COLUMN_NAME_BALANCE, balance + amount);
            db.update(DBEntry.ACCOUNT_TABLE_NAME, cv,
                    DBEntry.ACCOUNT_COLUMN_NAME_ID + " = '" + account + "'", null);
            cv = new ContentValues();
            cv.put(DBEntry.ACCOUNT_COLUMN_NAME_ID, account);
            cv.put(DBEntry.TRANSACTION_COLUMN_NAME_AMOUNT, amount);
            cv.put(DBEntry.TRANSACTION_COLUMN_NAME_TYPE, type);
            cv.put(DBEntry.TRANSACTION_COLUMN_NAME_CATEGORY, category);
            Time time = new Time();
            time.setToNow();            
            cv.put(DBEntry.TRANSACTION_COLUMN_NAME_TIMESTAMP, time.toString());
            db.insert(DBEntry.TRANSACTION_TABLE_NAME, DBEntry.TRANSACTION_COLUMN_NAME_CATEGORY, cv);
            return true;
	    } catch(Exception e) { e.printStackTrace();}	    
	    return false;
	}
}