package com.cyberdynefinances.tests;

import com.cyberdynefinances.WelcomeContainer;
import com.cyberdynefinances.dbManagement.DBHandler;
import android.test.ActivityInstrumentationTestCase2;

/**
 *Tests adding a username and password to the database.
 *@author Richard Chaussee
 */
public class AddUserTest extends ActivityInstrumentationTestCase2<WelcomeContainer>
{
    /**
     * Test Constructor.
     */
    @SuppressWarnings("deprecation")
    public AddUserTest() 
    {
        super("com.cyberdynefinances.WelcomContainer", WelcomeContainer.class);
    }
    
    /**
     * Tests that a user can be added and the information is stored in the database.
     */
    public void testAddUser()
    {
        //CHECKSTYLE:OFF    suppress error strings occur multiple times in file
        assertTrue(DBHandler.addUser("Test", "pass123"));
        //CHECKSTYLE:ON
        String[] info = DBHandler.getUserInfo("Test");
        assertEquals("Test", info[0]);
        assertEquals("pass123", info[1]);
    }
    
    /**
     * Tests trying to add a user with null parameters.
     */
    public void testAddNullUser()
    {
        assertFalse(DBHandler.addUser(null, "pass123"));
        assertFalse(DBHandler.addUser("Test", null));
        assertFalse(DBHandler.addUser(null, null));
    }
    
    /**
     * Tests trying to add a user with the empty string as parameters.
     */
    public void testAddEmtpyUser()
    {
        assertFalse(DBHandler.addUser("", "pass123"));
        assertFalse(DBHandler.addUser("Test", ""));
        assertFalse(DBHandler.addUser("", ""));
    }

    /**
     * Tests that multiple users can be added and their information is stored correctly.
     */
    public void testAddMultipleUsers()
    {
        //CHECKSTYLE:OFF    suppress error strings occur multiple times in file
        assertTrue(DBHandler.addUser("Test", "pass123"));
        assertTrue(DBHandler.addUser("Test1", "apple"));
        assertTrue(DBHandler.addUser("Test2", "orange"));
        assertTrue(DBHandler.addUser("Test3", "banana"));
        assertTrue(DBHandler.addUser("Test4", "drywall"));
        //CHECKSTYLE:ON
        String[] info = DBHandler.getUserInfo("Test");
        assertEquals("Test", info[0]);
        assertEquals("pass123", info[1]);
        
        info = DBHandler.getUserInfo("Test1");
        assertEquals("Test1", info[0]);
        assertEquals("apple", info[1]);
        
        info = DBHandler.getUserInfo("Test2");
        assertEquals("Test2", info[0]);
        assertEquals("orange", info[1]);
        
        info = DBHandler.getUserInfo("Test3");
        assertEquals("Test3", info[0]);
        assertEquals("banana", info[1]);
        
        info = DBHandler.getUserInfo("Test4");
        assertEquals("Test4", info[0]);
        assertEquals("drywall", info[1]);
    }
}
