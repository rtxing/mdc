package com.example.mdc.news;

import android.graphics.Bitmap;

import com.google.firebase.database.Exclude;

/**
 * Created by likhitha on 4/19/2017.
 */

public class Match_list {
    String date;
    String time;
    String matchname;
 //  Bitmap image;
    String status;
    String team1;
    String team2;

    public String getMatchname() {
        return matchname;
    }
  //  public Bitmap getMatchimage() {
  //     return image;
  //  }
    public String getMatchtime() {
        return time;
    }
    public String getMatchdate() {
        return date;
    }
    public String getMatchStatus() {
        return status;
    }
    public void setTeam1(String team1) {
        this.team1 = team1;
    }
    public void setTeam2(String team2) {
        this.team2 = team2;
    }
    @Exclude
   public String getTeam1() {
        return team1;
    }
    @Exclude
    public String getTeam2() {
        return team2;
    }

}
