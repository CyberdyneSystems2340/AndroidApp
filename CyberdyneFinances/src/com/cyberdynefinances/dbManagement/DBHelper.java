package com.cyberdynefinances.dbManagement;
//
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cyberdynefinances.MyApplication;
import com.cyberdynefinances.dbManagement.DBReaderContract.DBEntry;;

public final class DBHelper extends SQLiteOpenHelper {
	//SQL Commands for creating the account, user, and transaction tables.
	private static final String SQL_CREATE_USER_ENTRIES = "CREATE TABLE " +
			DBEntry.USER_TABLE_NAME + " (" +
			DBEntry.USER_COLUMN_NAME_ID + " TEXT PRIMARY KEY," +
			DBEntry.USER_COLUMN_NAME_PASSWORD + " TEXT)";
	
	private static final String SQL_CREATE_ACCOUNT_ENTRIES = "CREATE TABLE " + 
			DBEntry.ACCOUNT_TABLE_NAME + " (" +
			DBEntry.ACCOUNT_COLUMN_NAME_ID + " TEXT PRIMARY KEY," +
			DBEntry.USER_COLUMN_NAME_ID + " TEXT," +
			DBEntry.ACCOUNT_COLUMN_NAME_BALANCE + " TEXT," +
			DBEntry.ACCOUNT_COLUMN_NAME_INTEREST + " TEXT)";
	
	private static final String SQL_CREATE_TRANSACTION_ENTRIES = "CREATE TABLE " +
			DBEntry.TRANSACTION_TABLE_NAME + " (" +
			DBEntry.ACCOUNT_COLUMN_NAME_ID + " TEXT," +
			DBEntry.TRANSACTION_COLUMN_NAME_AMOUNT + " TEXT," +
			DBEntry.TRANSACTION_COLUMN_NAME_TYPE +" TEXT," +
			DBEntry.TRANSACTION_COLUMN_NAME_CATEGORY + " TEXT," +
			DBEntry.TRANSACTION_COLUMN_NAME_TIMESTAMP + " TEXT PRIMARY KEY)";
	
	//SQL Commands to delete any of the above created tables.
	private static final String	
			SQL_DELETE_USER_ENTRIES = "DROP TABLE IF EXISTS " + DBReaderContract.DBEntry.USER_TABLE_NAME,
			SQL_DELETE_ACCOUNT_ENTRIES = "DROP TABLE IF EXISTS " + DBReaderContract.DBEntry.ACCOUNT_TABLE_NAME,
			SQL_DELETE_TRANSACTION_ENTRIES = "DROP TABLE IF EXISTS " + DBReaderContract.DBEntry.TRANSACTION_TABLE_NAME;

	
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "CyberdyneFinancesDB";
	
	
	public DBHelper(Context context) {
		super(MyApplication.getAppContext(), DATABASE_NAME, null, DATABASE_VERSION);
		
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_USER_ENTRIES);
		db.execSQL(SQL_CREATE_ACCOUNT_ENTRIES);
		db.execSQL(SQL_CREATE_TRANSACTION_ENTRIES);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(SQL_DELETE_USER_ENTRIES);
		db.execSQL(SQL_DELETE_ACCOUNT_ENTRIES);
		db.execSQL(SQL_DELETE_TRANSACTION_ENTRIES);
        onCreate(db);
	}
	
	public void clearAllTables(SQLiteDatabase db){
	    onUpgrade(db,0,0);
	} 
	
	public void clearUserTable(SQLiteDatabase db){
		db.execSQL(SQL_DELETE_USER_ENTRIES);
		db.execSQL(SQL_CREATE_USER_ENTRIES);
	}
	
	public void clearAccountTable(SQLiteDatabase db) {
		db.execSQL(SQL_DELETE_ACCOUNT_ENTRIES);
		db.execSQL(SQL_CREATE_ACCOUNT_ENTRIES);
	}

	public void clearTransactionTable(SQLiteDatabase db) {
		db.execSQL(SQL_DELETE_TRANSACTION_ENTRIES);
		db.execSQL(SQL_CREATE_TRANSACTION_ENTRIES);

	}
}