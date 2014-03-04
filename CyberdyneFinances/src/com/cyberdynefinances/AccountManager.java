package com.cyberdynefinances;

import java.util.ArrayList;
import com.cyberdynefinances.dbManagement.DBHandler;

public class AccountManager
{
	private static DBHandler dbHandler = new DBHandler();
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
	
	public static void readAccounts()
	{
	    try {
	        String[] dbAccounts = dbHandler.getAccountsForUser(owner);
	        if (null != dbAccounts && dbAccounts.length > 0) {
                for (String dbAccount: dbAccounts) {
                    String[] curAcc = dbHandler.getAccountInfo(dbAccount);
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
	
	public static void addAccount(Account acc)
	{
		dbHandler.addAccount(owner, acc.getName(), acc.getBalance(), acc.getInterest());
		accountList.add(acc);
		activeAccount = acc;
	}
}
