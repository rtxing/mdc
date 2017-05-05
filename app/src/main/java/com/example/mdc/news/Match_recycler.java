package com.example.mdc.news;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import org.jetbrains.annotations.Nullable;
import java.util.ArrayList;
import java.util.List;

public class Match_recycler extends Fragment {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    //private CardAdapter adapter;
    // ArrayList<String> matchlist=new ArrayList<>();
    private DatabaseReference databaseReference;
    private DatabaseReference childRef;
    private DatabaseReference child;
    List<String> matchlist;
    ArrayList<String> matchname = new ArrayList<String>();
    ArrayList<String> matchdate = new ArrayList<String>();
    ArrayList<String> matchtime = new ArrayList<String>();
    ArrayList<Bitmap> matchimage = new ArrayList<Bitmap>();
    ListView lv;
    Match_list name;
    DatabaseReference db;
    CardAdapter adapter;
    Object team;
    //com.google.firebase.database.DataSnapshot dataSnapshot;

   /* private ListView listView;
    private String names[] = {

            "India vs Australia",
            "RPS Vs DD",
            "RCB Vs MI"

    };

    private String date[] = {
            "20/04/2017",
            "21/04/2017",
            "22/04/2017"

    };
    private String time[] = {
            "8:00 PM",
            "8:00 PM",
            "8:00 PM"

    };

    private Integer image[] = {
            R.drawable.image2,
            R.drawable.rps,
            R.drawable.image2
    };*/

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View rootView = inflater.inflate(R.layout.activity_match_recycler, container, false);
        Log.e("start", "Start");
        getActivity().setTitle("Play");
      /*  CardAdapter customList = new CardAdapter(getActivity(), names, date, time, image);

        listView = (ListView) rootView.findViewById(R.id.list_match);
        listView.setAdapter(customList);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //finish();
                startActivity(new Intent(getActivity(),Teams_match.class));
            }
        });*/

       final ArrayList<Match_list> matches = new ArrayList<>();

        //  matchlist = new ArrayList<>();
        //  databaseReference = FirebaseDatabase.getInstance().getReference();
        // recyclerView = (RecyclerView) rootView.findViewById(R.id.task_list);
        // recyclerView.setHasFixedSize(true);
        Log.e("start", "Start");
        lv = (ListView) rootView.findViewById(R.id.list_match);
        //INITIALIZE FIREBASE DB
        db = FirebaseDatabase.getInstance().getReference();
        childRef = db.child("Series/1/matches");
        Query query = childRef.orderByChild("status").equalTo("1");
        Log.e("adapter", String.valueOf(childRef));
        try {
            query.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                @Override

                public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                    Log.e("data", String.valueOf(dataSnapshot.getChildren()));
                  //  team = dataSnapshot.getValue();
                    for (com.google.firebase.database.DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                     // for (int i = 1; i < postSnapshot.getChildrenCount(); i++) {
                            //Getting the data from snapshot
                            Log.e("post", String.valueOf(dataSnapshot.getChildren()));
                            Match_list match = postSnapshot.getValue(Match_list.class);
                            //     matchname.add(match.getMatchname());
                            // matchimage.add(match.getMatchimage());
                            // matchdate.add(match.getMatchdate());
                            // matchtime.add(match.getMatchtime());
                            matches.add(match);
                       // }
                        adapter = new CardAdapter(getActivity(), matches); //matchname, matchimage, matchdate, matchtime);
                        lv.setAdapter(adapter);
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
     lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
             Intent intent = new Intent(getActivity(), Teams_match.class);
            // intent.putExtra("matchname", String.valueOf(team));
             //Log.e("datasnapshot", String.valueOf(team));
             startActivity(intent);
            }
     });
        return rootView;
    }
}




