package com.example.mdc.news;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mdc.news.fragments.Newsmain;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Overbyover extends AppCompatActivity {
    private static final long GAME_LENGTH_MILLISECONDS = 60000;
    private boolean mGameIsInProgress;
    private long mTimerMilliseconds;
    private InterstitialAd mInterstitialAd;
    private CountDownTimer mCountDownTimer;
    ImageView  mRetryButton;
    TextView timer;
    List<BallAnswers> answers;
    DatabaseReference databaseAnswers;
    DatabaseReference childref;
    RadioGroup rgbowl1,rgbowl2,rgbowl3,rgbowl4,rgbowl5,rgbowl6,rgshot1,rgshot2,rgshot3,rgshot4,rgshot5,rgshot6,rgruns1,rgruns2,rgruns3,rgruns4,rgruns5,rgruns6,rgout1,rgout2,rgout3,rgout4,rgout5,rgout6;
    RadioButton bow1, bow2, bow3, bow4, bow5, bow6, shot1, shot2, shot3, shot4, shot5, shot6,out6, run1, run2, run3, run4, run5, run6, run7, out1, out2, out3, out4, out5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overbyover);
        databaseAnswers = FirebaseDatabase.getInstance().getReference("Gamescore");
        childref = databaseAnswers.child("Series/Matches/Match1/Team1");
        timer = (TextView) findViewById(R.id.timer);
        try {
            rgbowl1 = (RadioGroup) findViewById(R.id.rg_bowl1);
            rgbowl2 = (RadioGroup) findViewById(R.id.rg_bowl2);
            rgbowl3 = (RadioGroup) findViewById(R.id.rg_bowl3);
            rgbowl4 = (RadioGroup) findViewById(R.id.rg_bowl4);
            rgbowl5 = (RadioGroup) findViewById(R.id.rg_bowl5);
            rgbowl6 = (RadioGroup) findViewById(R.id.rg_bowl6);
            rgshot1 = (RadioGroup) findViewById(R.id.rg_shot1);
            rgshot2 = (RadioGroup) findViewById(R.id.rg_shot2);
            rgshot3 = (RadioGroup) findViewById(R.id.rg_shot3);
            rgshot4 = (RadioGroup) findViewById(R.id.rg_shot4);
            rgshot5 = (RadioGroup) findViewById(R.id.rg_shot5);
            rgshot6 = (RadioGroup) findViewById(R.id.rg_shot6);
            rgruns1 = (RadioGroup) findViewById(R.id.rg_runs1);
            rgruns2 = (RadioGroup) findViewById(R.id.rg_runs2);
            rgruns3 = (RadioGroup) findViewById(R.id.rg_runs3);
            rgruns4 = (RadioGroup) findViewById(R.id.rg_runs4);
            rgruns5 = (RadioGroup) findViewById(R.id.rg_runs5);
            rgruns6 = (RadioGroup) findViewById(R.id.rg_runs6);
            rgout1 = (RadioGroup) findViewById(R.id.rg_out1);
            rgout2 = (RadioGroup) findViewById(R.id.rg_out2);
            rgout3 = (RadioGroup) findViewById(R.id.rg_out3);
            rgout4 = (RadioGroup) findViewById(R.id.rg_out4);
            rgout5 = (RadioGroup) findViewById(R.id.rg_out5);
            rgout6 = (RadioGroup) findViewById(R.id.rg_out6);
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
            mRetryButton = ((ImageView) findViewById(R.id.btn_oversubmit));
            //mRetryButton.setVisibility(View.INVISIBLE);
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
        int id = rgbowl1.getCheckedRadioButtonId();
        bow1 = (RadioButton) findViewById(id);
        String answer1 = String.valueOf(rgbowl1.indexOfChild(bow1));
        //bow1.getText().toString();
        Log.e("index", String.valueOf(rgbowl1.indexOfChild(bow1)));
        // Toast.makeText(this, String.valueOf(rgbowl.indexOfChild(bow1)), Toast.LENGTH_SHORT).show();
        int id1 = rgshot1.getCheckedRadioButtonId();
        shot1 = (RadioButton) findViewById(id1);
        String answer2 =String.valueOf(rgshot1.indexOfChild(shot1));

        int id2 = rgout1.getCheckedRadioButtonId();
        out1 = (RadioButton) findViewById(id2);
        String answer4 = String.valueOf(rgout1.indexOfChild(out1));

        int id3 = rgruns1.getCheckedRadioButtonId();
        run1 = (RadioButton) findViewById(id3);
        String answer3 = String.valueOf(rgruns1.indexOfChild(run1));

        String id4 = childref.push().getKey();
        BallAnswers artist = new BallAnswers(id4, answer1, answer2, answer3, answer4);
        childref.child(id4).setValue(artist);

        int id5 = rgbowl2.getCheckedRadioButtonId();
        bow2 = (RadioButton) findViewById(id5);
        String answer5 = String.valueOf(rgbowl2.indexOfChild(bow2));
        //bow1.getText().toString();
        Log.e("index", String.valueOf(rgbowl1.indexOfChild(bow1)));
        // Toast.makeText(this, String.valueOf(rgbowl.indexOfChild(bow1)), Toast.LENGTH_SHORT).show();
        int id6 = rgshot2.getCheckedRadioButtonId();
        shot2 = (RadioButton) findViewById(id6);
        String answer6 =String.valueOf(rgshot2.indexOfChild(shot2));

        int id7 = rgout2.getCheckedRadioButtonId();
        out2 = (RadioButton) findViewById(id7);
        String answer7 = String.valueOf(rgout2.indexOfChild(out2));

        int id8 = rgruns2.getCheckedRadioButtonId();
        run2 = (RadioButton) findViewById(id8);
        String answer8 = String.valueOf(rgruns2.indexOfChild(run2));

        String id9 = childref.push().getKey();
        BallAnswers artist1 = new BallAnswers(id9, answer5, answer6, answer7, answer8);
        childref.child(id9).setValue(artist1);

        int id10 = rgbowl3.getCheckedRadioButtonId();
        bow3 = (RadioButton) findViewById(id10);
        String answer9 = String.valueOf(rgbowl3.indexOfChild(bow3));
        //bow1.getText().toString();
        Log.e("index", String.valueOf(rgbowl1.indexOfChild(bow1)));
        // Toast.makeText(this, String.valueOf(rgbowl.indexOfChild(bow1)), Toast.LENGTH_SHORT).show();
        int id11 = rgshot3.getCheckedRadioButtonId();
        shot3 = (RadioButton) findViewById(id11);
        String answer10 =String.valueOf(rgshot3.indexOfChild(shot3));

        int id12 = rgout3.getCheckedRadioButtonId();
        out3 = (RadioButton) findViewById(id12);
        String answer11 = String.valueOf(rgout3.indexOfChild(out3));

        int id13 = rgruns3.getCheckedRadioButtonId();
        run3 = (RadioButton) findViewById(id13);
        String answer12 = String.valueOf(rgruns3.indexOfChild(run3));

        String id14 = childref.push().getKey();
        BallAnswers artist2 = new BallAnswers(id14, answer9, answer10, answer11, answer12);
        childref.child(id14).setValue(artist2);

        int id15 = rgbowl4.getCheckedRadioButtonId();
        bow4 = (RadioButton) findViewById(id15);
        String answer13 = String.valueOf(rgbowl4.indexOfChild(bow4));
        //bow1.getText().toString();
        Log.e("index", String.valueOf(rgbowl4.indexOfChild(bow4)));
        // Toast.makeText(this, String.valueOf(rgbowl.indexOfChild(bow1)), Toast.LENGTH_SHORT).show();
        int id16 = rgshot4.getCheckedRadioButtonId();
        shot4 = (RadioButton) findViewById(id16);
        String answer14 =String.valueOf(rgshot4.indexOfChild(shot4));

        int id17 = rgout4.getCheckedRadioButtonId();
        out4 = (RadioButton) findViewById(id17);
        String answer15 = String.valueOf(rgout4.indexOfChild(out4));

        int id18 = rgruns4.getCheckedRadioButtonId();
        run4 = (RadioButton) findViewById(id18);
        String answer16 = String.valueOf(rgruns4.indexOfChild(run4));

        String id19 = childref.push().getKey();
        BallAnswers artist3 = new BallAnswers(id19, answer13, answer14, answer15, answer16);
        childref.child(id19).setValue(artist3);

        int id20 = rgbowl5.getCheckedRadioButtonId();
        bow5 = (RadioButton) findViewById(id20);
        String answer17 = String.valueOf(rgbowl5.indexOfChild(bow5));
        //bow1.getText().toString();
        Log.e("index", String.valueOf(rgbowl5.indexOfChild(bow5)));
        // Toast.makeText(this, String.valueOf(rgbowl.indexOfChild(bow1)), Toast.LENGTH_SHORT).show();
        int id21 = rgshot5.getCheckedRadioButtonId();
        shot5 = (RadioButton) findViewById(id21);
        String answer18 =String.valueOf(rgshot5.indexOfChild(shot5));

        int id22 = rgout5.getCheckedRadioButtonId();
        out5 = (RadioButton) findViewById(id22);
        String answer19 = String.valueOf(rgout5.indexOfChild(out5));

        int id23 = rgruns5.getCheckedRadioButtonId();
        run5 = (RadioButton) findViewById(id23);
        String answer20 = String.valueOf(rgruns5.indexOfChild(run5));

        String id24 = childref.push().getKey();
        BallAnswers artist4 = new BallAnswers(id24, answer17, answer18, answer19, answer20);
        childref.child(id24).setValue(artist4);

        int id25 = rgbowl6.getCheckedRadioButtonId();
        bow6 = (RadioButton) findViewById(id25);
        String answer21 = String.valueOf(rgbowl6.indexOfChild(bow6));
        //bow1.getText().toString();
        Log.e("index", String.valueOf(rgbowl6.indexOfChild(bow6)));
        // Toast.makeText(this, String.valueOf(rgbowl.indexOfChild(bow1)), Toast.LENGTH_SHORT).show();
        int id26 = rgshot6.getCheckedRadioButtonId();
        shot6 = (RadioButton) findViewById(id26);
        String answer22 =String.valueOf(rgshot6.indexOfChild(shot6));

        int id27 = rgout6.getCheckedRadioButtonId();
        out6 = (RadioButton) findViewById(id27);
        String answer23 = String.valueOf(rgout6.indexOfChild(out6));

        int id28 = rgruns6.getCheckedRadioButtonId();
        run6 = (RadioButton) findViewById(id28);
        String answer24 = String.valueOf(rgruns6.indexOfChild(run6));

        String id29 = childref.push().getKey();
        BallAnswers artist5 = new BallAnswers(id29, answer21, answer22, answer23, answer24);
        childref.child(id29).setValue(artist5);
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
                // timer.setText("done!");
                mInterstitialAd.show();
                //  mRetryButton.setVisibility(View.VISIBLE);
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
            Toast.makeText(this, "Ad did not load", Toast.LENGTH_SHORT).show();
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
        menuInflater.inflate(R.menu.ballmain, menu);
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
