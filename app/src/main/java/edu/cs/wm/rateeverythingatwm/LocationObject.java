package edu.cs.wm.rateeverythingatwm;

import android.media.Image;
import android.util.Log;

import java.util.ArrayList;

public class LocationObject {

    private String subject;
    private String title;
    private String review;
    private Image image;
    private int rating;
    private ArrayList<String> comments;
    String tag = "LOCATION OBJECT";


    public LocationObject(){
        Log.v(tag, "Location Object");
    }

    public LocationObject(String title, String subject, String review, Image image, int rating,
                          ArrayList<String> comments, String category){
        this.subject = subject;
        this.title = title;
        this.review = review;
        this.image = image;
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

    public Image getImage(){
        return this.image;
    }

    public int getRating(){
        return this.rating;
    }

    public ArrayList<String> getComments(){
        return this.comments;
    }

}
