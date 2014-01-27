package com.cyberdynefinances;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class Fragments 
{

	public static class CardWelcomeFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            return inflater.inflate(R.layout.activity_main, container, false);
        }
    }
	
	public static class CardLoginFragment extends Fragment implements OnClickListener{
		View root;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        	View view = inflater.inflate(R.layout.activity_test, container, false);
     	    Button button = (Button) view.findViewById(R.id.login_screen_login_button);
     	    button.setOnClickListener(this);
     	    root = view;
            return view;
        }

		@Override
		public void onClick(View view) 
		{
			
			Intent intent = new Intent(this.getActivity(), SuccessActivity.class);
			EditText editText = (EditText) root.findViewById(R.id.edit_message);
			String message = editText.getText().toString();
			intent.putExtra("M", message);
			startActivity(intent);
			
			//Animation.flipCard(new Fragments.CardSuccessFragment(), getFragmentManager(), R.id.containerFrame);
		}
    }
	
	public static class CardRegisterFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            return inflater.inflate(R.layout.activity_register, container, false);
        }
    }
	
	public static class CardSuccessFragment extends Fragment {
		ViewGroup root;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
        	return inflater.inflate(R.layout.activity_success, container, false);
        }
    }
	
	public static class GenericFragment extends Fragment {
		ViewGroup root;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
        	return inflater.inflate(R.layout.activity_generic, container, false);
        }
    }
	
}
