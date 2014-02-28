/**
 * This method is needed by the DBHandler and DBHelper classes.
 * It allows these classes to know which database to grab based on the context.
 * DO NOT DELETE!!!
 * 
 * @author Robert
 * @version 1.0
 */
package com.cyberdynefinances;
import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {
	private static Context context;
	
	public void onCreate() {
		super.onCreate();
		MyApplication.context = getApplicationContext();
	}
	
	public static Context getAppContext() {
		return MyApplication.context;
	}
}