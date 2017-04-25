package com.example.mdc.news;

import android.app.Fragment;

/**
 * Created by likhitha on 4/14/2017.
 */

public class News {
    private String title;
    private String date;
    private String description;
    private String id;
    private String image;
    private String url;

    public News() {
      /*Blank default constructor essential for Firebase*/
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }
    public String getDescription() {
        return description;
    }
    public String getId() {
        return id;
    }
    public String getImage() {
        return image;
    }
    public String getUrl() {
        return url;
    }
}
