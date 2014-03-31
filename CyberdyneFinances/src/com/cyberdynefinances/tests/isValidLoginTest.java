package com.cyberdynefinances.tests;

import com.cyberdynefinances.LoginHandler;
import com.cyberdynefinances.WelcomeContainer;
import com.cyberdynefinances.dbManagement.DBHandler;
import android.test.ActivityInstrumentationTestCase2;

public class isValidLoginTest extends ActivityInstrumentationTestCase2<WelcomeContainer> {
    
    public isValidLoginTest() {
        super("com.cyberdynefinances.WelcomContainer", WelcomeContainer.class);
    }
    
    public void testLogin() {
        assertTrue(LoginHandler.isValidLogin("admin", "pass123"));
    }
    
    public void testLoginWithInvalidUsername() {
        assertFalse(LoginHandler.isValidLogin("wrong", "pass123"));
        assertFalse(LoginHandler.isValidLogin("", "pass123"));
    }
    
    public void testLoginWithInvalidPassword() {
    	assertFalse(LoginHandler.isValidLogin("admin", "wrong"));
    	assertFalse(LoginHandler.isValidLogin("admin", ""));
    }
    
    public void testLoginWithInvalidInfo() {
    	assertFalse(LoginHandler.isValidLogin("wrong", "wrong"));
    	assertFalse(LoginHandler.isValidLogin("", ""));
    }
    
    public void testLoginWithNullInfo() {
    	assertFalse(LoginHandler.isValidLogin(null, null));
    }
    
}

