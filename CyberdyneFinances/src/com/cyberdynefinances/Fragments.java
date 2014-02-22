package com.cyberdynefinances;

import java.util.ArrayList;

import android.app.Fragment;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

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
        	return root;
        }
        
        //clears the text fields when the fragment is paused
        @Override
        public void onPause()
        {
        	super.onPause();
    		((EditText) root.findViewById(R.id.usernameEditText)).setText("");
    		((EditText) root.findViewById(R.id.passwordEditText)).setText("");
        }
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
        
        //clears the text fields when the fragment is paused
        @Override
        public void onPause()
        {
        	super.onPause();
<<<<<<< HEAD
        	((EditText) root.findViewById(R.id.registerUsername)).setText("");
    		((EditText) root.findViewById(R.id.registerPassword)).setText("");
    		((EditText) root.findViewById(R.id.registerPasswordVerification)).setText("");
    		((EditText) root.findViewById(R.id.registerBalance)).setText("");
    		((EditText) root.findViewById(R.id.registerInterest)).setText("");
=======
        	((EditText) root2.findViewById(R.id.registerUsername)).setText("");
    		((EditText) root2.findViewById(R.id.registerPassword)).setText("");
    		((EditText) root2.findViewById(R.id.registerPasswordVerification)).setText("");
>>>>>>> 1af1e5ab7624448c7c50154a32f5c40ee28e5474
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
	
	//Fragment for coin flip loading animation
	public static class CoinflipFragment extends Fragment 
	{
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
        {
        	View root = inflater.inflate(R.layout.activity_coinflip, container, false);
        	ImageView coinImage = (ImageView) root.findViewById(R.id.coinflip);
        	coinImage.setBackgroundResource(R.anim.coinflip);

        	AnimationDrawable coinAnimation = (AnimationDrawable) coinImage.getBackground();
        	coinAnimation.start();
        	return root;
        }
    }
	
	//test account fragment to display account info
	public static class AccountFragment extends Fragment
	{
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	    {
			View root = inflater.inflate(R.layout.activity_account, container, false);

        	((TextView) root.findViewById(R.id.account_username)).setText("Username: "+AccountManager.getActiveUser());
    		((TextView) root.findViewById(R.id.account_name)).setText(AccountManager.getActiveAccount().getAccountInfo());
    		
			return root;
	    }
	}
	
	public static class AccountCreationFragment extends Fragment
	{
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	    {
			View root = inflater.inflate(R.layout.activity_account_creation, container, false);
			return root;
	    }
	}
	
	public static class AccountHomeFragment extends Fragment
	{
		//TODO: change the welcomeRegisterClicked method in WelcomeContainer back to Fragments.RegisterFragment()
		//Change TestFragment back to R.layout.activity_test
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	    {
			View view = inflater.inflate(R.layout.activity_account_homepage, container, false);
			
			//things you may find useful
			String activeUser = AccountManager.getActiveUser(); //the name of the current user
			Double balance = AccountManager.getActiveAccount().balance; //the balance of the current account
			ArrayList<Account> accountList = AccountManager.getAccountList(); //an arraylist of all accounts the current user has
			String accountInfo = AccountManager.getActiveAccount().getAccountInfo();//basic info about the account like name, owner, balance, interest, transaction history(which is blank) separated by newlines
			
			//the findViewById method lets you get objects from the layout like textFields, buttons, spinners you just need to give it the id of what you want and cast it to that object
			Spinner s = (Spinner) view.findViewById(R.id.report_spinner); //parameter is the id of the spinner in this case it is report_spinner
			String[] test = {"test"};
			//all spinners have adapters, they tell the spinner things like what to show and how it should look
			//the second parameter is a layout file for the spinner for things like text size, the one it is using is a custom one i wrote for the admin account spinner. you can take a look at it and write your own for each spinner 
			//the last parameter is an array of strings containing things the spinner should have as options
			ArrayAdapter a = new ArrayAdapter(view.getContext(), R.layout.layout_spinner, test); 
			s.setAdapter(a);
			
			return view;
	    }
	}
}
