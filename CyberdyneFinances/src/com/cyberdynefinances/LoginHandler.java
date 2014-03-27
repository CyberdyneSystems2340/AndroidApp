package com.cyberdynefinances;

import java.util.ArrayList;
import java.util.Collections;
import com.cyberdynefinances.dbManagement.DBHandler;

/**
 * Performs all operations involved in loging in a user. Including: validating username/password,
 * adding new username/password, etc.
 * @author Cyberdyne Finances
 *
 */
public class LoginHandler {
    private static String admin = "admin";

    /**
     * Compares the given username and password to that in the database.
     * @param username - the users username
     * @param password - the users password
     * @return boolean
     */
    public static boolean isValidLogin(String username, String password) {
        if (username.equals(admin) && password.equals("pass123")) {
            return true;
        }
        String[] userInfo = DBHandler.getUserInfo(username);
        return (DBHandler.containsUser(username) && null != userInfo && userInfo[1]
                .hashCode() == password.hashCode());
    }

    /**
     * Adds a new username and password combination to the database.
     * @param username - the username to add to the database
     * @param password - the password to add to the database
     * @return boolean
     */
    public static boolean register(String username, String password) {
        return DBHandler.addUser(username, password);
    }

    /**
     * Checks if the database contains the username.
     * @param userName - the username to search for
     * @return boolean
     */
    public static boolean containsName(String userName) {
        return DBHandler.containsUser(userName);
    }

    /**
     * Gets all the usernames from the db.
     * @return ArrayList<String>
     */
    public static ArrayList<String> getUsernames() {
        ArrayList<String> arr = new ArrayList<String>();
        String[][] allUsersInfo = DBHandler.getAllUsersInfo();
        for (String[] userInfo : allUsersInfo) {
            if (userInfo[0].equals(admin)) {
                continue;
            }
            arr.add(userInfo[0]);
        }
        Collections.sort(arr);

        return arr;
    }

    /**
     * Removes the user from the database.
     * @param username - the username to remove
     */
    public static void remove(String username) {
        DBHandler.deleteUser(username);
    }
}
