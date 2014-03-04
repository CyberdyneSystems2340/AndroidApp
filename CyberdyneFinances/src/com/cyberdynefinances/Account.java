package com.cyberdynefinances;

import java.util.ArrayList;

import com.cyberdynefinances.dbManagement.DBHandler;

public class Account 
{
	private static DBHandler dbHandler = new DBHandler();
	private String accountName;
	private double balance = 0.0;
	private double interest = 0.0;
	private ArrayList<String> categoriesDeposit = new ArrayList<String>();
	private ArrayList<String> categoriesWithdraw = new ArrayList<String>();
	
	public Account(String name, double balance, double interest)
	{
		accountName = name;
		this.balance = balance;
		this.interest = interest;
		String[] s = {"Birthday", "Parents", "Salary", "Scholarship"};
		for(String item:s)
			categoriesDeposit.add(item);
		String[] d = {"Clothing", "Entertainment", "Food", "Rent"};
		for(String item:d)
			categoriesWithdraw.add(item);
	}
	
	protected boolean withdraw(String category, double amount)
	{
		//remove amount from balance and register the transaction
		if(balance - amount < 0.00)
			return false;
		balance -= amount;
		registerTransaction("Withdraw", category, amount);
		return true;
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
		dbHandler.makeTransaction(accountName, amount, type, category);
		if(type.equalsIgnoreCase("deposit"))
		{
			if(!categoriesDeposit.contains(category))
				categoriesDeposit.add(category);
		}
		else
		{
			if(!categoriesWithdraw.contains(category))
				categoriesWithdraw.add(category);
		}
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
	public ArrayList<String> getCategoriesDeposit()
	{
		return categoriesDeposit;
	}
	
	public ArrayList<String> getCategoriseWithdraw()
	{
		return categoriesWithdraw;
	}
	
	@Override
	public String toString()
	{
		return getAccountInfo();
	}
	
}
