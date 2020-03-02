package edu.cs.wm.rateeverythingatwm;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_list);

        mRecyclerView = findViewById(R.id.recycler_view);
        db = FirebaseFirestore.getInstance();
        mDocRef = db.collection("reviews");
        dataModelList = new ArrayList<>();

        final Context context = this;

        db.collection("reviews")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("A", document.getId() + " => " + document.getData());
                                LocationObject obj = document.toObject(LocationObject.class);
                                dataModelList.add(obj);
                            }

                        } else {
                            Log.d("A", "Error getting documents: ", task.getException());
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


        //List<LocationObject> dataModelList = new ArrayList<>();
        //for (int i = 1; i <= 20; ++i) {
        //    dataModelList.add(new LocationObject(title, subject, review, imageURL, rating, author, comments));
        //}

        // use this setting to improve performance if you know that changes

        // in content do not change the layout size of the RecyclerView
/*
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter and pass in our data model list

        mAdapter = new MyAdapter(dataModelList, this);
        mRecyclerView.setAdapter(mAdapter);

 */
    }
}





