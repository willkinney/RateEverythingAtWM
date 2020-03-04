package edu.cs.wm.rateeverythingatwm;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Calendar;

public class SingleReviewActivity extends AppCompatActivity {

    private FirebaseStorage storage = FirebaseStorage.getInstance();

    private TextView subjectTextView;
    private TextView titleTextView;
    private TextView reviewTextView;
    private TextView ratingView;
    private TextView authorTextView;
    private EditText commentEditText;
    private ArrayList<String> comments;
    private LocationObject wholeReview;
    private String ID;
    private ImageView image;


    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private CollectionReference mDocRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_review);
        subjectTextView = findViewById(R.id.subjectText);
        titleTextView = findViewById(R.id.titleText);
        reviewTextView = findViewById(R.id.reviewText);
        ratingView = findViewById(R.id.ratingText);
        authorTextView = findViewById(R.id.authorText);
        commentEditText = findViewById(R.id.commentEditText);
        image = findViewById(R.id.imageView2);

        db = FirebaseFirestore.getInstance();
        mDocRef = db.collection("reviews");
        mAuth = FirebaseAuth.getInstance();

        setUpReview();
    }

    private void setUpReview() {
        Bundle incomingBundle = getIntent().getExtras();
        wholeReview = (LocationObject) incomingBundle.getSerializable("REVIEW");
        ID = wholeReview.getReviewID();
        comments = wholeReview.getComments();

        if (wholeReview.getHasImage()) {
            // TODO put the picture on the screen
            image.setVisibility(View.VISIBLE);
            StorageReference reviewImageRef = FirebaseStorage.getInstance()
                    .getReference().child("images/" + wholeReview.getReviewID() + ".png");
            GlideApp.with(this /* context */)
                    .load(reviewImageRef)
                    .override(500, 500)
                    .into(image);
        } else {
            image.setVisibility(View.GONE);
        }

        String subjectText = wholeReview.getSubject();
        String titleText = wholeReview.getTitle();
        String reviewText = wholeReview.getReview();
        String rating = String.valueOf(wholeReview.getRating());
        String author = wholeReview.getAuthor();

        subjectTextView.setText(subjectText);
        titleTextView.setText(titleText);
        reviewTextView.setText(reviewText);
        ratingView.setText(rating + "/10");
        authorTextView.setText(author);

        LinearLayout linearLayout = findViewById(R.id.commentLayout);
        for (String comment : comments) {
            TextView commentsToAdd = new TextView(this);
            commentsToAdd.setGravity(Gravity.BOTTOM);
            commentsToAdd.setText(comment);
            linearLayout.addView(commentsToAdd);
        }
    }

    public void viewReviews(View view) {
        Intent intent = new Intent(this, ReviewListActivity.class);
        startActivity(intent);
    }

    public void addComment(View view) {
        String newComment =
                mAuth.getCurrentUser().getDisplayName().split(" ")[0] + " @ " + Calendar.getInstance().getTime().toString() + ":\n" + commentEditText.getText().toString();
        if (commentEditText.getText().toString().matches("")) {
            Toast.makeText(getApplicationContext(), "Comment cannot be empty", Toast.LENGTH_SHORT).show();
        } else {
            comments.add(newComment);
            mDocRef.document(ID).update("comments", FieldValue.arrayUnion(newComment))
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("ADD COMMENT", "DocumentSnapshot successfully updated!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("ADD COMMENT", "Error updating document", e);
                        }
                    });

            commentEditText.setText("");

            LinearLayout linearLayout = findViewById(R.id.commentLayout);
            TextView textView = new TextView(this);
            textView.setGravity(Gravity.BOTTOM);
            textView.setText(newComment);
            linearLayout.addView(textView);
            linearLayout.invalidate();
        }
    }
}
