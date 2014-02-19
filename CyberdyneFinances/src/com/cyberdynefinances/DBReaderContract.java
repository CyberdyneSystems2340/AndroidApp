package com.cyberdynefinances;
import android.provider.BaseColumns;
public final class DBReaderContract {	
	public DBReaderContract() {}
	
	public static abstract class DBEntry implements BaseColumns{

		public static final String TABLE_NAME = "Accounts", COLUMN_NAME_ID = "ID",
				COLUMN_NAME_BALANCE = "Balance", COLUMN_NAME_WITHDRAWAL = "Withdrawal",
				COLUMN_NAME_DEPOSIT = "Deposit", COLUMN_NAME_TIMESTAMP = "Date";
		
		
	}
}