package com.example.likhitha.news;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import org.jetbrains.annotations.Nullable;

public class NewsFragment extends Fragment {
    VerticalViewPager verticalViewPager;
    //DataBaseHelper db;
    int icount;
    Cursor mcursor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        Log.e("hi","news");
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);
        verticalViewPager = (VerticalViewPager) rootView.findViewById(R.id.verticleViewPager);
      getActivity().setTitle("News");
        setHasOptionsMenu(true);
        Log.e("hi","news");
      //  verticalViewPager.setAdapter(new VerticlePagerAdapter(getActivity()));

     /*     try {
            mcursor = db.getAllNews();
            mcursor.moveToFirst();
            icount = mcursor.getInt(0);
            if(icount>0){

            }
        }
        catch (Exception e) {

          String url = Config.DATA_URL;
            StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    showJSON(response);
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //.makeText(getApplicationContext(),error.getMessage().toString(),Toast.LENGTH_LONG).show();
                        }
                    });

            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(stringRequest);

        }
      }*/

        return rootView;
    }
   /* private void showJSON(String response){
        DataBaseHelper db = new DataBaseHelper(getActivity());
        String title="";
        String image="";
        String details="";
        String datetime="";

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);

            for(int i=0; i<result.length(); i++) {
                JSONObject collegeData = result.getJSONObject(i);
                title = collegeData.getString(Config.KEY_TITLE);
                image = collegeData.getString(Config.KEY_IMAGE);
                details = collegeData.getString(Config.KEY_DETAILS);
                datetime = collegeData.getString(Config.KEY_DATETIME);
                db.inserData(title, image, details, datetime);
                mcursor = db.getAllNews();
                mcursor.moveToFirst();
                int iicount = mcursor.getInt(0);
                if(iicount>0){
                    verticalViewPager.setAdapter(new VerticlePagerAdapter(getActivity()));
                }


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }*/


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles

        Log.e("hi","hi");


        verticalViewPager.setAdapter(new VerticlePagerAdapter(getActivity()));

    }
   /* @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.refresh, menu);
    }


    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_sync: {
                Toast.makeText(getContext(), "Sync..", Toast.LENGTH_SHORT).show();
                verticalViewPager.setAdapter(new VerticlePagerAdapter(getActivity()));

                return true;
            }


        }return false;
    }*/
}
