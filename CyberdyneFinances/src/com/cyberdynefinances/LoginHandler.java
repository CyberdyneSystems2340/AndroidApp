package com.cyberdynefinances;

import java.util.Hashtable;
import java.util.Map.Entry;
import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

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
	
	public static void register(String username, String password)
	{
		table.put(username, password.hashCode());
	}
}
