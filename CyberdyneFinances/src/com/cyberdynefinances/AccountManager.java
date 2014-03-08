package com.cyberdynefinances;

import java.util.ArrayList;

import android.text.format.Time;

import com.cyberdynefinances.dbManagement.DBHandler;

public class AccountManager
{
	//interfaces with accounts and is the only thing that can because the Account class is protected
	private static ArrayList<Account> accountList = new ArrayList<Account>();
	private static String owner;
	private static Account activeAccount = null;
	
	public static void loadUser(String user)
	{
		owner = user;
		accountList.clear();
		//activeAccount = null;
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
	
	
	public static String getSpendingReport(Time dateStart, Time dateEnd)
	{
		String total = "";
		for(Account a : accountList)
		{
			total += a.getSpendingReport(dateStart, dateEnd) + " ";
		}
		return total;
	}
	
	
	public static void readAccounts()
	{
	    try {
	        String[] dbAccounts = DBHandler.getAccountsForUser(owner);
	        if (null != dbAccounts && dbAccounts.length > 0) {
                for (String dbAccount: dbAccounts) {
                    String[] curAcc = DBHandler.getAccountInfo(dbAccount);
                    if (null != curAcc[2] && null != curAcc[3])
                    accountList.add(new Account(dbAccount, Double.parseDouble(curAcc[2]), Double.parseDouble(curAcc[3])));
                }
                activeAccount = accountList.get(0);
	        }
	    } catch(Exception e) { e.printStackTrace();}
	}
	
	public static void writeAccounts()
	{
		//TODO:writes all accounts in account list to the database including their lists of categories
	}
	
	public static ArrayList<Account> getAccountList()
	{
		return accountList;
	}
	
	public static Account getActiveAccount()
	{
		return activeAccount;
	}
	
	public static void setActiveAccount(String name)
	{
		for(Account acc: accountList)
		{
			if(acc.getName().equals(name))
			{
				activeAccount = acc;
				break;
			}
		}
	}
	
	public static boolean addAccount(Account acc)
	{
		boolean accountAdded  = DBHandler.addAccount(owner, acc.getName(), acc.getBalance(), acc.getInterest());
		if(accountAdded)
		{
		    accountList.add(acc);
	        activeAccount = acc;
		}
		return accountAdded;
	}
	
	public static String getReport(String report, Time begin, Time end)
	{
	    String ret = "";
	    
	    if(report.equalsIgnoreCase("Transaction History"))
	    {

	    }
	    else if(report.equalsIgnoreCase("Spending Category Report"))
	    {
	        //ret = getSpendingReport(begin, end);
	    }
	    else if(report.equalsIgnoreCase("Income Source Report"))
        {
            
        }
	    else if(report.equalsIgnoreCase("Cash Flow Report"))
        {
            
        }

	    return ret;
	}
}
