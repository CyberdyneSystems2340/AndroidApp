package com.example.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by michael on 3/8/2014
 */
public class AccountRecordActivity extends Activity{
    private TextView _startDateField, _endDateField, _builtRecordView;
    private ListView _accountBalanceList;
    private TextView _totalBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_record);
    }


    // TODO Customize a menu for the AccountViewActivity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.accountsmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addaccount:
                // startActivityForResult(new Intent(this, AddAccountDialog.class), 1);
                // updateData();
                return true;
            case R.id.addtransaction:
//                addTransaction();
                return true;
            case R.id.refresh:
//                updateData();
                return true;
            case R.id.manageAccountMenuItem:
                Log.e(getClass().getName(), "Manage Accounts not implemented yet!");
                return true;
            case R.id.logOutMenuItem:
                ParseUser.logOut();
                startActivity(new Intent(this, LoginActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onButtonClick(View V) {
        _startDateField = (TextView) findViewById(R.id.editText_start_date);
        _endDateField = (TextView) findViewById(R.id.editText_end_date);
        String startDateString = _startDateField.getText().toString();
        String endDateString = _endDateField.getText().toString();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yy"); //Find a way to force slashes (our format)
        Date startDate = null, endDate = null;
        try {
            startDate = dateFormatter.parse(startDateString);
        } catch (java.text.ParseException e) {
            Toast.makeText(this, "Invalid Start Date", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            endDate = dateFormatter.parse(endDateString);
        } catch (java.text.ParseException e) {
            Toast.makeText(this, "Invalid End Date", Toast.LENGTH_SHORT).show();
            return;
        }

        if (endDate.compareTo(startDate) <= 0) {

            Toast.makeText(this, "End Date must be strictly later than Start Date!", Toast.LENGTH_SHORT).show();
            return;
        }

        AccountRecord report = new AccountRecord(startDate, endDate);
        //Toast.makeText(this, report.buildRecord(), Toast.LENGTH_SHORT).show();
         _builtRecordView= (TextView) findViewById(R.id.built_record_label);
         _builtRecordView.setText(report.buildRecord());
         _builtRecordView.setMovementMethod(new ScrollingMovementMethod());
    }

}
