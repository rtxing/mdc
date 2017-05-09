package com.example.mdc.news;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileNameEdit extends AppCompatActivity {
    Button btn_ok,btn_cancel;
    EditText etname;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setContentView(R.layout.activity_profile_name_edit);
        btn_ok = (Button) findViewById(R.id.btn_ok);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        etname = (EditText) findViewById(R.id.et_nameedit);
        firebaseAuth = FirebaseAuth.getInstance();


        //if the user is not logged in
        //that means current user will return null
        if (firebaseAuth.getCurrentUser() == null) {
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, LoginActivity.class));
        }
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Profile");
        final SharedPreferences sharedPreferences = this.getSharedPreferences(Session.PREFS_NAME, 0);
       btn_ok.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
              // Intent intent = new Intent(ProfileNameEdit.this, Profile.class);
               FirebaseUser user = firebaseAuth.getCurrentUser();
               databaseReference.child(user.getUid()).child("name").setValue(etname.getText().toString());
               SharedPreferences sharedPreferences = getSharedPreferences(Session.PREFS_NAME, 0);
               SharedPreferences.Editor editor = sharedPreferences.edit();
               editor.putString(Session.KEY_NAME, etname.getText().toString());
               editor.commit();
               Toast.makeText(ProfileNameEdit.this, "Name Updated...!", Toast.LENGTH_SHORT).show();
               etname.setText("");
              // getFragmentManager().popBackStack();
           }
       });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etname.setText("");
               // getFragmentManager().popBackStack();
            }
        });
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


                // startActivity(new Intent(this, Profile.class));
                return true;
            }
        }
        return false;
    }
}
