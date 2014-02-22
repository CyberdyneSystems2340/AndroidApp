package com.cyberdynefinances;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map.Entry;

import com.cyberdynefinances.dbManagement.DBHandler;

import android.content.SharedPreferences;

public class LoginHandler 
{
	@SuppressWarnings("serial")
	//Hashtable to store username and password combinations. Stores the password as its hash for security
	private static Hashtable<String, Integer> table = new Hashtable<String, Integer>() {{ put("admin", ("pass123".hashCode()));}};
	private static DBHandler dbHandler = new DBHandler();
	
	//Compares the given username and password to that in the database
	public static boolean isValidLogin(String username, String password)
	{
        String[] userInfo = dbHandler.getUserInfo(username);
		return (dbHandler.containsUser(username) && null != userInfo &&
		        userInfo[1].hashCode() == password.hashCode());
	}
	
	//Reads in data from the SharedPreferences file to populate the hashtable
	public static void readTable(SharedPreferences settings)
	{
		int count=0;
		while(true)
		{
			String username = settings.getString("username"+count, "");
			if(username.equals(""))
				break;
			int password = settings.getInt("password"+count, 0);
			table.put(username, password);
			count++;
		}
	}
	
	//Writes out data in the hashtable to the SharedPreferences file
	public static void writeTable(SharedPreferences settings)
	{
	    SharedPreferences.Editor editor = settings.edit();
	    int count = 0;
	    for(Entry<String, Integer> s : table.entrySet())
	    {
	    	editor.putString("username"+count, s.getKey());
		    editor.putInt("password"+count, s.getValue());
		    count++;
	    }
	    editor.commit();
	}
	
	//Adds a new username and password combination to the hashtable
	public static void register(String username, String password)
	{
		dbHandler.addUser(username, password);
	}
	
	//Checks if the userID is present in the database.
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
		table.remove(username);
	}
}
