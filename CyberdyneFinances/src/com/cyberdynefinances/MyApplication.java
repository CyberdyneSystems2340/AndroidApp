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
//TODO: Add description to javadoc for class
/**
 * 
 * @author Cyberdyne Finances
 */
public class MyApplication extends Application {
    //CHECKSTYLE:OFF    suppress error of Missing Javadoc comment
    private static Context context;
    //CHECKSTYLE: ON

    /**
     * This method is used on creation of the app.
     * It sets the context of this class. 
     */
    public void onCreate() {
        super.onCreate();
        MyApplication.context = getApplicationContext();
    }

    /**
     * This method returns the context of the application.
     *  
     * @return Context object of the application.
     */
    public static Context getAppContext() {
        return MyApplication.context;
    }
}