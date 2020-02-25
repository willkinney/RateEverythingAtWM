package edu.cs.wm.rateeverythingatwm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.storage.FirebaseStorage;

public class SingleReviewActivity extends AppCompatActivity {

    private FirebaseStorage storage = FirebaseStorage.getInstance();


    Intent incomingIntent = getIntent();
    Bundle incomingBundle = incomingIntent.getExtras();

    LocationObject wholeReview = (LocationObject) incomingBundle.getSerializable("REVIEW");

    TextView subjectTextView = (TextView) findViewById(R.id.subjectText);
    TextView titleTextView = (TextView) findViewById(R.id.titleText);
    TextView reviewTextView = (TextView) findViewById(R.id.reviewText);
    TextView ratingView = (TextView) findViewById(R.id.ratingText);


    String subjectText = wholeReview.getSubject();
    String titleText = wholeReview.getTitle();
    String reviewText = wholeReview.getReview();
    String rating = String.valueOf(wholeReview.getRating());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_review);

        subjectTextView.setText(subjectText);
        titleTextView.setText(titleText);
        reviewTextView.setText(reviewText);
        ratingView.setText(rating);
    }


    public void viewReviews(View view) {
        Intent intent = new Intent(this, Reviews_Activity.class);
        startActivity(intent);
    }

    public void addComment(View view) {
        // TODO Add functionality to add comments to already existing reviews
    }
}
