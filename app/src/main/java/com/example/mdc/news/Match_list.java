package com.example.mdc.news;

import android.graphics.Bitmap;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;


/**
 * Created by likhitha on 4/19/2017.
 */

public class Match_list extends ArrayList<CharSequence> {
    String date;
    String time;
    String matchname;
    //  Bitmap image;
    String status;
    String team1;
    String team2;
    String teamname1;
    String teamname2;
    String teamdescription1;
    String teamdescription2;


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

    public String getTeamname1() {
        return teamname1;
    }

    public String getTeamname2() {
        return teamname2;
    }

    public String getTeamdescription1() {
        return teamdescription1;
    }

    public String getTeamdescription2() {
        return teamdescription2;
    }

}
