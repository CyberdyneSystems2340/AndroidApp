package com.cyberdynefinances;

import com.cyberdynefinances.DBReaderContract.DBEntry;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public final class AccountDBHelper extends SQLiteOpenHelper {
	private static final String 
			//SQL Commands for creating the account, user, and transaction tables.
			SQL_CREATE_ACCOUNT_ENTRIES = "CREATE TABLE " + 
			DBEntry.ACCOUNT_TABLE_NAME + " (" +
			DBEntry.ACCOUNT_COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
			DBEntry.ACCOUNT_COLUMN_NAME_BALANCE + " TEXT," +
			DBEntry.ACCOUNT_COLUMN_NAME_INTEREST + " TEXT);",
			
			SQL_CREATE_USER_ENTRIES = "CREATE TABLE " +
			DBEntry.USER_TABLE_NAME + " (" +
			DBEntry.USER_COLUMN_NAME_ID + " TEXT PRIMARY KEY," +
			DBEntry.USER_COLUMN_NAME_PASSWORD + " TEXT," +
			DBEntry.USER_COLUMN_NAME_ACCOUNTS + " TEXT);",
			
			SQL_CREATE_TRANSACTION_ENTRIES = "CREATE TABLE " +
			DBEntry.TRANSACTION_TABLE_NAME + " (" +
			DBEntry.TRANSACTION_COLUMN_NAME_ID + " TEXT," +
			DBEntry.TRANSACTION_COLUMN_NAME_TRANSACTION + " INTEGER," +
			DBEntry.TRANSACTION_COLUMN_NAME_TIMESTAMP + " TEXT);",
			
			//SQL Commands to delete any of the above created tables.
			SQL_DELETE_ACCOUNT_ENTRIES = "DROP TABLE IF EXISTS " + DBEntry.ACCOUNT_TABLE_NAME + ";",
			SQL_DELETE_USER_ENTRIES = "DROP TABLE IF EXISTS " + DBEntry.USER_TABLE_NAME + ";",
			SQL_DELETE_TRANSACTION_ENTRIES = "DROP TABLE IF EXISTS " + DBEntry.TRANSACTION_TABLE_NAME + ";";

	
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "DBReader.db";
	
	
	public AccountDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_USER_ENTRIES + SQL_CREATE_ACCOUNT_ENTRIES + SQL_CREATE_TRANSACTION_ENTRIES);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(SQL_DELETE_USER_ENTRIES + SQL_DELETE_ACCOUNT_ENTRIES + SQL_DELETE_TRANSACTION_ENTRIES);
        onCreate(db);
	}
	
	public static void onOpen() {}
}