package edu.cs.wm.rateeverythingatwm;

import android.location.Location;

import java.io.Serializable;
import java.util.ArrayList;

public class LocationObject implements Serializable, Comparable<LocationObject> {

    private String subject;
    private String title;
    private String review;
    private Boolean hasImage;
    private int rating;
    private ArrayList<String> comments;
    private String author;
    private String timestamp;
    private String reviewID;
    String tag = "LOCATION_OBJECT";


    public LocationObject(){

    }

    public LocationObject(String title, String subject, String review, Boolean hasImage, int rating,
                          String author, ArrayList<String> comments, String timestamp, String reviewID) {
        this.subject = subject;
        this.title = title;
        this.review = review;
        this.hasImage = hasImage;
        this.rating = rating;
        this.comments = comments;
        this.author = author;
        this.timestamp = timestamp;
        this.reviewID = reviewID;
    }

    public String getSubject(){
        return this.subject;
    }

    public String getTitle() { return this.title; }

    public String getReview(){
        return this.review;
    }

    public Boolean getHasImage() {
        return this.hasImage;
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

    public String getTimestamp() {
        return this.timestamp;
    }

    public String getReviewID() {
        return this.reviewID;
    }

    @Override
    public int compareTo(LocationObject o){
        String x = this.getSubject();
        String y = o.getSubject();
        return(x.toUpperCase().compareTo(y.toUpperCase()));
    }


}
