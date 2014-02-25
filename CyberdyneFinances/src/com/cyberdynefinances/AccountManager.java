package com.cyberdynefinances;

import java.util.ArrayList;

import com.cyberdynefinances.dbManagement.DBHandler;

public class AccountManager extends Account
{
    private static DBHandler dbHandler = new DBHandler();

	//not used as this is a static class
	public AccountManager(String name, double balance, double interest) {super(name, balance, interest);}

	//interfaces with accounts and is the only thing that can because the Account class is protected
	private static ArrayList<Account> accountList = new ArrayList<Account>();
	private static String owner;
	private static Account activeAccount;
	
	public static void loadUser(String user)
	{
		owner = user;
		readAccounts();
	}
	
	public static String getActiveUser()
	{
		return owner;
	}
	
	public static String getTransactionHist(Account account)
	{
        //TODO: Interact with DB
	    return account.getAccountInfo();
	}
	
	public static void deposit(Account account, String category, double amount)
	{
        //TODO: Interact with DB
		account.deposit(category, amount);
	}
	
	public static void withdraw(Account account, String category, double amount)
	{
        //TODO: Interact with DB
		account.withdraw(category, amount);
	}
	
	public static void readAccounts()
	{
        String[] dbAccounts = dbHandler.getAccountsForUser(owner);
        if (null != dbAccounts && dbAccounts.length > 0) {
            for (String dbAccount: dbAccounts) {
                String[] curAcc = dbHandler.getAccountInfo(dbAccount);
                accountList.add(new Account(dbAccount, Double.parseDouble(curAcc[2]), Double.parseDouble(curAcc[3])));
            }
            activeAccount = accountList.get(0);
        }
		//load list of accounts owned by active user into accountList
		//set activeAccount to first account in list
	}
	
	public static void writeAccounts()
	{

        //TODO: writeAccounts to DB
		//writes all accounts in account list to the database
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
	    new DBHandler().addAccount(owner, acc.getName(), acc.getBalance(), acc.getInterest());
		accountList.add(acc);
		activeAccount = acc;
	}
}
