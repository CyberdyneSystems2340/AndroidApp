package com.example.app;

import android.accounts.Account;
import android.util.Log;

import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.NumberFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by michaelfalk on 3/9/14.
 */

public class AccountRecord {
    private Date start, end;
    private double balance;
    private List<Transaction> transactions;
    private List<String> accounts;
    private List<Double> accountBalances; //Don't HIT ME FOR THIS. Had to make parallel lists b/c time.

    /**
     * @param startDate beginning date from where to start reading transactions
     * @param endDate date to stop reading transactions
     */
    public AccountRecord(Date startDate, Date endDate) {


        start = new Date(startDate.getTime());
        end = new Date(endDate.getTime());
        this.balance = 0;
        accounts = new ArrayList<String>();
        accountBalances = new ArrayList<Double>();
    }

    /**
     * Creates formatted financial report
     * @return String representation of the financial report
     */

    public String buildRecord(){
        String out = "Account Report: \n";
        double totalBalance = 0;

//        DecimalFormat toMoney = (DecimalFormat)NumberFormat.getCurrencyInstance();
//        String symbol = toMoney.getCurrency().getSymbol();
//        toMoney.setNegativePrefix(symbol+"-"); // or "-"+symbol if that's what you need
//        toMoney.setNegativeSuffix("");
        DecimalFormat toMoney = new DecimalFormat("$#,##0.00;-$#,##0.00");

        out+="\nTransactions:\n";
        for (Transaction t: getRecords()) {

            if ((t.getTransactionDate().compareTo(start) >= 0) && (t.getTransactionDate().compareTo(end) <= 0)) {
            out += "\tAccount: "+t.getTransactionAccount().getAccountName() +":\n\tName: "+ t.getTransactionName() + "-\n\tDate: "+ t.getTransactionDate().toString() +"\n\tAmount Spent: " + toMoney.format(t.getTransactionValue()) + "\n\n" ;
            totalBalance += t.getTransactionValue();

                if (!accounts.contains(t.getTransactionAccount().toString())){
                    accounts.add(t.getTransactionAccount().toString());
                    accountBalances.add(new Double(0)); //Initialize subaccount balance
                }
                int index = accounts.indexOf(t.getTransactionAccount().toString());
                accountBalances.set(index, accountBalances.get(index)+t.getTransactionValue());
            }
        }


        out+="Account Balances:\n";
        for (int j = 0; j < accounts.size(); j++){
            out+="\t"+accounts.get(j)+": "+toMoney.format(accountBalances.get(j))+"\n";
        }

        out += "\n\tTotal Balance: "+toMoney.format(totalBalance)+"\n";

        return out;
    }

    /**
     * Finds records given date boundary information
     * @return List of transactions found between the start and end date
     */

    private List<Transaction> getRecords() {
        Log.i(getClass().getName(), "Now getting records!");
        // Mapping of Account names to Transaction lists to be returned.
        List<Transaction> list = new LinkedList<Transaction>();

        ParseQuery<Transaction> query = new ParseQuery(Transaction.class);
        // Constraint: all dates from start to end, inclusive
        query.whereEqualTo("date", start);
        query.whereGreaterThan("date", start);
        query.whereEqualTo("date", end);
        query.whereLessThan("date", end);
        // Make sure we only select our current user's transactions
        query.whereEqualTo("user", ParseUser.getCurrentUser());
        //include accounts into consideration
        query.include("userAccount");
        // Execute the search in the foreground (we don't want background)
        try {
            list = query.find();
            Log.i(getClass().getName(), "" + list.size());
        } catch (ParseException e) {
            Log.e(getClass().getName(), "Parse exception: " + e.getCode());
        }

        return list;
    }
}
