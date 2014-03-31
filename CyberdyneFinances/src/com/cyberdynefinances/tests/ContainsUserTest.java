package com.cyberdynefinances.tests;

import com.cyberdynefinances.WelcomeContainer;
import com.cyberdynefinances.dbManagement.DBHandler;
import android.test.ActivityInstrumentationTestCase2;


/**
 * ContainsUserTesting class.
 * 
 * @author Clayton Pierce
 *
 */
public class ContainsUserTest extends ActivityInstrumentationTestCase2<WelcomeContainer> 
{    
    /**
     * Test Constructor.
     */
    @SuppressWarnings("deprecation")
    public ContainsUserTest() 
    {
        super("com.cyberdynefinances.WelcomContainer", WelcomeContainer.class);
    }
    
    /**
     * Tests that a user can be added and the information is stored in the database.
     */
    public void testContainsUser()
    {
        //CHECKSTYLE:OFF    suppress error strings occur multiple times in file
        DBHandler.addUser("Kang", "pass123");
        assertTrue(DBHandler.containsUser("Kang"));
        assertFalse(DBHandler.containsUser("Thanos"));
        assertFalse(DBHandler.containsUser("Ultron"));
        //CHECKSTYLE:ON
    }
    
    /**
     * Tests trying to check a null user.
     */
    public void testContainsNullUser()
    {
        DBHandler.addUser(null, "pass123");
        assertFalse(DBHandler.containsUser(null));
    }
    
    /**
     * Tests trying to contain a user with the empty string as parameters.
     */
    public void testContainsEmtpyUser()
    {
        DBHandler.addUser("", "pass123");
        assertFalse(DBHandler.containsUser(""));
    }
    
    /**
     * Tests that it contains a removed user.
     */
    public void testContainsRemovedUser()
    {
        //CHECKSTYLE:OFF - Multiple Strings
        DBHandler.addUser("SilverSurfer", "Galactus");
        DBHandler.deleteUser("SilverSurfer");
        assertFalse(DBHandler.containsUser("SilverSurfer"));
        //CHECKSTYLE:ON
    }
    
    /**
     * Tests that contains multiple users.
     */
    public void testContainsManyMuchUsers()
    {
        //CHECKSTYLE:OFF - Multiple Strings
        DBHandler.addUser("IronMan", "Jarvis");
        DBHandler.addUser("CaptainMurica", "Bucky");
        DBHandler.addUser("Antman", "HenryPym");
        DBHandler.addUser("Thor", "Mjonir");
        DBHandler.addUser("Hawkeye", "BlackWidow");
        //CHECKSTYLE:ON
        assertTrue(DBHandler.containsUser("Antman"));
        assertFalse(DBHandler.containsUser("KangTheConqueror")); // He is no Avenger!
        assertTrue(DBHandler.containsUser("CaptainMurica"));
        DBHandler.deleteUser("Antman");
        assertFalse(DBHandler.containsUser("Antman"));
        DBHandler.addUser("Antman", "ScottLang");   // Add the new Antman!
        assertTrue(DBHandler.containsUser("Antman"));
    }
}