package com.example.app;

import java.util.List;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

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
	
	public String getAccountName() {
		return getString("accountName");
	}
	
	public void addTransaction(Transaction t) {
		// TODO implement this
	}
	
	public void removeTransaction(Transaction t) {
		// TODO implement this
	}
	
	public void setTxnList(List<Transaction> txnList) {
		put("txnList", txnList);
	}
	
	public void setUser(ParseUser user) {
		put("user", user);
	}
	
	@SuppressWarnings("unchecked")
	public List<Transaction> getTxnList() {
		return (List<Transaction>) getParseObject("txnList");
	}
}
