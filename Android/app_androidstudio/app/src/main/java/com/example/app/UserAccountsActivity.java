package com.example.app;

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

import java.util.ArrayList;
import java.util.List;

public class UserAccountsActivity extends Activity implements OnItemClickListener {
	private TextView _nameView;
	private ListView _accountsView;
	private UserAccountAdapter _adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);

        ParseUser user = ParseUser.getCurrentUser();
        if (user == null || ParseUser.getCurrentUser().getUsername() == null) {
            Log.v(UserAccountsActivity.class.getName(), "USER WAS NULL!");
        	Intent intent = new Intent(this, LoginActivity.class);
        	startActivity(intent);
        	finish();
        }

        _adapter = new UserAccountAdapter(this, new ArrayList<UserAccount>());

        _nameView = (TextView) findViewById(R.id.username_field);
        _accountsView = (ListView) findViewById(R.id.accounts_list);

        if (user != null) _nameView.setText(user.getUsername());
        _accountsView.setAdapter(_adapter);
        _accountsView.setOnItemClickListener(this);

        updateData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateData();
    }

    public void updateData() {
    	ParseQuery<UserAccount> query = ParseQuery.getQuery(UserAccount.class);
    	query.whereEqualTo("user", ParseUser.getCurrentUser());
    	query.setCachePolicy(CachePolicy.CACHE_THEN_NETWORK);
    	query.findInBackground(new FindCallback<UserAccount>() {
    		@Override
    		public void done(List<UserAccount> uacs, ParseException e) {
                if (e != null) {
                    Log.e(getClass().getName(), "error code: " + e.getCode());
                    return;
                }

                // TODO fix ordering of UserAccounts in _accountsView after updating
                if (uacs != null) {
                    _adapter.clear();
                    for (int i = 0; i < uacs.size(); i++) {
                        _adapter.add(uacs.get(i));
                    }
    			}
    			else Log.i(this.getClass().getName(), "UACs WAS NULL");
    		}
    	});

        ParseSingleton.getInstance().put("accountsList", _adapter.getList());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data.getExtras() != null) {
            UserAccount uac = (UserAccount)ParseSingleton.getInstance().get("newAccount");
            _adapter.add(uac);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.accountsmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.addaccount:
				startActivityForResult(new Intent(this, AddAccountDialog.class), 1);
				updateData();
				return true;
            case R.id.addtransaction:
                Log.e(getClass().getName(), "Add Transaction not implemented yet!");
                return true;
			case R.id.refresh:
				updateData();
				return true;
            case R.id.manageAccountMenuItem:
                Log.e(getClass().getName(), "Manage Accounts not implemented yet!");
                return true;
			case R.id.logOutMenuItem:
				ParseUser.logOut();
				startActivity(new Intent(this, LoginActivity.class));
				return true;
            case R.id.recordAccountItem:
                startActivity(new Intent(this, AccountRecordActivity.class));
                return true;
			default:
				return super.onOptionsItemSelected(item);
		}
    }


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        UserAccount uac = _adapter.getItem(position);
		Intent intent = new Intent(this, AccountViewActivity.class);
        intent.putExtra("accountID", position);
        startActivity(intent);
	}

}
