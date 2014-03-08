package com.cyberdynefinances;

import java.util.ArrayList;

import android.text.format.Time;

import com.cyberdynefinances.dbManagement.DBHandler;

public class Account 
{
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
	
	protected String getSpendingReport(Time dateStart, Time dateEnd)
	{
		String[][] str = DBHandler.getTransactionHistory(accountName);
		String curr = "";
		Time time = new Time();
		int day = 0;
		int month = 0;
		int year = 0;
		int sec = 0;
		int min = 0;
		int hour = 0;
		// Comment stuff
		for(int i = 0; i < str[4].length; i++)
		{
			curr = str[4][i];
			day = Integer.parseInt(curr.substring(0, 2));
			month = Integer.parseInt(curr.substring(3, 5));
			year = Integer.parseInt(curr.substring(6, 10));
			
		}
		return "";
		/**
		String[][] str = DBHandler.getTransactionHistory(accountName);
		String subInc = "";	// Substring inclusive (Database: 01.05.2001)
		String subExS = "";	// Substring exclusive Start (dateStart: 01.04.2000) 
		String subExE = ""; // Substring exclusive End (dateEnd: 05.06.2002
		String total = "";
		for(int i = 0; i < str[4].length; i ++)
		{
			// Make sure it is a withdrawal
			if(str[2][i].equals("Withdrawal"))
			{
				subExS = dateStart.substring(6, 0);
				subExE = dateEnd.substring(6, 10);
				subInc = str[4][i].substring(6, 10);
				// If the year is within the dateStart and dateEnd, else continue
				if((Double.parseDouble(subExS) <= Double.parseDouble(subInc)) &&
						Double.parseDouble(subExE) >= Double.parseDouble(subInc))
				{
					subExS = dateStart.substring(0, 2);
					subExE = dateEnd.substring(0, 2);
					subInc = str[4][i].substring(0, 2);
					// If the month is within the dateStart and DateEnd, else continue
					if((Double.parseDouble(subExS) <= Double.parseDouble(subInc)) &&
							Double.parseDouble(subExE) >= Double.parseDouble(subInc))
					{
						subExS = dateStart.substring(3, 5);
						subExE = dateEnd.substring(3, 5);
						subInc = str[4][i].substring(3, 5);
						// If the day is within the dateStart and dateEnd, else continue
						if((Double.parseDouble(subExS) <= Double.parseDouble(subInc)) &&
								Double.parseDouble(subExE) >= Double.parseDouble(subInc))
						{
							total += str[4][i] + " ";
						}
						else
							continue;
					}
					else
						continue;
				}
				else
					continue;
			}
			else
				continue;
		}
		return total;
		*/
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
		DBHandler.makeTransaction(accountName, amount, type, category);
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
