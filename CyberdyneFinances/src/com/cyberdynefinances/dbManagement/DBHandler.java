package com.cyberdynefinances.dbManagement;

import com.cyberdynefinances.MyApplication;

public class DBHandler {
	AccountDBHelper dbHelper;
	public DBHandler() {
		dbHelper = new AccountDBHelper(MyApplication.getAppContext());
	}

	//TODO:Write method to handle adding a user to the db.
	public boolean addUser(String userID, String password, String accounts){
		return false;
	}
	
	//TODO:Write method to handle retrieving user info from db.
	public String[] getUser(String userID){
		String[] info = new String[2];//change 2 to the number of columns.
		return info;
	}
	
	//TODO:Write method to add account for user.
	
	//TODO:Write method to read accounts owned by user.
	
	//TODO:Write method to update account for user.
	
	//TODO:Write method to remove account from user.
	
	//TODO:Write a method to make a transaction for the user.
}