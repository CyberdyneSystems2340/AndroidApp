package com.cyberdynefinances;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import android.app.Fragment;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

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
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	    {
			View view = inflater.inflate(R.layout.activity_account_homepage, container, false);
			ArrayList<Account> accountList = AccountManager.getAccountList(); //an arraylist of all accounts the current user has

			Spinner reportSpinner = (Spinner) view.findViewById(R.id.report_spinner);
			String[] reports = {"Transaction History", "Spending Category Report", "Income Source Report", "Cash Flow Report"};
			ArrayAdapter a = new ArrayAdapter(view.getContext(), R.layout.layout_report_spinner, reports); 
			reportSpinner.setAdapter(a);
			
			Spinner accountSpinner = (Spinner) view.findViewById(R.id.account_spinner); 
			int len = accountList.size();
			String[] accountSpinnerList = new String[len];
			for(int i = 0; i < len; i++){
				accountSpinnerList[i] = accountList.get(i).getName();
			}
			Arrays.sort(accountSpinnerList);
			ArrayAdapter a2 = new ArrayAdapter(view.getContext(), R.layout.layout_report_spinner, accountSpinnerList); 
			accountSpinner.setOnItemSelectedListener(new OnItemSelectedListener() 
			{
				@Override
				public void onItemSelected(AdapterView<?> arg0, View view1,int arg2, long arg3) 
				{
					TextView textField = (TextView) view1;
					AccountManager.setActiveAccount(textField.getText().toString());
					Double balance = AccountManager.getActiveAccount().getBalance();
					View view = view1.getRootView();
					TextView balanceText = (TextView) view.findViewById(R.id.account_balance);
			    	if(AccountManager.getActiveAccount().getBalance()>=1000000000f) //textField only fits so many digits, scale the textField when that number is exceeded
			    	{
			    		balanceText.setScaleX(0.8f);
			    		balanceText.setScaleY(0.8f);
			    	}
			    	else
			    	{
			    		balanceText.setScaleX(1f);
			    		balanceText.setScaleY(1f);
			    	}
					balanceText.setText(NumberFormat.getCurrencyInstance().format(balance));
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {}
			});
			accountSpinner.setAdapter(a2);
			Double balance = AccountManager.getActiveAccount().getBalance();
			TextView balanceText = (TextView) view.findViewById(R.id.account_balance);
	    	if(AccountManager.getActiveAccount().getBalance()>=1000000000f) //textField only fits so many digits, scale the textField when that number is exceeded
	    	{
	    		balanceText.setScaleX(0.8f);
	    		balanceText.setScaleY(0.8f);
	    	}
	    	else
	    	{
	    		balanceText.setScaleX(1f);
	    		balanceText.setScaleY(1f);
	    	}
			balanceText.setText(NumberFormat.getCurrencyInstance().format(balance));
			
			return view;
	    }
	}
}
