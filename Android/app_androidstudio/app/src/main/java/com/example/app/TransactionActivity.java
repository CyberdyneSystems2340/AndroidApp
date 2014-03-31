package com.example.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.method.KeyListener;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.ParseACL;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by richard on 3/3/14.
 */
public class TransactionActivity extends Activity {
    private ArrayList<EditText> _editTexts;
    private HashMap<EditText, KeyListener> _listeners;
    private EditText _name;
    private EditText _value;
    private EditText _date;
    private EditText _description;
    private Spinner _accountSpinner;
    private MediaPlayer mp;


    // TODO Clear text in txn name field on first tap on first create only
    // (or just highlight all of the text on selecting the EditText every time

    // TODO Replace the date field with a DatePicker and a TimePicker


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        _editTexts = new ArrayList<EditText>();
        _listeners = new HashMap<EditText, KeyListener>();
        _name = (EditText) findViewById(R.id.txncreate_txn_name);
        _value = (EditText) findViewById(R.id.txncreate_txn_value);
        _date = (EditText) findViewById(R.id.txncreate_txn_date);
        _description = (EditText) findViewById(R.id.txncreate_description);
        _accountSpinner = (Spinner) findViewById(R.id.txncreate_account_spinner);

        _editTexts.add(_name);
        _editTexts.add(_value);
        _editTexts.add(_date);
        _editTexts.add(_description);

        for (EditText e : _editTexts) {
            _listeners.put(e, e.getKeyListener());
        }

        mp = MediaPlayer.create(this.getApplicationContext(), R.raw.c17);
    }

    @Override
    protected void onStart() {
        super.onStart();
        _date.setText(new Date().toString());
        if (getIntent().hasExtra("edit")) {
            if (!getIntent().getBooleanExtra("edit", true)) {
                for (int i = 0; i < _editTexts.size(); i++) {
                    EditText e = _editTexts.get(i);
                    if (e != null) {
                        e.setKeyListener(null);
                        e.setEnabled(false);
                    }
                }
            }
            else {
                for (EditText e : _editTexts) {
                    if (e != null) {
                        e.setEnabled(true);
                        e.setKeyListener(_listeners.get(e));
                    }
                }
            }
        }
        else {
            for (EditText e : _editTexts) {
                e.setEnabled(true);
                e.setKeyListener(_listeners.get(e));
            }
        }

        // Now to populate the spinner with values
        ArrayList<String> ul = new ArrayList<String>();
        for (UserAccount u : ((List<UserAccount>)ParseSingleton.getInstance().get("accountsList"))) {
            ul.add(u.getAccountName());
        }

        ArrayAdapter<String> a = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ul);
        a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _accountSpinner.setAdapter(a);
        _accountSpinner.setSelection(getIntent().getIntExtra("accountID", 0));
    }

    public void createNewTransaction(View v) {
        Intent i = new Intent();

        if ((_name.getText().length() > 0) && !_name.getText().equals(getResources().getString(R.id.txncreate_txn_name)) && (_value.getText().length() > 0)) {
            Transaction t = new Transaction();
            t.setACL(new ParseACL(ParseUser.getCurrentUser()));
            t.setUser(ParseUser.getCurrentUser());
            t.setTransactionName(_name.getText().toString());
            t.setTransactionValue(Double.parseDouble(_value.getText().toString()));
            // TODO not this
            t.setTransactionAccount(((List<UserAccount>) ParseSingleton.getInstance().get("accountsList")).get(_accountSpinner.getSelectedItemPosition()));
            String date = _date.getText().toString();
            t.setTransactionDate(new Date(date));
            t.saveEventually();
            ParseSingleton.getInstance().put("newTransaction", t);
            i.putExtra("transactionModified", true);
//            i.putExtra("transactionName", _name.getText().toString());
//            i.putExtra("transactionValue", Double.parseDouble(_value.getText().toString()));
//            i.putExtra("transactionDate", date);
//            i.putExtra("transactionAccountID", _accountSpinner.getSelectedItemPosition());
            setResult(RESULT_OK, i);
            mp.start();
        }
        else {
            Context context = getApplicationContext();
            CharSequence text = "Invalid Inputs";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            setResult(RESULT_CANCELED, i);
        }

        finish();
    }
}
