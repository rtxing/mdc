package com.example.mdc.news.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mdc.news.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import org.jetbrains.annotations.Nullable;


public class Play extends Fragment {
    private static final long GAME_LENGTH_MILLISECONDS = 0;
    private boolean mGameIsInProgress;
    private long mTimerMilliseconds;
    private InterstitialAd mInterstitialAd;
    private CountDownTimer mCountDownTimer;
    private Button mRetryButton;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        Log.e("hi","news");
        View rootView = inflater.inflate(R.layout.fragment_play, container, false);
        final TextView textView = ((TextView) rootView.findViewById(R.id.timer));
       // verticalViewPager = (VerticalViewPager) rootView.findViewById(R.id.verticleViewPager);
        getActivity().setTitle("Play");
        setHasOptionsMenu(true);


        Log.e("hi","news");
        MobileAds.initialize(getActivity(), "ca-app-pub-3940256099942544~3347511713");
        MobileAds.initialize(getActivity(), "ca-app-pub-5879449540669875~4077574146");
        mInterstitialAd = new InterstitialAd(getActivity());
        // Defined in res/values/strings.xml
        mInterstitialAd.setAdUnitId(getString(R.string.ad_unit_id));
        final AdRequest adRequest = new AdRequest.Builder().build();
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                startGame();
            }
        });

        // Create the "retry" button, which tries to show an interstitial between game plays.
        mRetryButton = ((Button) rootView.findViewById(R.id.btn_submit));
         mRetryButton.setVisibility(View.INVISIBLE);
        mRetryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInterstitial();
            }
        });

        startGame();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles


    }
    private void createTimer(final long milliseconds) {
        // Create the game timer, which counts down to the end of the level
        // and shows the "retry" button.
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }


        mCountDownTimer = new CountDownTimer(milliseconds, 50) {
            @Override
            public void onTick(long millisUnitFinished) {
                mTimerMilliseconds = millisUnitFinished;
                //textView.setText("seconds remaining: " + ((millisUnitFinished / 1000) + 1));
            }

            @Override
            public void onFinish() {
                mGameIsInProgress = false;
                //textView.setText("done!");
                mRetryButton.setVisibility(View.VISIBLE);
            }
        };
    }
    @Override
    public void onPause() {
        // Cancel the timer if the game is paused.
        mCountDownTimer.cancel();
        super.onPause();
    }

    private void showInterstitial() {
        // Show the ad if it's ready. Otherwise toast and restart the game.
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Toast.makeText(getActivity(), "Ad did not load", Toast.LENGTH_SHORT).show();
            startGame();
        }
    }

    private void startGame() {
        // Request a new ad if one isn't already loaded, hide the button, and kick off the timer.
        if (!mInterstitialAd.isLoading() && !mInterstitialAd.isLoaded()) {
            AdRequest adRequest = new AdRequest.Builder().build();
            mInterstitialAd.loadAd(adRequest);
        }

        mRetryButton.setVisibility(View.INVISIBLE);
        resumeGame(GAME_LENGTH_MILLISECONDS);
    }

    private void resumeGame(long milliseconds) {
        // Create a new timer for the correct length and start it.
        mGameIsInProgress = true;
        mTimerMilliseconds = milliseconds;
        createTimer(milliseconds);
        mCountDownTimer.start();
    }
  }
