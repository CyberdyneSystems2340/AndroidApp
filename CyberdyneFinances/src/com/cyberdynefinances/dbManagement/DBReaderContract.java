package com.cyberdynefinances.dbManagement;
import android.provider.BaseColumns;
public final class DBReaderContract {	
	public DBReaderContract() {}
	
	public static abstract class DBEntry implements BaseColumns{
		//Table names
		public static final String ACCOUNT_TABLE_NAME = "Accounts",	TRANSACTION_TABLE_NAME = "Transactions", USER_TABLE_NAME = "Users";
		
		//Identification columns for tables.
		public static final String ACCOUNT_COLUMN_NAME_ID = "AccountNumber", USER_COLUMN_NAME_ID = "UserID";
		
		//This is where the non-unique columns for the transaction table are specified.
		public static final String TRANSACTION_COLUMN_NAME_AMOUNT = "Amount",
				TRANSACTION_COLUMN_NAME_TYPE = "Type",
				TRANSACTION_COLUMN_NAME_TIMESTAMP = "Date",
				TRANSACTION_COLUMN_NAME_CATEGORY = "Category";
		
		//This is where the non-unique columns for the account table are specified.
		public static final String ACCOUNT_COLUMN_NAME_INTEREST = "Interest", ACCOUNT_COLUMN_NAME_BALANCE = "Balance";
		
		//This is where the non-unique columns for the user table are specified.
		public static final String USER_COLUMN_NAME_PASSWORD = "Password";
	}
}