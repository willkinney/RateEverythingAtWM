package edu.cs.wm.rateeverythingatwm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class LauncherActivity extends AppCompatActivity {

    private DocumentReference mDocRef = FirebaseFirestore.getInstance().document("sampleData/reviews");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
    }

    public void createReview(View view) {
        Intent intent = new Intent(this, PostReviewActivity.class);
        startActivity(intent);
    }

    public void viewReviews(View view) {
        Intent intent = new Intent(this, ReviewList.class);
        startActivity(intent);
    }
}
