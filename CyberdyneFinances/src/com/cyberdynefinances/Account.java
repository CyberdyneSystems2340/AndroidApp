package com.cyberdynefinances;

public class Account 
{
	private String accountName;
	private double balance = 0.0;
	private double interest = 0.0;
	
	public Account(String name, double balance, double interest)
	{
		accountName = name;
		this.balance = balance;
		this.interest = interest;
	}
	
	protected void withdraw(String category, double amount)
	{
		//remove amount from balance and register the transaction
		balance -= amount;
		registerTransaction("Withdraw", category, amount);
}
	
	protected void deposit(String category, double amount)
	{
		//add amount to balance and register the transaction
		balance += amount;
		registerTransaction("Deposit", category, amount);
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
	
	private void registerTransaction(String type, String category, double amount)
	{
        //TODO: Interact with DB
		//writes the type, category, amount, and timestamp/date of the transaction to the database
	}
	
	public double getBalance()
	{
		return balance;
	}
	
	public double getInterest()
	{
		return interest;
	}
	
	public String getName() {
	    return accountName;
	}
	
	@Override
	public String toString()
	{
		return getAccountInfo();
	}
	
}
