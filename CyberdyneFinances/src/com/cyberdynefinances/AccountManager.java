package com.cyberdynefinances;

import java.util.ArrayList;

<<<<<<< HEAD
import com.cyberdynefinances.dbManagement.DBHandler;

public class AccountManager extends Account
{
    private static DBHandler dbHandler = new DBHandler();

	//not used as this is a static class
	public AccountManager(String name, double balance, double interest) {super(name, balance, interest);}

=======
public class AccountManager
{
>>>>>>> c9a359a5239915fef69d038d9f51db7ea559a15d
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
<<<<<<< HEAD
        //TODO: Interact with DB
	    return account.getAccountInfo();
=======
		return activeAccount.getAccountInfo();
>>>>>>> c9a359a5239915fef69d038d9f51db7ea559a15d
	}
	
	public static void deposit(String category, double amount)
	{
<<<<<<< HEAD
        //TODO: Interact with DB
		account.deposit(category, amount);
=======
		activeAccount.deposit(category, amount);
>>>>>>> c9a359a5239915fef69d038d9f51db7ea559a15d
	}
	
	public static boolean withdraw(String category, double amount)
	{
<<<<<<< HEAD
        //TODO: Interact with DB
		account.withdraw(category, amount);
=======
		return activeAccount.withdraw(category, amount);
>>>>>>> c9a359a5239915fef69d038d9f51db7ea559a15d
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
<<<<<<< HEAD

        //TODO: writeAccounts to DB
		//writes all accounts in account list to the database
=======
		//writes all accounts in account list to the database including their lists of categories
>>>>>>> c9a359a5239915fef69d038d9f51db7ea559a15d
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
