package com.example.app;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Transaction")
public class Transaction extends ParseObject {
	public Transaction() {
		
	}
	
	public void setUser(ParseUser user) {
		put("user", user);
	}
	
	public void setMoney(double money) {
		put("money", money);
	}
	
	public double getMoney() {
		return getDouble("money");
	}
	
	public void setDate(String d) {
		put("date", d);
	}
	
	public String getDate() {
		return getString("date");
	}
}
