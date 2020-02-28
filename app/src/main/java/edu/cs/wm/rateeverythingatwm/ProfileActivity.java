package edu.cs.wm.rateeverythingatwm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }

    public void profileOnClick(View view) {
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
