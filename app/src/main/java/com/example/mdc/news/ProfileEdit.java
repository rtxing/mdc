package com.example.mdc.news;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileEdit extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    EditText etname,etemail,etphone,etcity,etdob;
    TextView tvemail;
    Button submit,logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
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
      submit.setOnClickListener(this);
    }
    private void saveUserInformation() {
        //Getting values from database
        String name = etname.getText().toString().trim();
        String dob = etdob.getText().toString().trim();
        String phone = etphone.getText().toString().trim();
        String email = etemail.getText().toString().trim();
        String city = etcity.getText().toString().trim();

        String id = databaseReference.push().getKey();
        //creating a userinformation object
        UserProfile userInformation = new UserProfile(id, name, dob, phone, email, city);

        //getting the current logged in user
        FirebaseUser user = firebaseAuth.getCurrentUser();


        databaseReference.child(user.getUid()).setValue(userInformation);

        //displaying a success toast
        Toast.makeText(this, "Information Saved...", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {
        //if logout is pressed
    /*  if (view == logout) {
            //logging out the user
            firebaseAuth.signOut();
            //closing activity
            finish();
            //starting login activity
            startActivity(new Intent(this, LoginActivity.class));
        }
*/
        if(view == submit){
            saveUserInformation();
        }

    }
}
