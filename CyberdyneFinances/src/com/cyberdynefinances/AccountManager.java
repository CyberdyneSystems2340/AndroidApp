package com.cyberdynefinances;

import java.util.ArrayList;

import android.text.format.Time;

import com.cyberdynefinances.dbManagement.DBHandler;
//TODO: Add file header Jdoc.
public class AccountManager {
    // interfaces with accounts and is the only thing that can because the
    // Account class is protected
    //TODO: Turn off Checkstyle for Javadoc here, reason is because these are private variables.
    private static ArrayList<Account> accountList = new ArrayList<Account>();
    private static String owner;
    private static Account activeAccount = null;
    //TODO: Turn Checkstyle back on for Javadoc here.
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
        // activeAccount = null;
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
            //TODO: Check the following line to see if \n is needed!
            total += a.getName() + "\n" + a.getSpendingReport(dateStart, dateEnd) + "\n";
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
/* We will not be using this as far as I know.
    public static void writeAccounts() {
        // TODO:writes all accounts in account list to the database including
        // their lists of categories
    }
*/
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
    public static String getReport(String report, Time begin, Time end) {
        String ret = "";

        if (report.equalsIgnoreCase("Transaction History")) {

        } else if (report.equalsIgnoreCase("Spending Category Report")) {
            ret = getSpendingReport(begin, end);
        } else if (report.equalsIgnoreCase("Income Source Report")) {

        } else if (report.equalsIgnoreCase("Cash Flow Report")) {

        }

        return ret;
    }
}
