package edu.cs.wm.rateeverythingatwm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ReviewCardAdapter extends RecyclerView.Adapter<ReviewCardAdapter.MyViewHolder> {

    private List<LocationObject> dataModelList;
    private Context mContext;

    // View holder class whose objects represent each list item

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView cardImageView;
        public TextView titleTextView;
        public TextView ratingTextView;
        public TextView subTitleTextView;
        public ImageView hasPhotoImageView;
        public Button showFullReviewButton;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            cardImageView = itemView.findViewById(R.id.imageView);
            titleTextView = itemView.findViewById(R.id.card_title);
            subTitleTextView = itemView.findViewById(R.id.card_subtitle);
            ratingTextView = itemView.findViewById(R.id.card_rating);
            hasPhotoImageView = itemView.findViewById(R.id.hasPhotoImageView);
            showFullReviewButton = itemView.findViewById(R.id.showFullReviewButton);
        }

        public void bindData(final LocationObject dataModel, Context context) {
//            StorageReference reviewImageRef = FirebaseStorage.getInstance()
//                    .getReference().child("images/" + dataModel.getReviewID() + ".png");

            if (dataModel.getHasImage()) {
                hasPhotoImageView.setVisibility(View.VISIBLE);
            } else {
                hasPhotoImageView.setVisibility(View.GONE);
            }

            titleTextView.setText(dataModel.getSubject());
            subTitleTextView.setText(dataModel.getTitle());
            ratingTextView.setText(dataModel.getRating() + "/10");
        }
    }

    public ReviewCardAdapter(List<LocationObject> modelList, Context context) {
        dataModelList = modelList;
        mContext = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate out card list item

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_reviews_, parent, false);
        // Return a new view holder

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        // Bind data for the item at position
        holder.bindData(dataModelList.get(position), mContext);

        holder.showFullReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reviewIDString = dataModelList.get(position).getReviewID();
                DocumentReference docRef = FirebaseFirestore.getInstance().collection("reviews").document(reviewIDString);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                LocationObject reviewToBundle = document.toObject(LocationObject.class);
                                Log.d("FetchIndivReview", "DocumentSnapshot data: " + document.getData());
                                Intent showFullReviewIntent = new Intent(mContext, SingleReviewActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("REVIEW", reviewToBundle);
                                showFullReviewIntent.putExtras(bundle);
                                mContext.startActivity(showFullReviewIntent);
                            } else {
                                Log.d("FetchIndivReview", "No such document");
                            }
                        } else {
                            Log.d("FetchIndivReview", "get failed with ", task.getException());
                        }
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        // Return the total number of items
        return dataModelList.size();
    }

}
