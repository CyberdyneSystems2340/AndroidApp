/**
 * This class is what we use to interact with the database that stores all
 * user information and account histories.
 * ONLY USE THIS CLASS TO ACCESS DB!!!
 * 
 * @author Robert
 * @version 3.14 
 */

package com.cyberdynefinances.dbManagement;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.Time;
import com.cyberdynefinances.MyApplication;
import com.cyberdynefinances.dbManagement.DBReaderContract.DBFactory;

public class DBHandler {
    private static DBHelper dbHelper;
    private static String withdrawText = "WITHDRAW";
    private static String depositText = "DEPOSIT";
    private static String equal = " = '";
    private static String where = " WHERE ";
    private static String selectAll = "SELECT * FROM ";
    private static String apastrophy = "'";

    public DBHandler() {
        dbHelper = new DBHelper(MyApplication.getAppContext());
    }
    /**
     * This method checks to see if the user table contains the specified user.
     *
     * @param userID - The id to check if present.
     * @return True if the user exists, false if not.
     */
    public static boolean containsUser(String userID) {
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor c = db.rawQuery(selectAll + DBFactory.USER_TABLE_NAME
                    + where + DBFactory.USER_COLUMN_NAME_ID + equal + userID
                    + apastrophy, null);
            int rows = c.getCount();
            if (null != c) {
                c.close();
            }
            return 0 < rows;
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * This method adds a user to the db. Both a userid and a password MUST be
     * supplied for a new user to be added.
     *
     * @param userID
     *            - The new user ID.
     * @param password
     *            - The new users password.
     *
     * @return True if user was added successfully, false if user already exists
     *         or an error occurred.
     */
    public static boolean addUser(String userID, String password) {
        try {
            if (isValid(userID) && isValid(password)) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(DBFactory.USER_COLUMN_NAME_ID, userID);
                values.put(DBFactory.USER_COLUMN_NAME_PASSWORD, password);
                db.insert(DBFactory.USER_TABLE_NAME, null, values);
                return true;
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * This method allows the password of a specified user to be changed.
     *
     * @param userID
     *            The user id of the user to change the password.
     * @param password
     *            The new password to set for the user.
     * @return True if password change successful, false if not.
     */
    public static boolean changePassword(String userID, String password) {
        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(DBFactory.USER_COLUMN_NAME_PASSWORD, password);
            db.update(DBFactory.USER_TABLE_NAME, cv, DBFactory.USER_COLUMN_NAME_ID
                    + equal + userID + apastrophy, null);
            return true;
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * This method retrieves a specified user's data from the users table. It
     * returns the userID, Password, and the Accounts for with this user.
     *
     * @param userID
     *            - The id of the user that you wish to get info about.
     * @return An array containing the data about the user from the database,
     *         returns null if user does not exist. Array order is such: [0] =
     *         userID, [1] = password, [2] = accounts.
     * */
    public static String[] getUserInfo(String userID) {
        String[] info = new String[3];
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor c = db.rawQuery(selectAll + DBFactory.USER_TABLE_NAME
                    + where + DBFactory.USER_COLUMN_NAME_ID + equal + userID
                    + apastrophy, null);
            if (null != c && 0 != c.getCount()) {
                c.moveToFirst();
                info[0] = c.getString(0);
                info[1] = c.getString(1);
            }
            if (null != c) {
                c.close();
            }
            String[] accounts = getAccountsForUser(userID);
            info[2] = "None";
            if (null != accounts && 0 < accounts.length) {
                info[2] = accounts[0];
                for (int i = 1; i < accounts.length; i++) {
                    info[2] += "_" + accounts[i];
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return info;
    }

    /**
     * This method returns all the users currently stored in the database.
     *
     * @return An array filled with each user's info in the Users table. If not
     *         users are present, returns null. The array is a two dimensional
     *         array. The user information in each row of the array, is an
     *         array. Array is stored as such:[0] = userID, [1] = password, [3]
     *         = accounts.
     */
    public static String[][] getAllUsersInfo() {
        String[][] users = null;
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor c = db.rawQuery(selectAll + DBFactory.USER_TABLE_NAME,
                    null);
            if (null != c && 0 != c.getCount()) {
                c.moveToFirst();
                int i = 0;
                users = new String[c.getCount()][];
                do {
                    users[i++] = getUserInfo(c.getString(0));
                } while (c.moveToNext());
            }
            if (null != c) {
                c.close();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * This method creates and associates a new account with a current user.
     * userID provided MUST be valid, otherwise the account will not be created.
     *
     * @param userID - The user to whom this account will belong.
     * @param newAccount - The new account to add to the user.
     * @param balance - The balance for the account.
     * @param interest - The interest for the account.
     * @return True if the account was added, false if an error occurred, the
     *         account was already taken, or the userID was invalid.
     * */
    public static boolean addAccount(String userID, String newAccount, double balance,
            double interest) {
        if (!containsAccount(newAccount)) {
            try {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues val = new ContentValues();
                val.put(DBFactory.ACCOUNT_COLUMN_NAME_ID, newAccount);
                val.put(DBFactory.USER_COLUMN_NAME_ID, userID);
                val.put(DBFactory.ACCOUNT_COLUMN_NAME_BALANCE, balance);
                val.put(DBFactory.ACCOUNT_COLUMN_NAME_INTEREST, interest);
                db.insert(DBFactory.ACCOUNT_TABLE_NAME, null, val);
                return true;
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * This method returns all the accounts associated with a supplied user id.
     *
     * @param userID - The user id to check for accounts.
     * @return An array that holds the accounts for a specified user.
     */
    public static String[] getAccountsForUser(String userID) {
        String[] accounts = null;
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor c = db
                    .rawQuery("SELECT " + DBFactory.ACCOUNT_COLUMN_NAME_ID
                            + " FROM " + DBFactory.ACCOUNT_TABLE_NAME + where
                            + DBFactory.USER_COLUMN_NAME_ID + equal + userID
                            + apastrophy, null);
            if (null != c && 0 != c.getCount()) {
                c.moveToFirst();
                accounts = new String[c.getCount()];
                for (int i = 0; i < c.getCount(); i++) {
                    accounts[i] = c.getString(0);
                    c.moveToNext();
                }
            }
            if (null != c) {
                c.close();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    /**
     * This method returns all the basic info for the specified account.
     *
     * @param account - The account to get the information for.
     * @return String array as such: [0] - account, [1] - owner, [2] - balance,
     *         [3] - interest. Null if account name invalid.
     * */
    public static String[] getAccountInfo(String account) {
        String[] info = null;
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor c = db.rawQuery(selectAll
                    + DBFactory.ACCOUNT_TABLE_NAME + where
                    + DBFactory.ACCOUNT_COLUMN_NAME_ID + equal + account + apastrophy,
                    null);

            if (null != c && c.getCount() != 0) {
                c.moveToFirst();
                info = new String[4];
                info[0] = c.getString(0);
                info[1] = c.getString(1);
                info[2] = c.getString(2);
                info[3] = c.getString(3);
            }
            if (null != c) {
                c.close();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return info;
    }

    /**
     * Deletes a specified account from the database. Also, deletes all
     * transaction history for that account.
     *
     * @param account - The account to delete.
     * @return True if account deleted successfully, false if an error occurred.
     */
    public static boolean deleteAccount(String account) {
        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(DBFactory.TRANSACTION_TABLE_NAME,
                    DBFactory.ACCOUNT_COLUMN_NAME_ID + equal + account + apastrophy,
                    null);
            db.delete(DBFactory.ACCOUNT_TABLE_NAME,
                    DBFactory.ACCOUNT_COLUMN_NAME_ID + equal + account + apastrophy,
                    null);
            return true;
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * This method deletes a specified user from the database. It also removes
     * all the accounts associated to that user.
     * 
     * @param userID
     *            - The user to delete.
     * @return True if user was deleted, or was never present. False if an error
     *         occurred.
     */
    public static boolean deleteUser(String userID) {
        try {

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String[] accounts = getAccountsForUser(userID);
            for (String account : accounts) {
                deleteAccount(account);
            }
            db.delete(DBFactory.USER_TABLE_NAME, DBFactory.USER_COLUMN_NAME_ID
                    + equal + userID + apastrophy, null);
            return true;
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * This method makes a transaction with a specified account. The amount will
     * be either deducted or deposited to the account. The transactionType
     * dictates if the money will be withdrawn or deposited. The category will
     * show what category this transaction fits under.
     * 
     * @param account
     *            - The account to make a transaction with.
     * @param amount
     *            - The amount of money to deposit or withdraw.
     * @param transactionType
     *            - The amount type, 'Deposit' or 'Withdraw', case doesn't
     *            matter.
     * @param category
     *            - The category this transaction is associated with.
     * @return True, if transaction was made, false if invalid transaction or an
     *         error occurred.
     */
    public static boolean makeTransaction(String account, double amount,
            String transactionType, String category) {
        double balance = Double.parseDouble(getAccountInfo(account)[2]);
        if (transactionType.equalsIgnoreCase(depositText)) {
            return updateBalanceAndTransactionHistory(
                    dbHelper.getWritableDatabase(), new ContentValues(),
                    account, balance, amount, depositText, category);
        }
        if (transactionType.equalsIgnoreCase(withdrawText)) {
            return updateBalanceAndTransactionHistory(
                    dbHelper.getWritableDatabase(), new ContentValues(),
                    account, balance, -amount, withdrawText, category);
        }
        return false;
    }

    /**
     * This method returns the transaction history of a specified account.
     * 
     * @param account
     *            The account of which to grab the transaction history.
     * @return A two-dimensional array where each row holds a transaction array.
     *         The array is as follows: [0] - account, [1] - amount, [2] - type,
     *         [3] - category, [4] - time stamp
     */
    public static String[][] getTransactionHistory(String account) {
        String[][] history = null;
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor c = db.rawQuery(selectAll
                    + DBFactory.TRANSACTION_TABLE_NAME + where
                    + DBFactory.ACCOUNT_COLUMN_NAME_ID + equal + account + apastrophy,
                    null);
            if (null != c && 0 != c.getCount()) {
                c.moveToFirst();
                int i = 0;
                history = new String[c.getCount()][];
                do {
                    history[i++] = getTransactionInfo(c.getString(c
                            .getColumnIndex("Timestamp")));
                } while (c.moveToNext());
            }
            if (null != c) {
                c.close();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return history;
    }

    /**
     * This method returns the transaction history of a specified time stamp.
     * 
     * @param timeOfTransaction
     *            - The time of the transaction to get.
     * @return An array of this transaction, the array is as follows: [0] -
     *         Account, [1] - Amount, [2] - Type, [3] - Category, [4] - Time
     *         stamp. Null if invalid time stamp.
     */
    public static String[] getTransactionInfo(String timeOfTransaction) {
        String[] transaction = null;
        try {

            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor c = db.rawQuery(selectAll
                    + DBFactory.TRANSACTION_TABLE_NAME + where
                    + DBFactory.TRANSACTION_COLUMN_NAME_TIMESTAMP + equal
                    + timeOfTransaction + apastrophy, null);
            if (null != c && 0 != c.getCount()) {
                c.moveToFirst();
                transaction = new String[c.getColumnCount()];
                transaction[0] = c.getString(0);
                transaction[1] = c.getString(1);
                transaction[2] = c.getString(2);
                transaction[3] = c.getString(3);
                transaction[4] = c.getString(4);
            }
            if (null != c) {
                c.close();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return transaction;
    }
    // This method checks to see if a string is valid for being added to the db.
    private static boolean isValid(String str) {
        return (null != str && !str.isEmpty());
    }

    // This method checks to see if this account has already been
    private static boolean containsAccount(String str) {
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor c = db
                    .rawQuery(selectAll + DBFactory.ACCOUNT_TABLE_NAME
                            + where + DBFactory.ACCOUNT_COLUMN_NAME_ID
                            + equal + str + apastrophy, null);
            int rows = c.getCount();
            if (null != c) {
                c.close();
            }
            return 0 < rows;
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return false;
    }

    // This method adds an amount to an account in the db.
    private static boolean updateBalanceAndTransactionHistory(SQLiteDatabase db,
            ContentValues cv, String account, double balance, double amount,
            String type, String category) {
        try {
            cv.put(DBFactory.ACCOUNT_COLUMN_NAME_BALANCE, balance + amount);
            db.update(DBFactory.ACCOUNT_TABLE_NAME, cv,
                    DBFactory.ACCOUNT_COLUMN_NAME_ID + equal + account + apastrophy,
                    null);
            ContentValues cv2 = new ContentValues();
            cv2.put(DBFactory.ACCOUNT_COLUMN_NAME_ID, account);
            cv2.put(DBFactory.TRANSACTION_COLUMN_NAME_AMOUNT, amount);
            cv2.put(DBFactory.TRANSACTION_COLUMN_NAME_TYPE, type);
            cv2.put(DBFactory.TRANSACTION_COLUMN_NAME_CATEGORY, category);
            Time time = new Time();
            time.setToNow();
            cv2.put(DBFactory.TRANSACTION_COLUMN_NAME_TIMESTAMP,
                    time.format("%d.%m.%Y %H:%M:%S"));
            db.insert(DBFactory.TRANSACTION_TABLE_NAME,
                    DBFactory.TRANSACTION_COLUMN_NAME_CATEGORY, cv2);
            return true;
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return false;
    }
}