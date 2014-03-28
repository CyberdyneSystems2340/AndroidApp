package com.cyberdynefinances;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;

import com.cyberdynefinances.dbManagement.DBHandler;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

import android.app.Fragment;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
        	((EditText) root.findViewById(R.id.registerUsername)).setText("");
    		((EditText) root.findViewById(R.id.registerPassword)).setText("");
    		((EditText) root.findViewById(R.id.registerPasswordVerification)).setText("");
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
	
	
	public static class GraphLayoutFragment extends Fragment
	{
		public static View root;
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	    {
			String[][] transactions = DBHandler.getTransactionHistory(AccountManager.getActiveAccount().getName());
			root = inflater.inflate(R.layout.graph_layout, container, false);
			String[] moneys = new String[transactions.length];
			for(int i = 0; i < moneys.length; i++)
			{
				moneys[i] = transactions[i][1]; //Getting a deep copy of the array
			}
	
			double sum = 0;
			for(String i : moneys)
			{
				sum += Double.parseDouble(i);
			}
			
			GraphView.GraphViewData[] data = new GraphView.GraphViewData[moneys.length];
			String[] horzArr = new String[moneys.length];
			
			//data[0] = new GraphView.GraphViewData(0, 4000);//AccountManager.getActiveAccount().getBalance() - sum);
					
			for(int i = 0; i < data.length; i++)
			{
				//data[i] = new GraphView.GraphViewData(i, Math.sin(i));
				data[i] = new GraphView.GraphViewData(i, Double.parseDouble(moneys[i]));
				horzArr[i] = i + 1 + "";
			}
			
			GraphViewSeries exampleSeries = new GraphViewSeries(data);
			 
			GraphView graphView = new LineGraphView(
			      root.getContext() // context
			      , "Transaction History" // heading
			);
			
			graphView.setHorizontalLabels(horzArr);
			graphView.setVerticalLabels(new String[] {"Top", sum/2 + "", "Bot"});
			
			graphView.addSeries(exampleSeries); // data
			LinearLayout layout = (LinearLayout)root.findViewById(R.id.graph1); 
			layout.addView(graphView); 
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
					if(textField==null)
						return;
					AccountManager.setActiveAccount(textField.getText().toString());
					Double balance = AccountManager.getActiveAccount().getBalance();
					View view = view1.getRootView();
					TextView balanceText = (TextView) view.findViewById(R.id.account_balance);
					TextView reportText = (TextView) view.findViewById(R.id.report_text_view);
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
			    	String[][] transactions = DBHandler.getTransactionHistory(textField.getText().toString());
			    	//Log.e("tag",textField.getText().toString());
			        String rows = "";
			        if (null != transactions) {
			            for (String[] transaction : transactions) {
			                rows += "\n\nAccount: " + transaction[0] + ", Amount: " + transaction[1] +
			                        ", Type: " + transaction[2] + ", Category: " + transaction[3] +
			                        ", Timestamp: " + transaction[4];
			            }
			        }
			        //Log.e("tag",rows);
			        reportText.setText(rows);
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
			
			Spinner monthBegin = (Spinner) view.findViewById(R.id.date_month_begin);
			Spinner dayBegin = (Spinner) view.findViewById(R.id.date_day_begin);
			Spinner yearBegin = (Spinner) view.findViewById(R.id.date_year_begin);
			Spinner monthEnd = (Spinner) view.findViewById(R.id.date_month_end);
            Spinner dayEnd = (Spinner) view.findViewById(R.id.date_day_end);
            Spinner yearEnd = (Spinner) view.findViewById(R.id.date_year_end);
			String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
			String[] days = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
			String[] years = {"2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014"};
			ArrayAdapter month = new ArrayAdapter(view.getContext(), R.layout.layout_date_spinner, months); 
			ArrayAdapter day = new ArrayAdapter(view.getContext(), R.layout.layout_date_spinner, days); 
			ArrayAdapter year = new ArrayAdapter(view.getContext(), R.layout.layout_date_spinner, years);
			monthBegin.setAdapter(month);
			dayBegin.setAdapter(day);
			yearBegin.setAdapter(year);
			monthEnd.setAdapter(month);
            dayEnd.setAdapter(day);
            yearEnd.setAdapter(year);
			
			return view;
	    }
	}
}
