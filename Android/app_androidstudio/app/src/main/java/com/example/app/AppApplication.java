package com.example.app;
import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;


public class AppApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		Parse.initialize(this, "YIVDzaJoFQvyNUY8OA6LlM6hniqOrjYvBJA9PaTD", "8NHMSqmbfZGOPSkzovW05PcJ4Rxkhfhj9FdJTy3g");
		ParseObject.registerSubclass(UserAccount.class);
        ParseObject.registerSubclass(Transaction.class);
    }
	
}
