package com.example.mdc.news.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.mdc.news.R;
import com.example.mdc.news.adapters.HomePagerAdapter;

/**
 * Created by android602 on 18/5/17.
 */

public class HomeActivity extends AppCompatActivity {

    private static final int VIEW_PAGER_PAGES = 3;
    private static final int VIEW_PAGER_HOME_POSITION = 1;

    private ViewPager viewPager;
    private HomePagerAdapter adapterPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
    }

    private void init(){
        this.viewPager = (ViewPager) findViewById(R.id.vp_home);
        this.adapterPager = new HomePagerAdapter(getSupportFragmentManager(), VIEW_PAGER_PAGES, this);
        viewPager.setAdapter(adapterPager);
        viewPager.setOffscreenPageLimit(VIEW_PAGER_PAGES);
        viewPager.setCurrentItem(VIEW_PAGER_HOME_POSITION);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }
}
