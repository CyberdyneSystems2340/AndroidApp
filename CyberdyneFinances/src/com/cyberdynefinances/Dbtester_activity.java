package com.cyberdynefinances;

import com.cyberdynefinances.DBReaderContract.DBEntry;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.widget.TextView;

public class Dbtester_activity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dbtester_activity);
		/*
		TextView tView = (TextView) findViewById(R.id.dbtester_textview);
		
		AccountDBHelper dbHelper = new AccountDBHelper(MyApplication.getAppContext());
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(DBReaderContract.DBEntry.USER_COLUMN_NAME_ID, "TestID");
		
		db.insert(DBReaderContract.DBEntry.USER_TABLE_NAME,
				  DBReaderContract.DBEntry.USER_COLUMN_NAME_ACCOUNTS,
				  values);

		db = dbHelper.getReadableDatabase();
		
		String[] projection = {DBEntry.USER_COLUMN_NAME_ID};
		
		String sortOrder = DBEntry.USER_COLUMN_NAME_ID + " DESC";
		String selection = "UserID";
		String[] selectionArgs = {"TestID"};
		Cursor c = db.query(DBEntry.USER_TABLE_NAME,
				projection,selection,selectionArgs, null, null, sortOrder);
		c.moveToFirst();
		String itemID = c.getString(c.getColumnIndex(DBEntry.USER_COLUMN_NAME_ID));
		
		tView.setText(itemID);*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dbtester_activity, menu);
		return true;
	}

}
