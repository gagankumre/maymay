package com.example.gagan.maymay;

import android.widget.ImageView;

/**
 * Created by GAGAN on 05-02-2018.
 */

public class Notificationcard {
    private String name, date, time;
    private ImageView profilepic;

    public Notificationcard() {
    }

    public Notificationcard(String name, String date, String time, ImageView profilepic) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.profilepic= profilepic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {return time;}

    public void setTime(String time) {
        this.time = time;
    }

    public ImageView getProfilepic(){return profilepic;}

    public void setProfilepic(){this.profilepic=profilepic;}
}

