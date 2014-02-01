package com.cyberdynefinances;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
	
	public static class LoginFragment extends Fragment
	{
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
        {
        	return inflater.inflate(R.layout.activity_login, container, false);
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
