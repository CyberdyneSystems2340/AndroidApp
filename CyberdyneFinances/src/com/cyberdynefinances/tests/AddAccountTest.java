package com.cyberdynefinances.tests;

import com.cyberdynefinances.WelcomeContainer;
import com.cyberdynefinances.dbManagement.DBHandler;
import android.test.ActivityInstrumentationTestCase2;

public class AddAccountTest extends ActivityInstrumentationTestCase2<WelcomeContainer> {
    
    public AddAccountTest() {
        super("com.cyberdynefinances.WelcomContainer", WelcomeContainer.class);
    }
    
    public void testAddAccount() {
        assertTrue(DBHandler.addAccount("Test", "1", 100.0, 0.01));
        String[] accInfo = DBHandler.getAccountInfo("1");
        assertEquals("Test", accInfo[1]);
        assertEquals("1", accInfo[0]);
        assertEquals("100.0", accInfo[2]);
        assertEquals("0.01", accInfo[3]);
        DBHandler.deleteAccount("1");
    }
    
    public void testAddAccountWithInvalidInfo() {
        assertFalse(DBHandler.addAccount(null, "1", 100, .01));
        assertFalse(DBHandler.addAccount("Test", null, 100, .01));
        assertFalse(DBHandler.addAccount("Test", "1", -0.01, .01));
        assertFalse(DBHandler.addAccount("Test", "1", 100, -0.01));
    }
    
    
    public void testAddTwoAccountWithSameName() {
        assertTrue(DBHandler.addAccount("Test", "1", 100, .01));
        assertFalse(DBHandler.addAccount("Test", "1", 100, .01));
        DBHandler.deleteAccount("1");
    }
    
    public void testAddMultipleAccountsWithSameUser() {
        assertTrue(DBHandler.addAccount("Test", "1", 100, .01));
        assertTrue(DBHandler.addAccount("Test", "2", 200, .02));
        assertTrue(DBHandler.addAccount("Test", "3", 300, .03));
        assertTrue(DBHandler.addAccount("Test", "4", 400, .04));        
        String[] accInfo = DBHandler.getAccountInfo("1");
        assertEquals("Test", accInfo[1]);
        assertEquals("1", accInfo[0]);
        assertEquals("100.0", accInfo[2]);
        assertEquals("0.01", accInfo[3]);
        accInfo = DBHandler.getAccountInfo("2");
        assertEquals("Test", accInfo[1]);
        assertEquals("2", accInfo[0]);
        assertEquals("200.0", accInfo[2]);
        assertEquals("0.02", accInfo[3]);
        accInfo = DBHandler.getAccountInfo("3");
        assertEquals("Test", accInfo[1]);
        assertEquals("3", accInfo[0]);
        assertEquals("300.0", accInfo[2]);
        assertEquals("0.03", accInfo[3]);
        accInfo = DBHandler.getAccountInfo("4");
        assertEquals("Test", accInfo[1]);
        assertEquals("4", accInfo[0]);
        assertEquals("400.0", accInfo[2]);
        assertEquals("0.04", accInfo[3]);
        DBHandler.deleteAccount("1");
        DBHandler.deleteAccount("2");
        DBHandler.deleteAccount("3");
        DBHandler.deleteAccount("4");
    }
    
    public void testAddMultipleAccountsWithUniqueUsers() {
        assertTrue(DBHandler.addAccount("Test1", "1", 100, .01));
        assertTrue(DBHandler.addAccount("Test2", "2", 200, .02));
        assertTrue(DBHandler.addAccount("Test3", "3", 300, .03));
        assertTrue(DBHandler.addAccount("Test4", "4", 400, .04));        
        String[] accInfo = DBHandler.getAccountInfo("1");
        assertEquals("Test1", accInfo[1]);
        assertEquals("1", accInfo[0]);
        assertEquals("100.0", accInfo[2]);
        assertEquals("0.01", accInfo[3]);
        accInfo = DBHandler.getAccountInfo("2");
        assertEquals("Test2", accInfo[1]);
        assertEquals("2", accInfo[0]);
        assertEquals("200.0", accInfo[2]);
        assertEquals("0.02", accInfo[3]);
        accInfo = DBHandler.getAccountInfo("3");
        assertEquals("Test3", accInfo[1]);
        assertEquals("3", accInfo[0]);
        assertEquals("300.0", accInfo[2]);
        assertEquals("0.03", accInfo[3]);
        accInfo = DBHandler.getAccountInfo("4");
        assertEquals("Test4", accInfo[1]);
        assertEquals("4", accInfo[0]);
        assertEquals("400.0", accInfo[2]);
        assertEquals("0.04", accInfo[3]);
        DBHandler.deleteAccount("1");
        DBHandler.deleteAccount("2");
        DBHandler.deleteAccount("3");
        DBHandler.deleteAccount("4");        
    }
}

