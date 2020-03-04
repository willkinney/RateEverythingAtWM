package edu.cs.wm.rateeverythingatwm;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<LocationObject> dataModelList;
    private Context mContext;

    // View holder class whose objects represent each list item

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView cardImageView;
        public TextView titleTextView;
        public TextView idTextView;
        public TextView subTitleTextView;
        public String reviewID;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardImageView = itemView.findViewById(R.id.imageView);
            titleTextView = itemView.findViewById(R.id.card_title);
            subTitleTextView = itemView.findViewById(R.id.card_subtitle);
            idTextView = itemView.findViewById(R.id.idTextView);
        }

        public void bindData(final LocationObject dataModel, Context context) {
//            this.reviewID = dataModel.getReviewID();
            idTextView.setText(dataModel.getReviewID());
            StorageReference reviewImageRef = FirebaseStorage.getInstance()
                    .getReference().child("images/" + dataModel.getReviewID() + ".png");

            if (/*dataModel.getHasImage()*/ false) {
                final long FIFTY_MEGABYTES = 1024 * 1024 * 50;
                reviewImageRef.getBytes(FIFTY_MEGABYTES).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] reviewImageBytes) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(reviewImageBytes, 0, reviewImageBytes.length);
                        cardImageView.setImageBitmap(bitmap);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Log.v("DownloadImage", "Error when fetching " + dataModel.getReviewID() + ": " + exception);
                        cardImageView.setVisibility(View.GONE);
                    }
                });
            } else {
                cardImageView.setVisibility(View.GONE);
            }

            if (dataModel.getTitle() == "") {
                titleTextView.setText("(no title)");
            } else {
                titleTextView.setText(dataModel.getTitle());
            }

            if (dataModel.getSubject() == "") {
                subTitleTextView.setText("(no subject)");
            } else {
                subTitleTextView.setText(dataModel.getSubject());
            }
        }
    }

    public MyAdapter(List<LocationObject> modelList, Context context) {
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
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Bind data for the item at position

        holder.bindData(dataModelList.get(position), mContext);
    }

    @Override
    public int getItemCount() {
        // Return the total number of items
        return dataModelList.size();
    }

}
