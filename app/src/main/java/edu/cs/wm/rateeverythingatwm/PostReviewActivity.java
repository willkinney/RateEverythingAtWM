package edu.cs.wm.rateeverythingatwm;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class PostReviewActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference mDocRef = db.collection("reviews");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_review);
    }

    public void postReview(View view) {
        EditText subjectEditText = (EditText) findViewById(R.id.subjectEditText);
        EditText titleEditText = (EditText) findViewById(R.id.titleEditText);
        EditText reviewEditText = (EditText) findViewById(R.id.reviewEditText);

        SeekBar ratingSeekbar = (SeekBar) findViewById(R.id.ratingSeekBar);

        String subjectText = subjectEditText.getText().toString();
        String titleText = titleEditText.getText().toString();
        String reviewText = reviewEditText.getText().toString();
        String imageURL = null;

        int rating = ratingSeekbar.getProgress();


        ArrayList<String> comments = new ArrayList<String>();

        LocationObject review = new LocationObject(titleText, subjectText, reviewText, imageURL, rating, comments);

        mDocRef.add(review)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("Yes", "DocumentSnapshot added with ID: " + documentReference.getId());

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("No", "Error adding document", e);
                    }
                });

        // After posting a review, create a new intent to bring the user to the review that they just posted
        // TODO Implement functionality for viewing the review that was just posted
        // Intent postedIntent = new Intent(this, SingleReviewActivity.class);
        Intent postedIntent = new Intent(this, MainActivity.class); // TEMPORARY brings you back to main screen until SingleReview is implemented
        startActivity(postedIntent);

    }

}
