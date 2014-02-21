package com.cyberdynefinances;

public class AccountManager extends Account
{
	//not used as this is a static class
	public AccountManager(String name, double balance, double interest) {
		super(name, balance, interest);
		// TODO Auto-generated constructor stub
	}

	//interfaces with accounts and is the only thing that can because the Account class is protected
	private static Account[] accountList;
	private static String owner;
	private static Account activeAccount;
	
	public static void loadUser(String user)
	{
		owner = user;
		loadAccounts();
	}
	
	public static String getActiveUser()
	{
		return owner;
	}
	
	public static String getTransactionHist(Account account)
	{
		return account.getAccountInfo();
	}
	
	public static void deposit(Account account, double amount)
	{
		account.deposit(amount);
	}
	
	public static void withdraw(Account account, double amount)
	{
		account.withdraw(amount);
	}
	
	public static void loadAccounts()
	{
		//load list of accounts owned by acctive user into accountList
		//set activeAccount to first account in list
	}
	
	public static Account[] getAccountList()
	{
		return accountList;
	}
	
	public static Account getActiveAccount()
	{
		if(activeAccount==null)
			return new Account("Test", 100, 2);
		return activeAccount;
	}
	
	//TODO: method to add account to account list for user
}
