package com.cyberdynefinances;

public class Account 
{
	protected String accountName;
	protected double balance = 0.0;
	protected double interest = 0.0;
	
	public Account(String name, double balance, double interest)
	{
		accountName = name;
		this.balance = balance;
		this.interest = interest;
	}
	
	protected void withdraw(double amount)
	{
		//remove amount from balance and register the transaction
	}
	
	protected void deposit(double amount)
	{
		//add amount to balance and register the transaction
	}
	
	private String getTransactionHist()
	{
		//read in strings from account file
		return "";
	}
	
	protected String getAccountInfo()
	{
		String string = "";
		string += "Account Name: "+ accountName + "\n";
		string += "Owner: " + AccountManager.getActiveUser() + "\n";
		string += "Balance: " + balance + "\n";
		string += "Interest: " + interest + "\n";
		string += "Transaction History:\n" + getTransactionHist();
		return string;
	}
	
	private void registerTransaction(String type, double amount)
	{
		//writes the type, amount and timestamp/date of the transaction to the account file
	}
	
	@Override
	public String toString()
	{
		return getAccountInfo();
	}
	
}
