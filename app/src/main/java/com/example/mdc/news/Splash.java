package com.example.mdc.news;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final ImageView zoom = (ImageView) findViewById(R.id.image_splash);
        new Handler().postDelayed(new Runnable() {

         /*
          * Showing splash screen with a timer. This will be useful when you
          * want to show case your app logo / company
          */

            @Override
            public void run() {
                //try {
                 //   Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate);
                 //   zoom.startAnimation(animation);

               // } finally {

                    Intent i = new Intent(Splash.this, LoginActivity.class);
                    startActivity(i);
                //}

                finish();
            }
        }, 3000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
