package com.cyberdynefinances.tests;

import com.cyberdynefinances.WelcomeContainer;
import com.cyberdynefinances.dbManagement.DBHandler;

import android.test.ActivityInstrumentationTestCase2;

/**
 *Tests the deletion of all the accounts of the user.
 *@author Brian Hassan
 */
public class DeleteAccountsTest extends ActivityInstrumentationTestCase2<WelcomeContainer>
{
    //CHECKSTYLE:OFF
    String user = "user";
    String acc = "acc";
    String acc2 = "acc2";
    //CHECKSTYLE:ON
    /**
     * Test Constructor.
     */
    @SuppressWarnings("deprecation")
    public DeleteAccountsTest() 
    {
        super("com.cyberdynefinances.WelcomContainer", WelcomeContainer.class);
    }
    /** Single account test.
     * 
     */
    public void test1() {
        DBHandler.addUser(user, "13");
        DBHandler.addAccount( user , acc, 1000 , 1 );
        String[] arr = DBHandler.getAccountsForUser( user );
        assertEquals( arr[0] , acc );
        DBHandler.deleteAccountsForUser( user );
        String[] arr2 = DBHandler.getAccountsForUser( user );
        assertEquals(arr2 , null);
    }
    /** Multiple Accounts Test.
     * 
     */
    public void test2() {
        DBHandler.addUser(user, "123");
        DBHandler.addAccount(user , acc , 1000 , 1);
        DBHandler.addAccount(user , acc2 , 1233 , 3);
        String[] arr = DBHandler.getAccountsForUser(user);
        assertEquals(arr[0] , acc);
        assertEquals(arr[1] , acc2);
        DBHandler.deleteAccountsForUser(user);
        String[] arr2 = DBHandler.getAccountsForUser(user);
        assertEquals(arr2 , null);
    }
    /**
     * No Accounts should trivially return true.
     * 
     */
    public void test3() {
        DBHandler.addUser(user, "3");
        assertTrue(DBHandler.deleteAccountsForUser(user));   
    }
}