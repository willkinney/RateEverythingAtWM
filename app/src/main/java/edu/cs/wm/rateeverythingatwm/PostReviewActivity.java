package edu.cs.wm.rateeverythingatwm;

import android.content.Intent;
import android.location.Location;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PostReviewActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference mDocRef = db.collection("reviews");
    private ImageView imageView;
    private Button picButton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_review);
        imageView = (ImageView) findViewById(R.id.ImageView01);
        picButton = (Button) findViewById(R.id.camerabutton);
        picButton.setOnClickListener(this);

    }

    public void postReview(View view) {
        EditText subjectEditText = (EditText) findViewById(R.id.subjectEditText);
        EditText titleEditText = (EditText) findViewById(R.id.titleEditText);
        EditText reviewEditText = (EditText) findViewById(R.id.reviewEditText);

        SeekBar ratingSeekbar = (SeekBar) findViewById(R.id.ratingSeekBar);

        String subjectText = subjectEditText.getText().toString();
        String titleText = titleEditText.getText().toString();
        String reviewText = reviewEditText.getText().toString();

        int rating = 5;

        String placeholder = null;

        ArrayList<String> comments = new ArrayList<String>();
//        comments.add("Comment1");
//        comments.add("Comment2");

        LocationObject review = new LocationObject(titleText, subjectText, reviewText, placeholder, rating, comments);

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
    }

    public void takePic(View view){
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePicture, 0);
    }

    public void pickPic(View view){
        Intent getPicture = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(getPicture, 1);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.camerabutton:
                takePic(view);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    imageView.setImageURI(selectedImage);
                }

                break;
            case 1:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    imageView.setImageURI(selectedImage);
                }
                break;
        }
    }

}
