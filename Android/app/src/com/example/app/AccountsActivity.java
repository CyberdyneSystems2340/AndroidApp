package com.example.app;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseQuery.CachePolicy;
import com.parse.ParseUser;

public class AccountsActivity extends Activity implements OnItemClickListener {
	private TextView _nameView;
	private ListView _accountsView;
	private UserAccountAdapter _adapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);
        
        ParseUser user = ParseUser.getCurrentUser();
        if (user == null || ParseUser.getCurrentUser().getUsername() == null) {
        	Intent intent = new Intent(this, LoginActivity.class);
        	startActivity(intent);
        	finish();
        }
        
        Log.v(AccountsActivity.class.getName(), ParseUser.getCurrentUser().getUsername());
        
        _adapter = new UserAccountAdapter(this, new ArrayList<UserAccount>());
        
        _nameView = (TextView) findViewById(R.id.username_field);
        _accountsView = (ListView) findViewById(R.id.accounts_list);

        _nameView.setText(ParseUser.getCurrentUser().getUsername());
        _accountsView.setAdapter(_adapter);
        _accountsView.setOnItemClickListener(this);
        
        updateData();
    }
    
    public void updateData() {
    	ParseQuery<UserAccount> query = ParseQuery.getQuery(UserAccount.class);
    	query.whereEqualTo("user", ParseUser.getCurrentUser());
    	query.setCachePolicy(CachePolicy.CACHE_THEN_NETWORK);
    	query.findInBackground(new FindCallback<UserAccount>() {
    		@Override
    		public void done(List<UserAccount> uacs, ParseException e) {
    			if (uacs != null) {
    				_adapter.clear();
    				for (int i = 0; i < uacs.size(); i++) {
    					_adapter.add(uacs.get(i));	
    				}
    			}
    			else Log.i(this.getClass().getName(), "UACs WAS NULL");
    		}
    	});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.accountsmenu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.addaccount:
				startActivity(new Intent(this, AddAccountDialog.class));
				updateData();
				return true;
			case R.id.refresh:
				updateData();
				return true;
			case R.id.logOutMenuItem:
				ParseUser.logOut();
				startActivity(new Intent(this, LoginActivity.class));
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
    }


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
	}
    
}
