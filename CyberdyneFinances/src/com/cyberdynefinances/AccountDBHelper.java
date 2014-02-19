package com.cyberdynefinances;

import com.cyberdynefinances.DBReaderContract.DBEntry;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public final class AccountDBHelper extends SQLiteOpenHelper {
	private static final String TEXT_TYPE = " TEXT", COMMA_SEP = ",",
			SQL_CREATE_ENTRIES = "CREATE TABLE " + DBEntry.TABLE_NAME + " (" +
					DBEntry._ID + " INTEGER PRIMARY KEY," +
					DBEntry.COLUMN_NAME_ID + TEXT_TYPE + COMMA_SEP +
					DBEntry.COLUMN_NAME_BALANCE + TEXT_TYPE + COMMA_SEP +
					DBEntry.COLUMN_NAME_WITHDRAWAL + TEXT_TYPE + COMMA_SEP +
					DBEntry.COLUMN_NAME_DEPOSIT + TEXT_TYPE + COMMA_SEP +
					DBEntry.COLUMN_NAME_TIMESTAMP + TEXT_TYPE + COMMA_SEP,	
			SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBEntry.TABLE_NAME;

	
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "DBReader.db";
	
	
	public AccountDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_ENTRIES);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
	}
	
	public static void onOpen() {}
	
	
	
	public static abstract class MakeEntry implements BaseColumns{
		public static final String TABLE_NAME = "Accounts", COLUMN_NAME_ID = "ID",
				COLUMN_NAME_BALANCE = "Balance", COLUMN_NAME_WITHDRAWAL = "Withdrawal",
				COLUMN_NAME_DEPOSIT = "Deposit", COLUMN_NAME_TIMESTAMP = "Date";
		
		
	}
}