package edu.cs.wm.rateeverythingatwm;

import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.Toast;

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

import java.io.ByteArrayOutputStream;
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
    private byte[] selectedImageBytes = null;
    private Boolean hasImage = false;

    private static final int REQUEST_GALLERY = 0;
    private static final int REQUEST_CAMERA = 1;
    private static int fileType = 0;
    private static final int UPLOAD_BITMAP = 2;
    private static final int UPLOAD_URI = 3;

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

        StorageReference ref = null;

        switch (fileType) {
            case UPLOAD_URI:
                ref = mStorageRef.child(freshReviewID + ".png");
                if (selectedImage != null) {
                    ref.putFile(selectedImage)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Log.v("ImageUpload", "Successfully uploaded image with as URI");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    Log.v("ImageUpload", "Failed to upload image");
                                }
                            });
                } else {
                    Log.v("ImageUpload", "selectedImage was null. Did not upload.");
                }
                break;

            case UPLOAD_BITMAP:
                ref = mStorageRef.child(freshReviewID + ".png");
                if (selectedImageBytes != null) {
                    UploadTask uploadTask = ref.putBytes(selectedImageBytes);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Log.v("ImageUpload", "Failed to upload image");
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Log.v("ImageUpload", "Successfully uploaded image with from bitmap");
                            // ...
                        }
                    });
                } else {
                    Log.v("ImageUpload", "selectedImage was null. Did not upload.");
                }
                break;


        }
//        StorageReference ref = mStorageRef.child(freshReviewID + ".png");
//        if (selectedImage != null) {
//            ref.putFile(selectedImage)
//                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            Log.v("ImageUpload", "Successfully uploaded image with URL");
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception exception) {
//                            Log.v("ImageUpload", "Failed to upload image");
//                        }
//                    });
//        }
//        else {
//            Log.v("ImageUpload", "selectedImage was null. Did not upload.");
//        }

        if (subjectText.matches("")) {
            Toast.makeText(getApplicationContext(), "Subject cannot be empty", Toast.LENGTH_SHORT).show();
        }
        if (titleText.matches("")) {
            Toast.makeText(getApplicationContext(), "Title cannot be empty", Toast.LENGTH_SHORT).show();
        }
        if (reviewText.matches("")) {
            Toast.makeText(getApplicationContext(), "Review body cannot be empty", Toast.LENGTH_SHORT).show();
        }

        if (!subjectText.matches("") && !titleText.matches("") && !reviewText.matches("")) {
            LocationObject review = new LocationObject(titleText, subjectText, reviewText, hasImage,
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

//            Intent postedIntent = new Intent(getApplicationContext(), SingleReviewActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("REVIEW", review);
//            postedIntent.putExtras(bundle);
//
//            startActivity(postedIntent);
//
//            finish();
            Intent backToListIntent = new Intent(getApplicationContext(), ReviewListActivity.class);
            Toast.makeText(getApplicationContext(), "Review posted!", Toast.LENGTH_LONG).show();
            startActivity(backToListIntent);
            finish();
        }
    }

    public void takePic(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, selectedImage);
        startActivityForResult(takePictureIntent, REQUEST_CAMERA);
//        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(takePicture, 0);
    }

    public void pickPic(View view) {
        Intent getPicture = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(getPicture, REQUEST_GALLERY);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (resultCode == RESULT_OK) {
                    hasImage = true;
                    Bundle extras = imageReturnedIntent.getExtras();
                    Bitmap takenPictureAsBitmap = (Bitmap) extras.get("data");
                    thumbnailImageView.setImageBitmap(takenPictureAsBitmap);
                    selectedImage = imageReturnedIntent.getData();

                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    takenPictureAsBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                    selectedImageBytes = bytes.toByteArray();
                    fileType = UPLOAD_BITMAP;

                    photoLabelTextView.setVisibility(View.VISIBLE);
                    thumbnailImageView.setVisibility(View.VISIBLE);
                }
                break;
            case REQUEST_GALLERY:
                hasImage = true;
                selectedImage = imageReturnedIntent.getData();
                thumbnailImageView.setImageURI(selectedImage);
                photoLabelTextView.setVisibility(View.VISIBLE);
                thumbnailImageView.setVisibility(View.VISIBLE);
                fileType = UPLOAD_URI;
                break;
            default:
                Log.v("TakingPhoto", "Unrecognized request code. Doing nothing.");
        }
    }

}
