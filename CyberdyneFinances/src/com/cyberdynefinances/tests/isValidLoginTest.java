package com.cyberdynefinances.tests;

import com.cyberdynefinances.LoginHandler;
import com.cyberdynefinances.WelcomeContainer;
import com.cyberdynefinances.dbManagement.DBHandler;
import android.test.ActivityInstrumentationTestCase2;
/**
 * A JUnit that tests the functionality of the isValidLogin method within
 *  LoginHandler.
 *
 * @author audreyvnelson
 *
 */
public class IsValidLoginTest extends ActivityInstrumentationTestCase2<WelcomeContainer> {

	/**
	 * Constructor for test.
	 */
    public IsValidLoginTest() {
        super("com.cyberdynefinances.WelcomContainer", WelcomeContainer.class);
    }

    /**
     * Tests the isValidLogin method with the correct parameters.
     */
    public void testLogin() {
    	//CHECKSTYLE:OFF (string "admin" occurs 3 times within file)
        assertTrue(LoginHandler.isValidLogin("admin", "pass123"));
        //CHECKSTYLE:ON
    }

    /**
     * Tests the isValidLogin method with the incorrect username.
     */
    public void testLoginWithInvalidUsername() {
    	//CHECKSTYLE:OFF (string "wrong" appears 4 times within file)
        assertFalse(LoginHandler.isValidLogin("wrong", "pass123"));
        //CHECKSTYLE:ON
        assertFalse(LoginHandler.isValidLogin("", "pass123"));
    }

    /**
     * Tests the isValidLogin method with the incorrect password.
     */
    public void testLoginWithInvalidPassword() {
    	assertFalse(LoginHandler.isValidLogin("admin", "wrong"));
    	assertFalse(LoginHandler.isValidLogin("admin", ""));
    }

    /**
     * Tests the isValidLogin method with the incorrect username and password.
     */
    public void testLoginWithInvalidInfo() {
    	assertFalse(LoginHandler.isValidLogin("wrong", "wrong"));
    	assertFalse(LoginHandler.isValidLogin("", ""));
    }

    /**
     * Tests the isValidLogin method with null username and password.
     *
     * *fails because this is not checked within the method, instead this
     *  case is checked in the method that calls this method.*
     */
    public void testLoginWithNullInfo() {
    	assertFalse(LoginHandler.isValidLogin(null, null));
    }

}

