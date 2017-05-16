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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileEdit extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    EditText etname,etemail,etphone,etcity,etdob;
    TextView tvemail;
    Button submit,logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        firebaseAuth = FirebaseAuth.getInstance();


        //if the user is not logged in
        //that means current user will return null
        if (firebaseAuth.getCurrentUser() == null) {
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, LoginActivity.class));
        }

        //getting the database reference
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Profile");


        //getting the views from xml resource
        etname = (EditText) findViewById(R.id.et_name);
        etdob = (EditText) findViewById(R.id.et_dob);
        etphone = (EditText) findViewById(R.id.et_phone);
        etemail = (EditText) findViewById(R.id.et_email);
        etcity = (EditText) findViewById(R.id.et_city);
        submit = (Button) findViewById(R.id.btn_save);
      //  logout = (Button) findViewById(R.id.btn_logout);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        //initializing views
        tvemail = (TextView) findViewById(R.id.tv_email);
      //  buttonLogout = (Button) findViewById(R.id.buttonLogout);

        //displaying logged in user name
      tvemail.setText("Welcome " + user.getEmail());

        //adding listener to button
      // logout.setOnClickListener(this);
      submit.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              String name = etname.getText().toString().trim();
              String dob = etdob.getText().toString().trim();
              String phone = etphone.getText().toString().trim();
              String email = etemail.getText().toString().trim();
              String city = etcity.getText().toString().trim();

              String id = databaseReference.push().getKey();
              FirebaseUser user = firebaseAuth.getCurrentUser();
              //creating a userinformation object
              UserProfile userInformation = new UserProfile(user.getUid(), name, dob, phone, email, city);

              //getting the current logged in user
              if (name != null && dob != null && phone != null && email != null && city != null) {
                  databaseReference.child(user.getUid()).child("name").setValue(name);
                  databaseReference.child(user.getUid()).child("dob").setValue(dob);
                  databaseReference.child(user.getUid()).child("phone").setValue(phone);
                  databaseReference.child(user.getUid()).child("email").setValue(email);
                  databaseReference.child(user.getUid()).child("city").setValue(city);
                  databaseReference.child(user.getUid()).child("pid").setValue(user.getUid());
                  SharedPreferences sharedPreferences = getSharedPreferences(Session.PREFS_NAME, 0);
                  SharedPreferences.Editor editor = sharedPreferences.edit();
                  editor.putString(Session.KEY_CITY, city);
                  editor.putString(Session.KEY_NAME, name);
                  editor.commit();

                  //displaying a success toast
                  Toast.makeText(ProfileEdit.this, "Profile Updated...!!", Toast.LENGTH_LONG).show();
              }
              else {
                  Toast.makeText(ProfileEdit.this, "Enter all fields...!!", Toast.LENGTH_LONG).show();
              }
          }
      });
    }
    private void saveUserInformation() {
        //Getting values from database

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
