package com.example.mdc.news;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.firebase.client.Firebase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

/**
 * Created by likhitha on 4/20/2017.
 */

public class CardAdapter extends BaseAdapter {
  /*  private String[] names;
    private String[] date;
    private String[] time;
    private Integer[] image;
    private Activity context;

    public CardAdapter(Activity context, String[] names, String[] date, String[] time, Integer[] image) {
        super(context, R.layout.match_list, names);
        this.context = context;
        this.names = names;
        this.date = date;
        this.time = time;
        this.image = image;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.match_list, null, true);
       // TextView textViewName = (TextView) listViewItem.findViewById(R.id.matchname);
       // TextView textViewDesc = (TextView) listViewItem.findViewById(R.id.textViewDesc);
       // ImageView image = (ImageView) listViewItem.findViewById(R.id.imageView);
       TextView nameTxt = (TextView) listViewItem.findViewById(R.id.matchname);
        ImageView image1 = (ImageView) listViewItem.findViewById(R.id.match_icon);
        TextView dateTxt = (TextView) listViewItem.findViewById(R.id.match_date);
        TextView timeTxt = (TextView) listViewItem.findViewById(R.id.match_time);
        nameTxt.setText(names[position]);
        dateTxt.setText(date[position]);
        timeTxt.setText(time[position]);
        image1.setImageResource(image[position]);
        return  listViewItem;
    }*/
   Context c;
    ArrayList<Match_list> matches;
    ArrayList<String> matchname;
    ArrayList<String> matchdate;
    ArrayList<String> matchtime;
    ArrayList<Bitmap> matchimage;


    public CardAdapter(Context c, ArrayList<Match_list> matches){  //ArrayList<String> matchname,  ArrayList<Bitmap> matchimage, ArrayList<String> matchdate, ArrayList<String> matchtime) {
        this.c = c;
        this.matches = matches;
        //this.matchname = matchname;
      //  this.matchimage = matchimage;
       /// this.matchdate = matchdate;
        //this.matchtime = matchtime;
    }

    @Override
    public int getCount() {
        return matches.size();
    }

    @Override
    public Object getItem(int pos) {
        return matches.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = LayoutInflater.from(c).inflate(R.layout.match_list, viewGroup, false);
        }
        Log.e("adapter","adapter");
        TextView nameTxt = (TextView) convertView.findViewById(R.id.matchname);
       // ImageView image = (ImageView) convertView.findViewById(R.id.matchicon);
        TextView dateTxt = (TextView) convertView.findViewById(R.id.match_date);
        TextView timeTxt = (TextView) convertView.findViewById(R.id.match_time);
        final Match_list s = (Match_list) this.getItem(position);
        nameTxt.setText(s.getMatchname());
        //image.setImageBitmap(s.getMatchimage());
        dateTxt.setText(s.getMatchdate());
        timeTxt.setText(s.getMatchtime());
        return convertView;
    }
}
