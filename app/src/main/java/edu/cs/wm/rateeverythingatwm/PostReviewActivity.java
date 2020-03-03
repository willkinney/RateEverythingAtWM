package edu.cs.wm.rateeverythingatwm;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

public class PostReviewActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseFirestore db;
    private CollectionReference mDocRef;
    private StorageReference mStorageRef;

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    private ImageView thumbnailImageView;
    private Button picButton;
    private Button chooseButton;
    private TextView photoLabelTextView;
    private EditText subjectEditText;
    private EditText titleEditText;
    private EditText reviewEditText;
    private SeekBar ratingSeekbar;

    private Uri selectedImage = null;
    private String imageURL = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_review);

        db = FirebaseFirestore.getInstance();
        mDocRef = db.collection("reviews");

        mStorageRef = FirebaseStorage.getInstance().getReference("images/");

        thumbnailImageView = findViewById(R.id.ImageView01);
        picButton = findViewById(R.id.takePhotoButton);
        chooseButton = findViewById(R.id.chooseFromGalleryButton);
        photoLabelTextView = findViewById(R.id.photoLabel);
        subjectEditText = findViewById(R.id.subjectEditText);
        titleEditText = findViewById(R.id.titleEditText);
        reviewEditText = findViewById(R.id.reviewEditText);
        ratingSeekbar = findViewById(R.id.ratingSeekBar);

        chooseButton.setOnClickListener(this);
        picButton.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        currentUser = mAuth.getCurrentUser();
    }


    public void postReview(View view) {
        String subjectText = subjectEditText.getText().toString();
        String titleText = titleEditText.getText().toString();
        String reviewText = reviewEditText.getText().toString();

        int rating = ratingSeekbar.getProgress();

        ArrayList<String> comments = new ArrayList<String>();
        final String freshReviewID = UUID.randomUUID().toString();

        StorageReference ref = mStorageRef.child(freshReviewID + ".png");
        if (selectedImage != null) {
            ref.putFile(selectedImage)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            imageURL = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                            Log.v("ImageUpload", "Successfully uploaded image with URL");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Log.v("ImageUpload", "Failed to upload image");
                        }
                    });
        }



        LocationObject review = new LocationObject(titleText, subjectText, reviewText, imageURL,
                rating, currentUser.getDisplayName().split(" ")[0], comments,
                Calendar.getInstance().getTime().toString(), freshReviewID);

        mDocRef.document(freshReviewID).set(review)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("ReviewUpload", "Review successfully uploaded with id: "
                                + freshReviewID);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("ReviewUpload", "Error uploading review", e);
                    }
                });

        Intent postedIntent = new Intent(getApplicationContext(), SingleReviewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("REVIEW", review);
        postedIntent.putExtras(bundle);

        startActivity(postedIntent);

        finish();

    }

    public void takePic(View view) {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePicture, 0);
    }

    public void pickPic(View view) {
        Intent getPicture = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(getPicture, 1);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.takePhotoButton:
                takePic(view);
                break;

            case R.id.chooseFromGalleryButton:
                pickPic(view);
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    selectedImage = imageReturnedIntent.getData();
                    thumbnailImageView.setImageURI(selectedImage);
                    photoLabelTextView.setVisibility(View.VISIBLE);
                    thumbnailImageView.setVisibility(View.VISIBLE);
                }
                break;
            case 1:
                if (resultCode == RESULT_OK) {
                    selectedImage = imageReturnedIntent.getData();
                    thumbnailImageView.setImageURI(selectedImage);
                    photoLabelTextView.setVisibility(View.VISIBLE);
                    thumbnailImageView.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

}
