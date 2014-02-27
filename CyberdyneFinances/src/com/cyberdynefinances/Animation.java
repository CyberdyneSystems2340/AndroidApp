package com.cyberdynefinances;

import android.app.Fragment;
import android.app.FragmentManager;

public class Animation 
{	
	//Flip animation
	public static void flip(Fragment frag, FragmentManager manager, int container) 
	{
        manager.beginTransaction()
        		.setCustomAnimations(R.anim.card_flip_right_in, R.anim.card_flip_right_out,R.anim.card_flip_left_in, R.anim.card_flip_left_out)
        		.replace(container, frag)
        		.addToBackStack(null)
                .commit();
    }
    
	//Slide animation
	public static void slide(Fragment frag, FragmentManager manager, int container) 
	{
        manager.beginTransaction()
        		.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_right_out,R.anim.slide_right_in, R.anim.slide_left_out)
        		.replace(container, frag)
        		.addToBackStack(null)
                .commit();
    }
	
	//Fade animation
	public static void fade(Fragment frag, FragmentManager manager, int container) 
	{
        manager.beginTransaction()
        		.setCustomAnimations(R.anim.fade_in, R.anim.fade_out,R.anim.fade_in, R.anim.fade_out)
        		.replace(container, frag)
        		.addToBackStack(null)
                .commit();
    }
	
	//Fade animation but wont add the fragment to the fragment stack so you cant go back to this fragment once you leave it. In order for it to work properly call it when you are leaving the fragment you dont want to go back to
	public static void fade(Fragment frag, FragmentManager manager, int container, boolean b) 
	{
        manager.beginTransaction()
        		.disallowAddToBackStack()
        		.setCustomAnimations(R.anim.fade_in, R.anim.fade_out,R.anim.fade_in, R.anim.fade_out)
        		.replace(container, frag)
                .commit();
    }
}
