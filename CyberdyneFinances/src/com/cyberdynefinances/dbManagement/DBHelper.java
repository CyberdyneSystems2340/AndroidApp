/**
 * This class is used be the DBHandler to interact with the database.
 * It sets up the tables for the database.
 * 
 * @author Robert
 * @version 3.14
 */

package com.cyberdynefinances.dbManagement;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.cyberdynefinances.MyApplication;
import com.cyberdynefinances.dbManagement.DBReaderContract.DBFactory;

public final class DBHelper extends SQLiteOpenHelper {
    private static final String OPARA = " (";
    private static final String CPARA = ")";
    private static final String COMMA = ",";
    private static final String TYPETEXT = " TEXT";
    private static final String TEXTID = " TEXT PRIMARY KEY";
    private static final String CREATETABLE = "CREATE TABLE ";
    private static final String DROPTABLE = "DROP TABLE IF EXISTS ";

    // SQL Commands for creating the account, user, and transaction tables.
    private static final String SQL_CREATE_USER_ENTRIES = CREATETABLE
            + DBFactory.USER_TABLE_NAME + OPARA + DBFactory.USER_COLUMN_NAME_ID
            + TEXTID + COMMA + DBFactory.USER_COLUMN_NAME_PASSWORD + TYPETEXT
            + CPARA;

    private static final String SQL_CREATE_ACCOUNT_ENTRIES = CREATETABLE
            + DBFactory.ACCOUNT_TABLE_NAME + OPARA
            + DBFactory.ACCOUNT_COLUMN_NAME_ID + TEXTID + COMMA
            + DBFactory.USER_COLUMN_NAME_ID + TYPETEXT + COMMA
            + DBFactory.ACCOUNT_COLUMN_NAME_BALANCE + TYPETEXT + COMMA
            + DBFactory.ACCOUNT_COLUMN_NAME_INTEREST + TYPETEXT + CPARA;

    private static final String SQL_CREATE_TRANSACTION_ENTRIES = CREATETABLE
            + DBFactory.TRANSACTION_TABLE_NAME
            + OPARA
            + DBFactory.ACCOUNT_COLUMN_NAME_ID
            + TYPETEXT
            + COMMA
            + DBFactory.TRANSACTION_COLUMN_NAME_AMOUNT
            + TYPETEXT
            + COMMA
            + DBFactory.TRANSACTION_COLUMN_NAME_TYPE
            + TYPETEXT
            + COMMA
            + DBFactory.TRANSACTION_COLUMN_NAME_CATEGORY
            + TYPETEXT
            + COMMA
            + DBFactory.TRANSACTION_COLUMN_NAME_TIMESTAMP + TEXTID + CPARA;

    // SQL Commands to delete any of the above created tables.
    private static final String SQL_DELETE_USER_ENTRIES = DROPTABLE
            + DBReaderContract.DBFactory.USER_TABLE_NAME;
    private static final String SQL_DELETE_ACCOUNT_ENTRIES = DROPTABLE
            + DBReaderContract.DBFactory.ACCOUNT_TABLE_NAME;
    private static final String SQL_DELETE_TRANSACTION_ENTRIES = DROPTABLE
            + DBReaderContract.DBFactory.TRANSACTION_TABLE_NAME;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "CyberdyneFinancesDB";

    public DBHelper(Context context) {
        super(MyApplication.getAppContext(), DATABASE_NAME, null,
                DATABASE_VERSION);
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
}