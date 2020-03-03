package edu.cs.wm.rateeverythingatwm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<LocationObject> dataModelList;
    private Context mContext;
//    private StorageReference storageReference = FirebaseStorage.getInstance().getReference("images/");


    // View holder class whose objects represent each list item

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView cardImageView;
        public TextView titleTextView;
        public TextView subTitleTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardImageView = itemView.findViewById(R.id.imageView);
            titleTextView = itemView.findViewById(R.id.card_title);
            subTitleTextView = itemView.findViewById(R.id.card_subtitle);
        }

        public void bindData(LocationObject dataModel, Context context) {
//            cardImageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.list_image));
            StorageReference reviewImageRef = FirebaseStorage.getInstance()
                    .getReference().child("images/" + dataModel.getReviewID() + ".png");

//            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
//            cardImageView.setImageBitmap(bitmap);
            Glide.with(context /* context */)
                    .load(reviewImageRef)
                    .into(cardImageView);
            titleTextView.setText(dataModel.getTitle());
            String x = dataModel.getTitle();
            subTitleTextView.setText(dataModel.getSubject());
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
