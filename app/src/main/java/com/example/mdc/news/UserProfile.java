package com.example.mdc.news;

import android.graphics.Bitmap;

/**
 * Created by likhitha on 5/5/2017.
 */

public class UserProfile {
    public String pid;
    public String name;
    public String email;
    public String city;
    public String dob;
    public String phone;
    public Bitmap image;
    public UserProfile(){

    }

    public UserProfile(String pid,String name, String dob, String phone, String email, String city) {
        this.pid = pid;
        this.name = name;
        this.dob = dob;
        this.phone = phone;
        this.email = email;
        this.city = city;
       // this.image = image;
    }
    public  String getPid() {
        return pid;
    }
    public String getName(){
        return name;
    }
}
