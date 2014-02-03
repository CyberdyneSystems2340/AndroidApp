package com.cyberdynefinances;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class Fragments 
{
	
	public static class WelcomeFragment extends Fragment 
	{
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
        {
            return inflater.inflate(R.layout.activity_welcome, container, false);
        }
    }
	
	public static class LoginFragment extends Fragment implements OnClickListener
	{
		View root;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
        {
        	root = inflater.inflate(R.layout.activity_login, container, false);
        	Button loginButton = (Button) root.findViewById(R.id.loginButton);
        	loginButton.setOnClickListener(this);
        	return root;
        }

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
	    		//show login error
	    	}
		}
        
    }
	
	public static class RegisterFragment extends Fragment 
	{
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
        {
            return inflater.inflate(R.layout.activity_test, container, false);
        }
    }
	
	public static class TestFragment extends Fragment 
	{
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
        {
        	return inflater.inflate(R.layout.activity_test, container, false);
        }
    }
	/*
	public static class GenericFragment extends Fragment 
	{
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
        {
        	return inflater.inflate(R.layout.activity_generic, container, false);
        }
    }
    */
}
