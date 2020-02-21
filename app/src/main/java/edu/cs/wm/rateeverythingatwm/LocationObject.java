package edu.cs.wm.rateeverythingatwm;

import android.location.Location;
import android.media.Image;
import android.util.Log;

import java.util.ArrayList;

public class LocationObject {

    private String name;
    private String review;
    private Location location;
    private Image image;
    private int rating;
    private ArrayList<String> comments;
    String tag = "LOCATION OBJECT";


    public LocationObject(){
        Log.v(tag, "Location Object");
    }

    public LocationObject(String name, String review, Location location, Image image, int rating,
                          ArrayList comments){
        this.name = name;
        this.review = review;
        this.location = location;
        this.image = image;
        this.rating = rating;
        this.comments = comments;
    }

    public String getName(){
        return this.name;
    }

    public String getReview(){
        return this.review;
    }

    public Location getLocation(){
        return this.location;
    }

    public Image getImage(){
        return this.image;
    }

    public int getRating(){
        return this.rating;
    }

    public ArrayList getComments(){
        return this.comments;
    }

}
