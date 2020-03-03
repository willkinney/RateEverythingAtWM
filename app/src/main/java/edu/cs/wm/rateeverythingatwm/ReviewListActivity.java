package edu.cs.wm.rateeverythingatwm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class ReviewListActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private CollectionReference mDocRef;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String title, subject, review, imageURL, author;
    private int rating;
    private ArrayList<String> comments;
    List<LocationObject> dataModelList;

    private LocationObject clickedReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_list);

        mRecyclerView = findViewById(R.id.recycler_view);
        db = FirebaseFirestore.getInstance();
        mDocRef = db.collection("reviews");
        dataModelList = new ArrayList<>();
        clickedReview = new LocationObject("Example Title", "Example Subject",
                "Example review text. More text. More and more.",
                "exampleimageurl.jpg", 6,
                "ExampleAuthor", null);

        final Context context = this;

        db.collection("reviews")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("ViewReviews", document.getId() + " => " + document.getData());
                                LocationObject obj = document.toObject(LocationObject.class);
                                dataModelList.add(obj);
                            }

                        } else {
                            Log.d("ViewReviews", "Error getting reviews: ", task.getException());
                        }
                        mRecyclerView.setHasFixedSize(true);

                        // use a linear layout manager

                        mLayoutManager = new LinearLayoutManager(context);
                        mRecyclerView.setLayoutManager(mLayoutManager);

                        // specify an adapter and pass in our data model list

                        mAdapter = new MyAdapter(dataModelList, context);
                        mRecyclerView.setAdapter(mAdapter);
                    }
                });
        // TODO how to get the specific review to pass as an intent when action button is clicked
    }

    public void goToReview(View view) {
        switch (view.getId()) {
            case R.id.showFullReviewButton:
                Intent postedIntent = new Intent(getApplicationContext(), SingleReviewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("REVIEW", clickedReview);
                postedIntent.putExtras(bundle);
                startActivity(postedIntent);
                break;
        }
    }
}





