package com.example.mdc.news;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TeamMembers extends AppCompatActivity {
    List<TeamPlayers> teamplayerslist = new ArrayList<>();
    RecyclerView recyclerView;
    TeamAdapter mAdapter;
    Context context;
    TextView ball,over;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_members);
        context = this;
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mAdapter = new TeamAdapter(teamplayerslist);
        ball = (TextView) findViewById(R.id.tv_ball);
        over = (TextView) findViewById(R.id.tv_over);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(
                new SimpleDividerItemDecoration(context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                TeamPlayers movie = teamplayerslist.get(position);
                Toast.makeText(getApplicationContext(), movie.getStrplayername() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        ball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeamMembers.this,Ballbyball.class);
                startActivity(intent);
            }
        });
        over.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeamMembers.this,Overbyover.class);
                startActivity(intent);
            }
        });
        prepareTeamData();


    }
    private void prepareTeamData() {
        TeamPlayers movie = new TeamPlayers("http://www.aboutpathankot.com/wp-content/uploads/2016/01/MS-Dhoni-1.jpg","MS Dhoni", "Captain of Indian cricket team", "286 - 249 - 67 - 9275");
        teamplayerslist.add(movie);

        movie = new TeamPlayers("http://st3.cricketcountry.com/wp-content/uploads/cricket/20150524070435.jpeg","Virender Sehwag ", "Indian cricket team player", "286 - 249 - 67 - 9275");
        teamplayerslist.add(movie);

        movie = new TeamPlayers("http://p.imgci.com/db/PICTURES/CMS/202700/202701.icon.jpg","Ravichandran Ashwin", "Indian cricket team player", "286 - 249 - 67 - 9275");
        teamplayerslist.add(movie);

        movie = new TeamPlayers("http://p.imgci.com/db/PICTURES/CMS/202700/202751.icon.jpg","Piyush Chawla", "Indian cricket team player", "286 - 249 - 67 - 9275");
        teamplayerslist.add(movie);

        movie = new TeamPlayers("http://p.imgci.com/db/PICTURES/CMS/202600/202683.icon.jpg","Gautam Gambhir", "Indian cricket team player", "286 - 249 - 67 - 9275");
        teamplayerslist.add(movie);

        movie = new TeamPlayers("http://p.imgci.com/db/PICTURES/CMS/128400/128477.icon.jpg","Harbhajan Singh", "Indian cricket team player", "286 - 49 - 67 - 9275");
        teamplayerslist.add(movie);

        movie = new TeamPlayers("http://p.imgci.com/db/PICTURES/CMS/202600/202669.icon.jpg","Zaheer Khan", "Indian cricket team player", "286 - 249 - 67 - 9275");
        teamplayerslist.add(movie);

        movie = new TeamPlayers("http://p.imgci.com/db/PICTURES/CMS/205200/205283.icon.jpg","Virat Kohli", "Indian cricket team player", "26 - 249 - 67 - 9275");
        teamplayerslist.add(movie);




        mAdapter.notifyDataSetChanged();
    }


    class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.MyViewHolder> {

        private List<TeamPlayers> moviesList;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView tvPlayersName, tvPlayersDesc, tvPlayerBatting;
            public CircleImageView ivPlayerImage;

            public MyViewHolder(View view) {
                super(view);
                tvPlayersName = (TextView) view.findViewById(R.id.tvPlayersName);
                tvPlayersDesc = (TextView) view.findViewById(R.id.tvPlayersDesc);
                tvPlayerBatting = (TextView) view.findViewById(R.id.tvPlayerBatting);
                ivPlayerImage = (CircleImageView) view.findViewById(R.id.ivPlayerImage);
            }
        }


        public TeamAdapter(List<TeamPlayers> moviesList) {
            this.moviesList = moviesList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.team_adapter_items, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            TeamPlayers player = moviesList.get(position);
            holder.tvPlayersName.setText(player.getStrplayername());
            holder.tvPlayersDesc.setText(player.getStrplayerdesc());
            holder.tvPlayerBatting.setText(player.getStrplayerscore());

            if(player.getStrplayerimage().length() != 0){
                Glide.with(context).load(player.getStrplayerimage())
                        // .thumbnail(0.5f)
                        .crossFade()
                        //.diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.ivPlayerImage);
            }else{

            }



        }

        @Override
        public int getItemCount() {
            return moviesList.size();
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
                startActivity(new Intent(TeamMembers.this, Teams_match.class));
                return true;
            }
        }
        return false;
    }
}
