package com.cyberdynefinances;

import java.util.ArrayList;

public class AccountManager
{

	//interfaces with accounts and is the only thing that can because the Account class is protected
	private static ArrayList<Account> accountList = new ArrayList<Account>();
	private static String owner;
	private static Account activeAccount = new Account("Test", 100, 2);
	
	public static void loadUser(String user)
	{
		owner = user;
		readAccounts();
	}
	
	public static String getActiveUser()
	{
		return owner;
	}
	
	public static String getTransactionHist()
	{
		return activeAccount.getAccountInfo();
	}
	
	public static void deposit(String category, double amount)
	{
		activeAccount.deposit(category, amount);
	}
	
	public static boolean withdraw(String category, double amount)
	{
		return activeAccount.withdraw(category, amount);
	}
	
	public static void readAccounts()
	{
		//load list of accounts owned by active user into accountList
		//set activeAccount to first account in list
	}
	
	public static void writeAccounts()
	{
		//writes all accounts in account list to the database including their lists of categories
	}
	
	public static ArrayList<Account> getAccountList()
	{
		return accountList;
	}
	
	public static Account getActiveAccount()
	{
		if(activeAccount==null)
			return new Account("Test", 100, 2);
		return activeAccount;
	}
	
	public static void addAccount(Account acc)
	{
		accountList.add(acc);
		activeAccount = acc;
	}
}
