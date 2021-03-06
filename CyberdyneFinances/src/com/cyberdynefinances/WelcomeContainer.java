package com.cyberdynefinances;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @author Cyberdyne Finances This is a container class for the welcome screen
 */
public class WelcomeContainer extends Activity
{
    // Called when this activity is created sets up the fragmentManager and
    // makes the Welcome Fragment the initial fragment to display
    @Override
    protected final void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.frame_welcome);
        if (savedInstanceState == null)
        {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.container_welcome,
                            new Fragments.CoinflipFragment()).commit();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(3100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Animation.fade(new Fragments.WelcomeFragment(), getFragmentManager(),
                                    R.id.container_welcome);
                        }
                    });
                }
            }).start();
        }
        
    }

    @Override
    public final boolean onOptionsItemSelected(final MenuItem item)
    {
        switch (item.getItemId())
        {
            
            case android.R.id.home:
            // Navigate "up" the demo structure to the launchpad activity.
            // See http://developer.android.com/design/patterns/navigation.html
            // for more.
                NavUtils.navigateUpTo(this, 
                        new Intent(this, WelcomeContainer.class));
                return true;
            default:
                break;
            
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Login button on the welcome screen.
     * 
     * @param view
     *            The view relating to the login button
     */
    public final void welcomeLoginClicked(final View view)
    {
        Animation.fade(new Fragments.LoginFragment(), getFragmentManager(),
                R.id.container_welcome);
    }

    /**
     * Register button on the welcome screen.
     * 
     * @param view
     *            The view relating to the register button
     */
    public final void welcomeRegisterClicked(final View view)
    {
        Animation.fade(new Fragments.RegisterFragment(), getFragmentManager(),
                R.id.container_welcome);
    }


    /**
     * The loginClicked method will verify the user and if successful
     * he will be transfered to the new screen.
     * 
     * @param view The view relating to the login button
     */
    public void loginClicked(View view)
    {
        View root = Fragments.LoginFragment.root;
        EditText editText = (EditText) root.findViewById(R.id.usernameEditText);
        String username = editText.getText().toString();
        editText = (EditText) root.findViewById(R.id.passwordEditText);
        String password = editText.getText().toString();
        if (LoginHandler.isValidLogin(username, password))
        {
            // if admin display admin specific activity
            if (username.equals("admin"))
            {
                Intent intent = new Intent(this, AdminActivity.class);
                startActivity(intent);
            }
            // normal user load their accounts and display the normal account
            // viewing activity
            else
            {
                AccountManager.loadUser(username);
                Intent intent = new Intent(this, AccountContainer.class);
                startActivity(intent);
            }
        } else
        {
            Toast.makeText(this, "Incorrect username or password",
                    Toast.LENGTH_LONG).show();
        }
    }
    
    /**
     * The registerClicked method will verify the information. 
     * It will also check if all of the credentials are 
     * valid for the database.
     * 
     * @param view The view relating to the register button
     */
    public void registerClicked(View view)
    {
        View root2 = Fragments.RegisterFragment.root;
        EditText editText = (EditText) root2
                .findViewById(R.id.registerUsername);
        String newName = editText.getText().toString();
        editText = (EditText) root2.findViewById(R.id.registerPassword);
        String newPassword = editText.getText().toString();
        editText = (EditText) root2
                .findViewById(R.id.registerPasswordVerification);
        String verifyPassword = editText.getText().toString();

        // Checks for blank username
        if (newName.length() == 0)
        {
            Toast.makeText(this, "Invalid username", Toast.LENGTH_LONG).show();
        }
        // Check if the name exists or not
        else if (LoginHandler.containsName(newName))
        {
            Toast.makeText(this, "Username already exists", Toast.LENGTH_LONG)
                    .show();
        }
        // Verify the password
        else if (!(newPassword.equals(verifyPassword)))
        {
            Toast.makeText(this, "Retype Password", Toast.LENGTH_LONG).show();
        }
        // Then you only need to check the first password and see if it matches
        // the given properties
        // Then return back to the welcome screen
        else if ((newPassword.length() < 6)
                || !(newPassword.matches(".*\\d.*")))
        {
            Toast.makeText(this,
                    "Password must contain 6 or more characters and a number",
                    Toast.LENGTH_LONG).show();
        } else
        {
            if (LoginHandler.register(newName, newPassword))
            {
                SoundPool sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
                int iTmp = sp.load(WelcomeContainer.this, R.raw.coinflip, 1);
                sp.play(iTmp, 1, 1, 0, 0, 1);
                MediaPlayer mPlayer = MediaPlayer.create(WelcomeContainer.this,
                        R.raw.coinflip);

                Toast.makeText(this, "Registration Successful",
                        Toast.LENGTH_LONG).show(); // Yay! you are a user
                                                  // now!!!!!
                Animation.fade(new Fragments.LoginFragment(),
                        getFragmentManager(), R.id.container_welcome, true);
                mPlayer.start();
            } else
            {
                Toast.makeText(this, "Registration failed, please try again.",
                        Toast.LENGTH_LONG).show(); // Oops, something broke.
            }
        }
    }
}
