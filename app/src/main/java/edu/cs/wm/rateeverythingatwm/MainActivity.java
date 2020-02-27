package edu.cs.wm.rateeverythingatwm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private DocumentReference mDocRef = FirebaseFirestore.getInstance().document("sampleData/reviews");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void createReview(View view) {
        Intent intent = new Intent(this, PostReviewActivity.class);
        startActivity(intent);
    }

    public void viewReviews(View view) {
        Intent intent = new Intent(this, ViewReviewsActivity.class);
        startActivity(intent);
    }
}
