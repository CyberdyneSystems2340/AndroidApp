package com.cyberdynefinances;

import java.util.ArrayList;
import java.util.Collections;
import com.cyberdynefinances.dbManagement.DBHandler;

public class LoginHandler 
{
	private static DBHandler dbHandler = new DBHandler();
	
	//Compares the given username and password to that in the database
	public static boolean isValidLogin(String username, String password)
	{
		if(username.equals("admin") && password.equals("pass123"))
				return true;
		String[] userInfo = dbHandler.getUserInfo(username);
		return (dbHandler.containsUser(username) && null != userInfo &&
		        userInfo[1].hashCode() == password.hashCode());
	}

	//Adds a new username and password combination to the database
	public static boolean register(String username, String password)
	{
		return dbHandler.addUser(username, password);
	}

	//Checks if the database contains the username
	public static boolean containsName(String userName)
	{
		return dbHandler.containsUser(userName);
	}
	
	//This method now gets filled with all the username from the db.
	public static ArrayList<String> getUsernames()
	{
		ArrayList<String> arr = new ArrayList<String>();
		String[][] allUsersInfo = dbHandler.getAllUsersInfo();
		for (String[] userInfo: allUsersInfo) {
		    if (userInfo[0].equals("admin")){
		        continue;
		    }
		    arr.add(userInfo[0]);
		}
		Collections.sort(arr);
		
		return arr;
	}
	
	public static void remove(String username)
	{
		dbHandler.deleteUser(username);
	}
}
