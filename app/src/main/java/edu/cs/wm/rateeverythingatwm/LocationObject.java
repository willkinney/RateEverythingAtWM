package edu.cs.wm.rateeverythingatwm;

import android.media.Image;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

public class LocationObject implements Serializable {

    private String subject;
    private String title;
    private String review;
    private String imageURL;
    private int rating;
    private ArrayList<String> comments;
    String tag = "LOCATION_OBJECT";


    public LocationObject(){
        Log.v(tag, "Location Object");
    }

    public LocationObject(String title, String subject, String review, String imageURL, int rating,
                          ArrayList<String> comments){
        this.subject = subject;
        this.title = title;
        this.review = review;
        this.imageURL = imageURL;
        this.rating = rating;
        this.comments = comments;
    }

    public String getSubject(){
        return this.subject;
    }

    public String getTitle() { return this.title; }

    public String getReview(){
        return this.review;
    }

    public String getImage() {
        return this.imageURL;
    }

    public int getRating(){
        return this.rating;
    }

    public ArrayList<String> getComments(){
        return this.comments;
    }

}
