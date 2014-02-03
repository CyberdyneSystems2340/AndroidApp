package com.cyberdynefinances;

import java.util.Hashtable;

public class LoginHandler 
{
	@SuppressWarnings("serial")
	private static Hashtable<String, Integer> table = new Hashtable<String, Integer>() {{ put("admin", ("pass123".hashCode()));}};
	
	public static boolean isValidLogin(String username, String password)
	{
		if(table.get(username)==null)
			return false;
		if(table.get(username)==password.hashCode())
			return true;
		return false;
	}
	
	private static void readTable()
	{
		
	}
	
	private static void writeTable()
	{
		
	}
	
}
