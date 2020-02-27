package edu.cs.wm.rateeverythingatwm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.storage.FirebaseStorage;

public class SingleReviewActivity extends AppCompatActivity {

    private FirebaseStorage storage = FirebaseStorage.getInstance();

    private TextView subjectTextView;
    private TextView titleTextView;
    private TextView reviewTextView;
    private TextView ratingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_review);
        subjectTextView = findViewById(R.id.subjectText);
        titleTextView = findViewById(R.id.titleText);
        reviewTextView = findViewById(R.id.reviewText);
        ratingView = findViewById(R.id.ratingText);

        setUpReview();
    }

    private void setUpReview() {
        Bundle incomingBundle = getIntent().getExtras();
        LocationObject wholeReview = (LocationObject) incomingBundle.getSerializable("REVIEW");

        String subjectText = wholeReview.getSubject();
        String titleText = wholeReview.getTitle();
        String reviewText = wholeReview.getReview();
        String rating = String.valueOf(wholeReview.getRating());

        subjectTextView.setText(subjectText);
        titleTextView.setText(titleText);
        reviewTextView.setText(reviewText);
        ratingView.setText(rating);
    }


    public void viewReviews(View view) {
        Intent intent = new Intent(this, ReviewList.class);
        startActivity(intent);
    }

    public void addComment(View view) {
        // TODO Add functionality to add comments to already existing reviews
    }
}
