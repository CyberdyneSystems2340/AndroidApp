package com.cyberdynefinances;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import android.text.format.Time;
import com.cyberdynefinances.dbManagement.DBHandler;

/**
 * @author various Cyberdyne members
 */
public class Account 
{
    //CHECKSTYLE:OFF    suppress error of Missing Javadoc comment
    private String accountName;
    private double balance = 0.0;
    private double interest = 0.0;
    private String withdraw = "Withdraw";
    private String deposit = "Deposit";
    private String totalStr = "Total ";
    private ArrayList<String> categoriesDeposit = new ArrayList<String>();
    private ArrayList<String> categoriesWithdraw = new ArrayList<String>();
    //CHECKSTYLE:ON
	
    /**
     * The account class holds the user's name, balance, and interest.
     * 
     * @param name - The user's name
     * @param balance - The user's balance
     * @param interest - The user's interest
     */
    //CHECKSTYLE:OFF - hides a field error
    public Account(String name, double balance, double interest)
    {
    //CHECKSTYLE:ON
        accountName = name;
        this.balance = balance;
        this.interest = interest;
        String[] s = {"Birthday", "Parents", "Salary", "Scholarship"};
        for (String item:s)
        {
            categoriesDeposit.add(item);
        }
        String[] d = {"Clothing", "Entertainment", "Food", "Rent"};
        for (String item:d)
        {
            categoriesWithdraw.add(item);
        }
    }
	
    /**
     * The user withdraws money from is account.
     * 
     * @param category - The category of his choice
     * @param amount - The amount of his choice
     * @return - the returned amount
     */
    protected boolean withdraw(String category, double amount)
    {
        if (balance - amount < 0.00)
        {
            return false;
        }
        balance -= amount;
        registerTransaction(withdraw, category, amount);
        return true;
    }
	
    /**
     * The user deposits his money into the account. 
     * 
     * @param category - The category of his choice
     * @param amount - The amount he wants to give
     */
    protected void deposit(String category, double amount)
    {
		//add amount to balance and register the transaction
        balance += amount;
        registerTransaction(deposit, category, amount);
    }
	/**
	 * The user withdraws the amount he wishes during a certain date.
	 * @param category - The category of his choice
	 * @param amount - The amount he wishes to withdraw
	 * @param date - The date of his choice
	 * @return - The amount of his choice
	 */
    protected boolean withdrawWithDate(String category, double amount, String date)
    {
        //remove amount from balance and register the transaction
        if (balance - amount < 0.00)
        {
            return false;
        }
        balance -= amount;
        registerTransactionWithDate(withdraw, category, amount, date);
        return true;
    }
    
    /**
     * The user deposits the amount he wishes during a certain date.
     * @param category - The category of his choice
     * @param amount - The amount he wishes to deposit
     * @param date - The date of his choice
     */
    protected void depositWithDate(String category, double amount, String date)
    {
        //add amount to balance and register the transaction
        balance += amount;
        registerTransactionWithDate(deposit, category, amount, date);
    }
	
    /**
     * This determines whether to transact a deposit or withdraw.
     * 
     * @param type - Deposit or withdraw
     * @param category - The category of choice
     * @param amount - The amount to add or deduct
     */
    private void registerTransaction(String type, String category, double amount)
    {
       
        DBHandler.makeTransaction(accountName, amount, type, category);
        transactionHelper(type, category, amount);
		//writes the type, category, amount, and timestamp/date of the transaction to the database
    }
	
    /**
     * Similar to the registerTrans but with a date.
     * 
     * @param type - Withdraw or deposit
     * @param category - The category chosen
     * @param amount - The amount to transact
     * @param date - The date of the user's choice
     */
    private void registerTransactionWithDate(String type, String category, double amount, String date)
    {
        DBHandler.makeTransaction(accountName, amount, type, category, date);
        transactionHelper(type, category, amount);
        //writes the type, category, amount, and timestamp/date of the transaction to the database
    }
    
    /**
     * Used to help the two transaction methods.
     * 
     * @param type - Withdraw or deposit
     * @param category - The category chosen
     * @param amount - The amount to transact
     */
    private void transactionHelper(String type, String category, double amount)
    {
        if (type.equalsIgnoreCase(deposit))
        {
            if (!categoriesDeposit.contains(category))
            {
                categoriesDeposit.add(category);
            }
        }
        else
        {
            if (!categoriesWithdraw.contains(category))
            {
                categoriesWithdraw.add(category);
            }
        }
    }
	
	/**
	 * The report the return the total spending between given dates.
	 * 
	 * @param dateStart - The begin date
	 * @param dateEnd - The end date
	 * @return - a String of the total report
	 */
    protected String getSpendingReport(Time dateStart, Time dateEnd)
    {
        String[][] str = DBHandler.getTransactionHistory(accountName);
        if (null == str) 
        {
            //CHECKSTYLE:OFF    suppress error "/n" occurs 3 times in file
            return totalStr + NumberFormat.getCurrencyInstance().format(0.0) + "\n";
            //CHECKSTYLE:ON
        }
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
        for (int i = 0; i < str.length; i++)
        {
            if (str[i][2].equalsIgnoreCase(withdraw))
            {
                curr = str[i][4];
                day = Integer.parseInt(curr.substring(0, 2));
                month = Integer.parseInt(curr.substring(3, 5));
                year = Integer.parseInt(curr.substring(6, 10));
                hour = Integer.parseInt(curr.substring(11, 13));
                min = Integer.parseInt(curr.substring(14, 16));
                sec = Integer.parseInt(curr.substring(17, 19));
                time.set(sec, min, hour, day, month - 1, year); //months start at 00 = January
                if (time.after(dateStart) && time.before(dateEnd))
                {
                    double curAmount = 0;
                    if (totalAmount.get(str[i][3]) != null)
                    {
                        curAmount = totalAmount.get(str[i][3]);
                    }
                    totalAmount.put(str[i][3], curAmount - Double.parseDouble(str[i][1]));
                }
            }
        }
        double total = 0;
        for (Entry<String, Double> e : totalAmount.entrySet())
        {
            totalRep += e.getKey() + " " + NumberFormat.getCurrencyInstance().format(e.getValue()) + "\n";
            total -= e.getValue();
        }
        totalRep += totalStr + NumberFormat.getCurrencyInstance().format(total) + "\n";
        return totalRep;
    }
	
    /**
     * Returns the balance of the account.
     * 
     * @return - Total balance
     */
    public double getBalance()
    {
        return balance;
    }
	
    /**
     * Returns the interest of the account.
     * 
     * @return - The interest
     */
    public double getInterest()
    {
        return interest;
    }
	
    /**
     * The name of the account.
     * 
     * @return - The name of the account
     */
    // CHECKSTYLE:OFF   Unexpected getter name(getName and CategorizeWithraw)
    public String getName()
    {
        return accountName;
    }
	
    /**
     * The report of the deposited categories.
     * 
     * @return - ArrayList of Strings(categories)
     */
    public ArrayList<String> getCategoriesDeposit()
    {
        return categoriesDeposit;
    }
	
    /**
     * The report of the withdrawn categories.
     * 
     * @return - ArrayList of Strings(categories)
     */
    public ArrayList<String> getCategoriseWithdraw()
    {
    //CHECKSTYLE:ON
        return categoriesWithdraw;
    }
	
    @Override
    public String toString()
    {
        return "Name: " + accountName + " Balance: " + balance;
    }
}
