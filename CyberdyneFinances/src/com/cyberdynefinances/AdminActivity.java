package com.cyberdynefinances;

import java.util.ArrayList;

import com.cyberdynefinances.dbManagement.DBHandler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * A class that handles all actions performed by the admin account.
 * 
 * @author Cyberdyne Finances
 *
 */
public class AdminActivity extends Activity {
    private static String yesText = "Yes";
    private static String noText = "No";

    @Override
    /**
     * Updates the View according to the saved instance state
     * @param Bundle savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_admin);
        updateView();
    }

    @Override
    /**
     * responds to the item selected by the user
     * @param MenuItem item
     * @return boolean goHome
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
            // Navigate "up" the demo structure to the launchpad activity.
            // See http://developer.android.com/design/patterns/navigation.html
            // for more.
                NavUtils.navigateUpTo(this, new Intent(this, WelcomeContainer.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    /**
     * updates the spinner of usernames
     */
    private void updateView() {
        ArrayList<String> names = LoginHandler.getUsernames();
        Spinner s = (Spinner) this.findViewById(R.id.spinner);
        String[] spinnerArray = new String[names.size()];
        int i = 0;
        for (String name : names) {
            spinnerArray[i++] = name;
        }
        ArrayAdapter a = new ArrayAdapter(this, R.layout.layout_spinner,
                spinnerArray);
        s.setAdapter(a);
    }

     /**
     * deletes the selected account.
     * @param view
     */
    public void delete(View view) {
        final View v = (View) view.getParent();
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Delete User")
                .setMessage("Are you sure you want to delete this user?")
                .setPositiveButton(yesText,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                    int which) {
                                Spinner s = (Spinner) v
                                        .findViewById(R.id.spinner);
                                String name = s.getSelectedItem().toString();
                                LoginHandler.remove(name);
                                AdminActivity.this.updateView();
                            }
                        }).setNegativeButton(noText, null).show();
    }

     /**
     * resets the password of the selected account.
     * @param view
     */
    public void reset(View view) {
        final View v = (View) view.getParent();
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Reset Password")
                .setMessage(
                        "Are you sure you want to reset this user's password?")
                .setPositiveButton(yesText,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                    int which) {
                                Spinner s = (Spinner) v
                                        .findViewById(R.id.spinner);
                                String name = s.getSelectedItem().toString();
                                DBHandler.changePassword(name, "pass123");
                                AdminActivity.this.updateView();
                            }
                        }).setNegativeButton(noText, null).show();
    }
}
