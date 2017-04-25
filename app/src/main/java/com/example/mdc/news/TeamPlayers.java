package com.example.mdc.news;

/**
 * Created by likhitha on 4/23/2017.
 */

public class TeamPlayers {
    private String strplayerimage, strplayername, strplayerdesc, strplayerscore;


    public String getStrplayerimage() {
        return strplayerimage;
    }

    public void setStrplayerimage(String strplayerimage) {
        this.strplayerimage = strplayerimage;
    }

    public String getStrplayername() {
        return strplayername;
    }

    public void setStrplayername(String strplayername) {
        this.strplayername = strplayername;
    }

    public String getStrplayerdesc() {
        return strplayerdesc;
    }

    public void setStrplayerdesc(String strplayerdesc) {
        this.strplayerdesc = strplayerdesc;
    }

    public String getStrplayerscore() {
        return strplayerscore;
    }

    public void setStrplayerscore(String strplayerscore) {
        this.strplayerscore = strplayerscore;
    }

    public TeamPlayers(String strplayerimage, String strplayername, String strplayerdesc, String strplayerscore) {
        this.strplayerimage = strplayerimage;
        this.strplayername = strplayername;
        this.strplayerdesc = strplayerdesc;
        this.strplayerscore = strplayerscore;
    }
}
