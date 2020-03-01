package edu.cs.wm.rateeverythingatwm;

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
    private String author;
    String tag = "LOCATION_OBJECT";


    public LocationObject(){

    }

    public LocationObject(String title, String subject, String review, String imageURL, int rating,
                          String author, ArrayList<String> comments) {
        this.subject = subject;
        this.title = title;
        this.review = review;
        this.imageURL = imageURL;
        this.rating = rating;
        this.comments = comments;
        this.author = author;
    }

    public String getSubject(){
        return this.subject;
    }

    public String getTitle() { return this.title; }

    public String getReview(){
        return this.review;
    }

    public String getImageURL() {
        return this.imageURL;
    }

    public int getRating(){
        return this.rating;
    }

    public String getAuthor() {
        return this.author;
    }

    public ArrayList<String> getComments(){
        return this.comments;
    }

}
