package com.cyberdynefinances;

public class Account 
{

	protected String accountName = "";
	protected double balance = 0.0;
	
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
		string += "Account: "+ accountName;
		string += "Owner: " + AccountManager.getOwner();
		string += "Balance: " + balance;
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
