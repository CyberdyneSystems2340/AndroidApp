/**
 * This method merely holds the table and column names for the database.
 * 
 * @author Robert
 * @version 3.14
 */

package com.cyberdynefinances.dbManagement;

import android.provider.BaseColumns;

public final class DBReaderContract {

    public abstract static class DBFactory implements BaseColumns {
        // Table names
        public static final String ACCOUNT_TABLE_NAME = "Accounts";
        public static final String TRANSACTION_TABLE_NAME = "Transactions";
        public static final String USER_TABLE_NAME = "Users";

        // Identification columns for tables.
        public static final String ACCOUNT_COLUMN_NAME_ID = "AccountNumber";
        public static final String USER_COLUMN_NAME_ID = "UserID";

        // This is where the non-unique columns for the transaction table
        // are specified.
        public static final String TRANSACTION_COLUMN_NAME_AMOUNT = "Amount";
        public static final String TRANSACTION_COLUMN_NAME_TYPE = "Type";
        public static final String TRANSACTION_COLUMN_NAME_TIMESTAMP = "Timestamp";
        public static final String TRANSACTION_COLUMN_NAME_CATEGORY = "Category";
        // This is where the non-unique columns for the account table
        // are specified.
        public static final String ACCOUNT_COLUMN_NAME_INTEREST = "Interest";
        public static final String ACCOUNT_COLUMN_NAME_BALANCE = "Balance";

        // This is where the non-unique columns for the user table are
        // specified.
        public static final String USER_COLUMN_NAME_PASSWORD = "Password";
    }
}