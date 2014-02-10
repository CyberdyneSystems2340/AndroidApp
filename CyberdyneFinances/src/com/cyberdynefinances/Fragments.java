package com.cyberdynefinances;

import android.app.Fragment;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
}
