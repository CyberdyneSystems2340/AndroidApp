package com.cyberdynefinances;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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
	
	protected boolean withdrawWithDate(String category, double amount, String date)
    {
        //remove amount from balance and register the transaction
        if(balance - amount < 0.00)
            return false;
        balance -= amount;
        registerTransactionWithDate("Withdraw", category, amount, date);
        return true;
    }
    
    protected void depositWithDate(String category, double amount, String date)
    {
        //add amount to balance and register the transaction
        balance += amount;
        registerTransactionWithDate("Deposit", category, amount, date);
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
	
	private void registerTransactionWithDate(String type, String category, double amount, String date)
    {
        DBHandler.makeTransaction(accountName, amount, type, category, date);
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
	
	protected String getSpendingReport(Time dateStart, Time dateEnd)
	{
		String[][] str = DBHandler.getTransactionHistory(accountName);
		String curr = ""; // Current timeStamp
		String totalRep = ""; // All the withdrawals within the given parameters
		Map<String, Double> totalAmount = new HashMap<String, Double>();
		Time time = new Time();	// Time Object
		int day = 0;
		int month = 0;
		int year = 0;
		int sec = 0;
		int min = 0;
		int hour = 0;
		for(int i = 0; i < str.length; i++)
		{
			if(str[i][2].equalsIgnoreCase("Withdraw"))
			{
				curr = str[i][4];
				day = Integer.parseInt(curr.substring(0, 2));
				month = Integer.parseInt(curr.substring(3, 5));
				year = Integer.parseInt(curr.substring(6, 10));
				hour = Integer.parseInt(curr.substring(11, 13));
				min = Integer.parseInt(curr.substring(14,16));
				sec = Integer.parseInt(curr.substring(17, 19));
				time.set(sec, min, hour, day, month-1, year); //months start at 00 = January
				if(time.after(dateStart) && time.before(dateEnd))
				{
				    double curAmount = 0;
				    if(totalAmount.get(str[i][3])!=null)
				    {
				        curAmount = totalAmount.get(str[i][3]);
				    }
					totalAmount.put(str[i][3], curAmount-Double.parseDouble(str[i][1]));
				}
			}
		}
		double total = 0;
		for(Entry<String, Double> e : totalAmount.entrySet())
		{
		    totalRep += e.getKey()+" "+NumberFormat.getCurrencyInstance().format(e.getValue())+"\n";
		    total-=e.getValue();
		}
		totalRep += "Total "+NumberFormat.getCurrencyInstance().format(total)+"\n";
		return totalRep;
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
