package com.example.mdc.news.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.mdc.news.activities.HomeActivity;
import com.example.mdc.news.fragments.Newsmain;
import com.example.mdc.news.fragments.Play;
import com.example.mdc.news.fragments.Profile;

/**
 * Created by android602 on 18/5/17.
 */

public class HomePagerAdapter extends FragmentStatePagerAdapter {

    private int noOfTabs;
    private HomeActivity activity;

    private Play playFragment;
    private Newsmain newsmainFragment;
    private Profile profileFragment;

    public HomePagerAdapter(FragmentManager fm, int noOfTabs, HomeActivity activity) {
        super(fm);
        this.noOfTabs = noOfTabs;
        this.activity = activity;

    }

    @Override
    public Fragment getItem(int position) {


        switch (position) {
            case 0:
                playFragment = new Play();
                return playFragment;

            case 1:
                newsmainFragment = new Newsmain();
                return newsmainFragment;

            case 2:
                profileFragment = new Profile();
                return profileFragment;


            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return noOfTabs;
    }

}