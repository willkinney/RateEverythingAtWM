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

    public void launcherOnClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.viewReviewsButton:
                intent = new Intent(this, ReviewListActivity.class);
                break;

            case R.id.newReviewButton:
                intent = new Intent(this, PostReviewActivity.class);
                break;
        }
        if (intent != null)
            startActivity(intent);
    }
}
