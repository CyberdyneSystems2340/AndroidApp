package com.cyberdynefinances;

import android.app.Fragment;
import android.app.FragmentManager;

/**
 * @author Cyberdyne Finances
 * Contains methods to have transition animations between fragments
 */
public class Animation 
{	
	/**
	 * Does a flip animation transition between fragments.
	 * @param frag The fragment to transition to
	 * @param manager The fragment manager
	 * @param container The id of the container the fragment is in
	 */
    public static void flip(Fragment frag, FragmentManager manager, int container) 
    {
        manager.beginTransaction()
        		.setCustomAnimations(R.anim.card_flip_right_in, R.anim.card_flip_right_out, R.anim.card_flip_left_in, R.anim.card_flip_left_out)
        		.replace(container, frag)
        		.addToBackStack(null)
                .commit();
    }
    
	/**
     * Does a slide animation transition between fragments.
     * @param frag The fragment to transition to
     * @param manager The fragment manager
     * @param container The id of the container the fragment is in
     */
    public static void slide(Fragment frag, FragmentManager manager, int container) 
    {
        manager.beginTransaction()
        		.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_right_out, R.anim.slide_right_in, R.anim.slide_left_out)
        		.replace(container, frag)
        		.addToBackStack(null)
                .commit();
    }
	
	/**
     * Does a fade animation transition between fragments.
     * @param frag The fragment to transition to
     * @param manager The fragment manager
     * @param container The id of the container the fragment is in
     */
    public static void fade(Fragment frag, FragmentManager manager, int container) 
    {
        manager.beginTransaction()
        		.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
        		.replace(container, frag)
        		.addToBackStack(null)
                .commit();
    }
	
	//Fade animation but wont add the fragment to the fragment stack so you cant go back to this fragment once you leave it. In order for it to work properly call it when you are leaving the fragment you dont want to go back to
	/**
     * Does a fade animation transition between fragments, but doesn't add the fragment to the back stack of the fragment manager. This prevents one from going back to the fragment.
     * @param frag The fragment to transition to
     * @param manager The fragment manager
     * @param container The id of the container the fragment is in
     * @param b 
     */
    public static void fade(Fragment frag, FragmentManager manager, int container, boolean b) 
    {
        manager.beginTransaction()
        		.disallowAddToBackStack()
        		.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
        		.replace(container, frag)
                .commit();
    }
}
