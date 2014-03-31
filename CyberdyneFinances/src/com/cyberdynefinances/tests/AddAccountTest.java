package com.cyberdynefinances.tests;

import com.cyberdynefinances.WelcomeContainer;
import com.cyberdynefinances.dbManagement.DBHandler;
import android.test.ActivityInstrumentationTestCase2;
/**
 * This class is a JUnit test for the addAccount method in the DBHandler.
 * 
 * @author Robert McBride 
 * @version 3.14
 */
public class AddAccountTest extends ActivityInstrumentationTestCase2<WelcomeContainer> {
    //CHECKSTYLE:OFF - private variables, no javadocs neeeded.
    private String testName = "Test";
    private String test1 = "1";
    private String two = "2";
    private String three = "3";
    private String four = "4";
    private String testBalance = "100.0";
    private String testInterest = "0.01";
    //CHECKSTYLE:ON
    /**
     * Constructor necessary for testing. 
     * Needed to test that activity/DBHandler because this is handled by the phone/android API.
     */
    @SuppressWarnings("deprecation")
    public AddAccountTest() {
        super("com.cyberdynefinances.WelcomContainer", WelcomeContainer.class);
    }
    
    /**
     * This method tests that when an addAccount() is called that the information is actually added. 
     * 
     */
    public void testAddAccount() {
        assertTrue(DBHandler.addAccount(testName, test1, 100.0, 0.01));
        String[] accInfo = DBHandler.getAccountInfo(test1);
        assertEquals(testName, accInfo[1]);
        assertEquals(test1, accInfo[0]);
        assertEquals(testBalance, accInfo[2]);
        assertEquals(testInterest, accInfo[3]);
        DBHandler.deleteAccount(test1);
    }
    
    /**
     * This method tests that when an addAccount() is called, but that invalid info is supplied,
     *  that the information is NOT actually added to the database. 
     * 
     */
    public void testAddAccountWithInvalidInfo() {
        assertFalse(DBHandler.addAccount(null, test1, 100, .01));
        assertFalse(DBHandler.addAccount(testName, null, 100, .01));
        assertFalse(DBHandler.addAccount(testName, test1, -0.01, .01));
        assertFalse(DBHandler.addAccount(testName, test1, 100, -0.01));
    }
    
    /**
     * Tests to make sure that two accounts of the same name are not added twice. 
     * 
     */
    public void testAddTwoAccountWithSameName() {
        assertTrue(DBHandler.addAccount(testName, test1, 100, .01));
        assertFalse(DBHandler.addAccount(testName, test1, 100, .01));
        DBHandler.deleteAccount(test1);
    }
    
    /**
     * This method tests adding multiple different accounts to the database.
     * All with the same user.
     * 
     */
    public void testAddMultipleAccountsWithSameUser() {
        assertTrue(DBHandler.addAccount(testName, test1, 100, .01));
        assertTrue(DBHandler.addAccount(testName, two, 200.1, .2));
        assertTrue(DBHandler.addAccount(testName, three, 300.1, .3));
        assertTrue(DBHandler.addAccount(testName, four, 400.1, .4));
        String[] accInfo = DBHandler.getAccountInfo(test1);
        assertEquals(testName, accInfo[1]);
        assertEquals(test1, accInfo[0]);
        assertEquals(testBalance, accInfo[2]);
        assertEquals(testInterest, accInfo[3]);
        accInfo = DBHandler.getAccountInfo(two);
        assertEquals(testName, accInfo[1]);
        assertEquals(two, accInfo[0]);
        assertEquals("200.1", accInfo[2]);
        assertEquals("0.2", accInfo[3]);
        accInfo = DBHandler.getAccountInfo(three);
        assertEquals(testName, accInfo[1]);
        assertEquals(three, accInfo[0]);
        assertEquals("300.1", accInfo[2]);
        assertEquals("0.3", accInfo[3]);
        accInfo = DBHandler.getAccountInfo(four);
        assertEquals(testName, accInfo[1]);
        assertEquals(four, accInfo[0]);
        assertEquals("400.1", accInfo[2]);
        assertEquals("0.4", accInfo[3]);
        DBHandler.deleteAccount(test1);
        DBHandler.deleteAccount(two);
        DBHandler.deleteAccount(three);
        DBHandler.deleteAccount(four);
    }
    
    /**
     * This method tests adding different accounts all for unique users. 
     * 
     */
    public void testAddMultipleAccountsWithUniqueUsers() {
        String testUser1 = "Test1";
        String testUser2 = "Test2";
        String testUser3 = "Test3";
        String testUser4 = "Test4";
        assertTrue(DBHandler.addAccount(testUser1, test1, 100, .01));
        assertTrue(DBHandler.addAccount(testUser2, two, 200, .02));
        assertTrue(DBHandler.addAccount(testUser3, three, 300, .03));
        assertTrue(DBHandler.addAccount(testUser4, four, 400, .04));
        String[] accInfo = DBHandler.getAccountInfo(test1);
        assertEquals(testUser1, accInfo[1]);
        assertEquals(test1, accInfo[0]);
        assertEquals(testBalance, accInfo[2]);
        assertEquals(testInterest, accInfo[3]);
        accInfo = DBHandler.getAccountInfo(two);
        assertEquals(testUser2, accInfo[1]);
        assertEquals(two, accInfo[0]);
        assertEquals("200.0", accInfo[2]);
        assertEquals("0.02", accInfo[3]);
        accInfo = DBHandler.getAccountInfo(three);
        assertEquals(testUser3, accInfo[1]);
        assertEquals(three, accInfo[0]);
        assertEquals("300.0", accInfo[2]);
        assertEquals("0.03", accInfo[3]);
        accInfo = DBHandler.getAccountInfo(four);
        assertEquals(testUser4, accInfo[1]);
        assertEquals(four, accInfo[0]);
        assertEquals("400.0", accInfo[2]);
        assertEquals("0.04", accInfo[3]);
        DBHandler.deleteAccount(test1);
        DBHandler.deleteAccount(two);
        DBHandler.deleteAccount(three);
        DBHandler.deleteAccount(four);        
    }
}

