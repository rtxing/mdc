package com.example.mdc.news;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

public class Profilepickdisplay extends AppCompatActivity {
    Bitmap decodedByte;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilepickdisplay);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        try {
            sharedPreferences = this.getSharedPreferences(Session.PREFS_NAME, 0);


            String mypick = sharedPreferences.getString(Session.KEY_PROFILE_PICK, "Image Not Available");
            byte[] decodedString = Base64.decode(mypick, 0);
            decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            ImageView imgFullImage = (ImageView) findViewById(R.id.image_profile);
            imgFullImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Toast.makeText(ProfilePickDisplay.this, "Bhanu", Toast.LENGTH_SHORT).show();
                }
            });

            imgFullImage.setImageBitmap(decodedByte);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // automatically handle clicks on the Home/Up button, so long
        MenuInflater menuInflater = getMenuInflater();
        //menuInflater.inflate(R.menu.Profile_customer, menu);
        menuInflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home: {
                finish();
                getFragmentManager().popBackStack();
                //startActivity(new Intent(this, Newsmain.class));
                return true;
            }
        }
        return false;
    }
}
