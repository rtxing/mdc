package com.example.mdc.news;

import android.content.Intent;
import android.os.CountDownTimer;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Ballbyball extends AppCompatActivity {
    private static final long GAME_LENGTH_MILLISECONDS = 10000;
    private boolean mGameIsInProgress;
    private long mTimerMilliseconds;
    private InterstitialAd mInterstitialAd;
    private CountDownTimer mCountDownTimer;
    private ImageView mRetryButton;
  TextView timer;
    List<BallAnswers> answers;
    RadioGroup rgbowl, rgbowl1, rgshot, rgshot1, rgruns, rgout;
    DatabaseReference databaseAnswers;
    DatabaseReference childref;
    RadioButton bow1, bow2, bow3, bow4, bow5, bow6, shot1, shot2, shot3, shot4, shot5, shot6, run1, run2, run3, run4, run5, run6, run7, out1, out2, out3, out4, out5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ballbyball);
        databaseAnswers = FirebaseDatabase.getInstance().getReference("Gamescore");
        childref = databaseAnswers.child("Series/Matches/Match1/Team1");
        timer = (TextView) findViewById(R.id.timer);
        try {


        /*bow1 = (RadioButton) findViewById(R.id.bow_1);
        bow2 = (RadioButton) findViewById(R.id.bow_2);
        bow3 = (RadioButton) findViewById(R.id.bow_3);
        bow4 = (RadioButton) findViewById(R.id.bow_4);
        bow5 = (RadioButton) findViewById(R.id.bow_5);
        bow6 = (RadioButton) findViewById(R.id.bow_6);
        shot1 = (RadioButton) findViewById(R.id.shot_1);
        shot2 = (RadioButton) findViewById(R.id.shot_2);
        shot3 = (RadioButton) findViewById(R.id.shot_3);
        shot4 = (RadioButton) findViewById(R.id.shot_4);
        shot5 = (RadioButton) findViewById(R.id.shot_5);
        shot6 = (RadioButton) findViewById(R.id.shot_6);
        run1 = (RadioButton) findViewById(R.id.run_1);
        run2 = (RadioButton) findViewById(R.id.run_2);
        run3 = (RadioButton) findViewById(R.id.run_3);
        run4 = (RadioButton) findViewById(R.id.run_4);
        run5 = (RadioButton) findViewById(R.id.run_5);
        run6 = (RadioButton) findViewById(R.id.run_6);
        run7 = (RadioButton) findViewById(R.id.run_7);
        out1 = (RadioButton) findViewById(R.id.out_1);
        out2 = (RadioButton) findViewById(R.id.out_2);
        out3 = (RadioButton) findViewById(R.id.out_3);
        out4 = (RadioButton) findViewById(R.id.out_4);
        out5 = (RadioButton) findViewById(R.id.out_5);*/
            rgbowl = (RadioGroup) findViewById(R.id.rg_bowl);
            //   rgbowl1 = (RadioGroup) findViewById(R.id.rg_bowl1);
            rgout = (RadioGroup) findViewById(R.id.rg_out);
            rgruns = (RadioGroup) findViewById(R.id.rg_runs);
            rgshot = (RadioGroup) findViewById(R.id.rg_shot);
            //   rgshot1 = (RadioGroup) findViewById(R.id.rg_shot1);
            answers = new ArrayList<>();
            MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
            MobileAds.initialize(this, "ca-app-pub-5879449540669875~4077574146");
            mInterstitialAd = new InterstitialAd(this);

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
            mRetryButton = ((ImageView) findViewById(R.id.btn_ballsubmit));
            // mRetryButton.setVisibility(View.INVISIBLE);
            mRetryButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addAnswers();
                    showInterstitial();
                }
            });

            startGame();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void addAnswers() {
        //getting the values to save
        int id = rgbowl.getCheckedRadioButtonId();
        bow1 = (RadioButton) findViewById(id);
        String answer1 = String.valueOf(rgbowl.indexOfChild(bow1));
                //bow1.getText().toString();
        Log.e("index", String.valueOf(rgbowl.indexOfChild(bow1)));
       // Toast.makeText(this, String.valueOf(rgbowl.indexOfChild(bow1)), Toast.LENGTH_SHORT).show();
        int id1 = rgshot.getCheckedRadioButtonId();
        shot1 = (RadioButton) findViewById(id1);
        String answer2 =String.valueOf(rgshot.indexOfChild(shot1));

        int id2 = rgout.getCheckedRadioButtonId();
        out1 = (RadioButton) findViewById(id2);
        String answer4 = String.valueOf(rgout.indexOfChild(out1));

        int id3 = rgruns.getCheckedRadioButtonId();
        run1 = (RadioButton) findViewById(id3);
        String answer3 = String.valueOf(rgruns.indexOfChild(run1));


        //getting a unique id using push().getKey() method
        //it will create a unique id and we will use it as the Primary Key for our Artist
        String id4 = childref.push().getKey();

        //creating an Artist Object
        BallAnswers artist = new BallAnswers(id4, answer1, answer2, answer3, answer4);

        //Saving the Artist
       childref.child(id4).setValue(artist);


        //displaying a success toast
        Toast.makeText(this, "Data added", Toast.LENGTH_LONG).show();
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
                timer.setText("" + ((millisUnitFinished / 1000) + 1));
            }

            @Override
            public void onFinish() {
                mGameIsInProgress = false;
                //timer.setText("done!");
                mInterstitialAd.show();
               // mRetryButton.setVisibility(View.VISIBLE);
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
            Toast.makeText(Ballbyball.this, "Ad did not load", Toast.LENGTH_SHORT).show();
            startGame();
        }
    }

    private void startGame() {
        // Request a new ad if one isn't already loaded, hide the button, and kick off the timer.
        if (!mInterstitialAd.isLoading() && !mInterstitialAd.isLoaded()) {
            AdRequest adRequest = new AdRequest.Builder().build();
            mInterstitialAd.loadAd(adRequest);
        }

       // mRetryButton.setVisibility(View.INVISIBLE);
        resumeGame(GAME_LENGTH_MILLISECONDS);
    }

    private void resumeGame(long milliseconds) {
        // Create a new timer for the correct length and start it.
        mGameIsInProgress = true;
        mTimerMilliseconds = milliseconds;
        createTimer(milliseconds);
        mCountDownTimer.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // automatically handle clicks on the Home/Up button, so long
        MenuInflater menuInflater = getMenuInflater();
        //menuInflater.inflate(R.menu.Profile_customer, menu);
        menuInflater.inflate(R.menu.overmain, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home: {
                finish();
                startActivity(new Intent(this, Newsmain.class));
                return true;
            }

            }
        return false;
    }
}
