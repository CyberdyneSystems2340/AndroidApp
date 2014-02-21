package com.cyberdynefinances.dbmanagement;
import android.provider.BaseColumns;
public final class DBReaderContract {	
	public DBReaderContract() {}
	
	public static abstract class DBEntry implements BaseColumns{
		//This is where we decide what the tables should be called and what will be unique to each row in a table.
		public static final String 
				ACCOUNT_TABLE_NAME = "Accounts",	ACCOUNT_COLUMN_NAME_ID = "AccountNumber",
				TRANSACTION_TABLE_NAME = "Transactions",	TRANSACTION_COLUMN_NAME_ID = "AccountNumber",
				USER_TABLE_NAME = "Users", USER_COLUMN_NAME_ID = "UserID", USER_COLUMN_NAME_ACCOUNTS = "Accounts";
		
		//This is where the non-unique columns for the transaction table are specified.
		public static final String TRANSACTION_COLUMN_NAME_TRANSACTION = "Transaction", TRANSACTION_COLUMN_NAME_TIMESTAMP = "Date";
		
		//This is where the non-unique columns for the account table are specified.
		public static final String ACCOUNT_COLUMN_NAME_INTEREST = "Interest", ACCOUNT_COLUMN_NAME_BALANCE = "Balance";
		
		//This is where the non-unique columns for the user table are specified.
		public static final String USER_COLUMN_NAME_PASSWORD = "Password";
		
		
		
		
	}
}