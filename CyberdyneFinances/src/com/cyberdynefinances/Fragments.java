package com.cyberdynefinances;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Fragments 
{
	//First fragment that gets shown upon app startup
	public static class WelcomeFragment extends Fragment 
	{
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
        {
            return inflater.inflate(R.layout.activity_welcome, container, false);
        }
    }
	
	//Fragment to show the login view
	public static class LoginFragment extends Fragment
	{
		public static View root;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
        {
        	//Actual view relating to the fragment needed to gain access to elements of the login screen like text fields
        	root = inflater.inflate(R.layout.activity_login, container, false);
        	//Button loginButton = (Button) root.findViewById(R.id.loginButton);
        	//loginButton.setOnClickListener(this);
        	return root;
        }

        //Moved to WelcomeContainer using normal button click method
        //A button listener click method has to be set in this class rather than the container in order to gain access to elements of the login screen like text fields
        /*
		@Override
		public void onClick(View view) 
		{
			EditText editText = (EditText) root.findViewById(R.id.usernameEditText);
	    	String username = editText.getText().toString();
	    	editText = (EditText) root.findViewById(R.id.passwordEditText);
	    	String password = editText.getText().toString();
	    	if(LoginHandler.isValidLogin(username, password))
	    	{
	    		Animation.fade(new Fragments.TestFragment(), getFragmentManager(), R.id.container_welcome);
	    	}
	    	else
	    	{
	    		Toast.makeText(getActivity(), "Incorrect username or password", Toast.LENGTH_LONG).show();
	    	}
		}
        */
    }
	
	//Fragment for the register screen
	public static class RegisterFragment extends Fragment 
	{
		public static View root;
		
        @Override
        //This method sets the current view to that of the registration activity for the user.
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
        {
            root = inflater.inflate(R.layout.activity_register, container, false);
            return root;
        }
    }
	
	//Fragment for testing purposes
	public static class TestFragment extends Fragment 
	{
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
        {
        	return inflater.inflate(R.layout.activity_test, container, false);
        }
    }
}
