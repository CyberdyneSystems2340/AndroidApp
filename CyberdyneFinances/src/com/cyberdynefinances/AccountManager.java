package com.cyberdynefinances;

import java.util.ArrayList;
import android.text.format.Time;
import com.cyberdynefinances.dbManagement.DBHandler;

/**
 * Manages all accounts owned by a user. Is used to interact with accounts such as deposits, withdraws, and adding new accounts.
 * @author Cyberdyne Finances
 */
public class AccountManager {
    //CHECKSTYLE:OFF    suppress error of Missing Javadoc comment
    private static ArrayList<Account> accountList = new ArrayList<Account>();
    private static String owner;
    private static Account activeAccount = null;
    //CHECKSTYLE:ON
    /**
     * This method takes in a specific user name,
     *  sets it as the current owner of accounts,
     *  and gathers all accounts associated with that user into a collection.
     * 
     * @param user - The name of the user
     */
    public static void loadUser(String user) {
        owner = user;
        accountList.clear();
        readAccounts();
    }
    
    /**
     * This method returns the name of the current owner of the accounts. 
     * 
     * @return String of the name of the current owner.
     */
    public static String getOwner() {
        return owner;
    }

    /**
     * This method makes a deposit into the active account.
     *  
     * @param category - The category to associate with the deposit.
     * @param amount - The dollar amount to add to the account.
     */
    public static void deposit(String category, double amount) {
        activeAccount.deposit(category, amount);
    }

    /**
     * This method makes a withdrawal from the active account.
     *  
     * @param category - The category to associate with the withdrawal.
     * @param amount - The dollar amount to remove from the account.
     * @return True if the withdraw completed successfully, false if not.
     */
    public static boolean withdraw(String category, double amount) {
        return activeAccount.withdraw(category, amount);
    }

    /**
     * This method makes a deposit into the active account,
     *  and sets the date of the transactions manually. 
     *  
     * @param category - The category to associate with the deposit.
     * @param amount - The dollar amount to add to the account.
     * @param date - The date of the transaction.
     */
    public static void depositWithDate(String category, double amount, String date) {
        activeAccount.depositWithDate(category, amount, date);
    }

    /**
     * This method makes a withdrawal from the active account,
     *  and sets the date of the transactions manually. 
     *  
     * @param category - The category to associate with the withdrawal.
     * @param amount - The dollar amount to remove from the account.
     * @param date - The date of the transaction.
     * @return True if the withdraw completed successfully, false if not.
     */
    public static boolean withdrawWithDate(String category, double amount, String date) {
        return activeAccount.withdrawWithDate(category, amount, date);
    }

    /**
     * This method gets a spending report for the current account,
     *  between the dates specified.
     *  
     *  @param dateStart - The specific time, to the second, to start looking.
     *  @param dateEnd - The specific time, to the second, to stop looking.
     *  @return A String of the spending report, each row is a transaction.
     */
    public static String getSpendingReport(Time dateStart, Time dateEnd) {
        String total = "";
        for (Account a : accountList) {
            //CHECKSTYLE:OFF    suppress error of String "\n" occurs 2 times in file
            total += a.getName() + "\n" + a.getSpendingReport(dateStart, dateEnd) + "\n";
            //CHECKSTYLE:ON
        }
        return total;
    }

    /**
     * This method accesses the data handler and reads all of the accounts,
     * for the current owner, and stores them in a collection. Also,
     * it sets the first account in the collection to be the active account.
     * 
     */
    public static void readAccounts() {
        try {
            String[] dbAccounts = DBHandler.getAccountsForUser(owner);
            if (null != dbAccounts && dbAccounts.length > 0) {
                for (String dbAccount : dbAccounts) {
                    String[] curAcc = DBHandler.getAccountInfo(dbAccount);
                    if (null != curAcc[2] && null != curAcc[3]) {
                        accountList.add(new Account(dbAccount, Double.parseDouble(curAcc[2]), Double
                                .parseDouble(curAcc[3])));
                    }
                }
                activeAccount = accountList.get(0);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method returns and ArrayList of Account objects that represents
     * all the accounts in the collection currently. 
     * 
     * @return ArrayList of Account objects. 
     */
    public static ArrayList<Account> getAccountList() {
        return accountList;
    }

    /**
     * This method returns the currently active account.
     * 
     * @return Account object, this is the active account.
     */
    public static Account getActiveAccount() {
        return activeAccount;
    }

    /**
     * This method sets the active account to the one specified. 
     * 
     * @param name - The name of the new account that is to be set as active.
     */
    public static void setActiveAccount(String name) {
        for (Account acc : accountList) {
            if (acc.getName().equals(name)) {
                activeAccount = acc;
                break;
            }
        }
    }

    /**
     * This method adds a new account to the database, and if that succeeds,
     * it then adds it to the current collection and sets it to be active.
     * 
     * @param acc - The account object of the account to add.
     * @return True if succeeded, false if not.
     */
    public static boolean addAccount(Account acc) {
        boolean accountAdded = DBHandler.addAccount(owner, acc.getName(),
                acc.getBalance(), acc.getInterest());
        if (accountAdded) {
            accountList.add(acc);
            activeAccount = acc;
        }
        return accountAdded;
    }

    /**
     * This method gets the specified report, between two dates.
     * 
     * @param report - The name of the report to get.
     * Valid Report names are:
     * "Transaction History", "Spending Category Report",
     * "Income Source Report", "Cash Flow Report"
     * @param begin - The start time to collect reports from.
     * @param end - The end time to collect reports from.
     * @return A String of the report, each line is a transaction.
     */
    public static String getReport(String report, Time begin, Time end) 
    {
        String ret = "";
        Utils.formatPos.setNegativePrefix("");
        if (report.equalsIgnoreCase("Transaction History")) 
        {
            ret = getTransactionHistory(begin, end);
        } 
        else if (report.equalsIgnoreCase("Spending Category Report")) 
        {
            ret = getSpendingReport(begin, end);
        } 
        else if (report.equalsIgnoreCase("Income Source Report")) 
        {
            ret = getIncomeSourceReport(begin, end);
        } 
        else if (report.equalsIgnoreCase("Cash Flow Report")) 
        {
            ret = getCashFlowReport(begin, end);
        }
        else if (report.equalsIgnoreCase("Account Listing Report"))
        {
            ret = getAccountListingReport();
        }
        return ret;
    }
    
    /**
     * Generates the Account Listing Report.
     * @return String of the account listing report
     */
    public static String getAccountListingReport() 
    {
        String total = "";
        for (Account a : accountList) 
        {
            total += a.getName() + "\t" + Utils.formatPos.format(a.getBalance()) + "\n";
        }
        return total;
    }
    
    /**
     * Generates the Transaction History for an account.
     * @param begin Beginning time to consider transactions
     * @param end Ending time to consider transactions
     * @return String of the transaction history
     */
    public static String getTransactionHistory(Time begin, Time end)
    {
        Account a = AccountManager.getActiveAccount();
        return a.getName() + "\n" + a.getTransactionHistory(begin, end) + "\n";
    }
    
    /**
     * Generates the Income Source Report.
     * @param begin Beginning time to consider transactions
     * @param end Ending time to consider transactions
     * @return String of the income source report
     */
    public static String getIncomeSourceReport(Time begin, Time end)
    {
        String total = "";
        for (Account a : accountList) 
        {
            total += a.getName() + "\n" + a.getIncomeSourceReport(begin, end) + "\n";
        }
        return total;
    }
    
    /**
     * Generates the Cash Flow Report.
     * @param begin Beginning time to consider transactions
     * @param end Ending time to consider transactions
     * @return String of the cash flow report
     */
    public static String getCashFlowReport(Time begin, Time end)
    {
        String total = "";
        for (Account a : accountList) 
        {
            total += a.getName() + "\n" + a.getCashFlowReport(begin, end) + "\n\n";
        }
        return total;
    }
}
