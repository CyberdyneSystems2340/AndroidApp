package com.example.app;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.List;

@ParseClassName("Account")
public class UserAccount extends ParseObject {

    public UserAccount() {
        super();
    }

    public UserAccount(String accountName) {
        setAccountName(accountName);
    }

    public void setAccountName(String s) {
        put("accountName", s);
    }

    public void setInitialValue(double d) {
        put("initialValue", d);
    }

    public double getInitialValue() {
        return getDouble("initialValue");
    }

    public String getAccountName() {
        return getString("accountName");
    }

    public void setAccountValue(double d) {
        put("accountValue", d);
    }

    public double getAccountValue() {
        return getDouble("accountValue");
    }

    public void setTxnList(List<Transaction> txnList) {
        put("txnList", txnList);
    }

    public void setUser(ParseUser user) {
        put("user", user);
    }

    @Override
    public String toString() {
        return getString("accountName");
    }
}
