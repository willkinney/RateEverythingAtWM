package edu.cs.wm.rateeverythingatwm;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.storage.FirebaseStorage;

public class SingleReviewActivity extends AppCompatActivity {

    private FirebaseStorage storage = FirebaseStorage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_review);
    }

    Intent incomingIntent = getIntent();
    Bundle incomingBundle = incomingIntent.getExtras();

    LocationObject wholeReview = (LocationObject) incomingBundle.getSerializable("REVIEW");


}
