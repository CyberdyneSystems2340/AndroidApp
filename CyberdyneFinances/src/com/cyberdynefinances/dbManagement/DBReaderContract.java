package com.cyberdynefinances.dbManagement;

import android.provider.BaseColumns;

/**
 * This method holds the table and column names for the database.
 * @author Cyberdyne Finances
 */
public final class DBReaderContract {

    /**
     * This class acts as a template class for the databases columns and tables. 
     */
    public abstract static class DBFactory implements BaseColumns {
        // Table names
        /**
         * Table name for the accounts table. 
         */
        public static final String ACCOUNT_TABLE_NAME = "Accounts";
        /**
         * Table name for the transactions table. 
         */
        public static final String TRANSACTION_TABLE_NAME = "Transactions";
        /**
         * Table name for the users table. 
         */
        public static final String USER_TABLE_NAME = "Users";

        // Identification columns for tables.
        /**
         * The unique column for for the accounts table, the id of that row. 
         */
        public static final String ACCOUNT_COLUMN_NAME_ID = "AccountNumber";
        /**
         * The unique column for for the users table, the id of that row. 
         */
        public static final String USER_COLUMN_NAME_ID = "UserID";

        // This is where the non-unique columns for the transaction table
        // are specified.
        /**
         * This is the name of the amount column in the transaction table.
         */
        public static final String TRANSACTION_COLUMN_NAME_AMOUNT = "Amount";
        /**
         * This is the name of the type column in the transaction table.
         */
        public static final String TRANSACTION_COLUMN_NAME_TYPE = "Type";
        /**
         * This is the name of the timestamp column in the transaction table.
         */
        public static final String TRANSACTION_COLUMN_NAME_TIMESTAMP = "Timestamp";
        /**
         * This is the name of the category column in the transaction table.
         */
        public static final String TRANSACTION_COLUMN_NAME_CATEGORY = "Category";
        // This is where the non-unique columns for the account table
        // are specified.
        /**
         * This is the name of the interest column in the accounts table.
         */
        public static final String ACCOUNT_COLUMN_NAME_INTEREST = "Interest";
        /**
         * This is the name of the balance column in the accounts table.
         */
        public static final String ACCOUNT_COLUMN_NAME_BALANCE = "Balance";

        // This is where the non-unique columns for the user table are
        // specified.
        /**
         * This is the name of the password column in the users table.
         */
        public static final String USER_COLUMN_NAME_PASSWORD = "Password";
    }
}