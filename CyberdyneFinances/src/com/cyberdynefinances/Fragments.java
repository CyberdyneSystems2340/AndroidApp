package com.cyberdynefinances;

import android.app.Fragment;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
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
		public static View root2; // Copied Al >:D
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
        {
        	root2 = inflater.inflate(R.layout.activity_register, container, false);
        	return root2;
        }
        
        //clears the text fields when the fragment is paused
        @Override
        public void onPause()
        {
        	super.onPause();
        	((EditText) root2.findViewById(R.id.registerUsername)).setText("");
    		((EditText) root2.findViewById(R.id.registerPassword)).setText("");
    		((EditText) root2.findViewById(R.id.registerPasswordVerification)).setText("");
    		((EditText) root2.findViewById(R.id.registerBalance)).setText("");
    		((EditText) root2.findViewById(R.id.registerInterest)).setText("");
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
}
