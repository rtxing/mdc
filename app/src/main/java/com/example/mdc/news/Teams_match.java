package com.example.mdc.news;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;


public class
Teams_match extends AppCompatActivity {
LinearLayout linear1,linear2;
    TextView ball,over,team1,team2,description1,description2;
    DatabaseReference db,childRef;
    ArrayList<String> ateam1 = new ArrayList<>();
    ArrayList<String> ateam2 = new ArrayList<>();
    ArrayList<String> adescription1 = new ArrayList<>();
    ArrayList<String> adescription2 = new ArrayList<>();
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams_match);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Log.e("hi","hi");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        linear1 = (LinearLayout) findViewById(R.id.linear1);
        linear2 = (LinearLayout) findViewById(R.id.linear2);
        ball = (TextView) findViewById(R.id.tv_ball);
        over = (TextView) findViewById(R.id.tv_over);
        team1 = (TextView) findViewById(R.id.tv_team1);
        team2 = (TextView) findViewById(R.id.tv_team2);
        description1 = (TextView) findViewById(R.id.tv_description1);
        description2 = (TextView) findViewById(R.id.tv_description2);
        Bundle extras = getIntent().getExtras();
    try {
        if (extras != null) {
            name = extras.getString("matchname");
            Log.e("name", String.valueOf(name));

        }
        db = FirebaseDatabase.getInstance().getReference();
        childRef = db.child("Series/1/matches/Match1");
        Log.e("childref", String.valueOf(childRef));
       //  Query query =  childRef.child(name);
        //Log.e("query", String.valueOf(query));
       /* childRef.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                for (com.google.firebase.database.DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                  //  for (com.google.firebase.database.DataSnapshot child : postSnapshot.getChildren()) {
                        Team team = postSnapshot.getValue(Team.class);
                        ateam1.add(team.getTeamname1());
                        ateam2.add(team.getTeamname2());
                        adescription1.add(team.getTeamdescription1());
                        adescription2.add(team.getTeamdescription2());
                   // }
                }
                Log.e("team1", String.valueOf(ateam1));
                Log.e("team1", String.valueOf(ateam2));
                Log.e("team1", String.valueOf(adescription1));
                Log.e("team1", String.valueOf(adescription2));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
    }
    catch (Exception e)
    {
        e.printStackTrace();
    }
  //    team1.setText(ateam1.get(0));
      //  team2.setText(ateam2.get(0));
      //  adescription1.set(0,adescription1.get(0));
      // adescription2.set(0,adescription2.get(0));
        linear1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Teams_match.this,TeamMembers.class);
                startActivity(intent);
            }
        });
        linear2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Teams_match.this,TeamMembers.class);
                startActivity(intent);
            }
        });
        ball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Teams_match.this,Ballbyball.class);
                startActivity(intent);
            }
        });
        over.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Teams_match.this,Overbyover.class);
                startActivity(intent);
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
              //  finish();
                getFragmentManager().popBackStack();
                return true;
            }
        }
        return false;
    }
}
