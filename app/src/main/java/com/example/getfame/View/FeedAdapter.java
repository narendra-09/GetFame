package com.example.getfame.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.getfame.Models.FeedDetails;
import com.example.getfame.R;

import java.util.ArrayList;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.MyViewHolder> {
    private ArrayList<FeedDetails> feedDetails;
    private Context context;

    public FeedAdapter(ArrayList<FeedDetails> feedDetails, Context context) {
        this.feedDetails = feedDetails;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.feed_photos_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
       FeedDetails mFeedDetails=feedDetails.get(position);
        Glide.with(context).load(mFeedDetails.getProfile_pic()).apply(RequestOptions.circleCropTransform()).into(holder.profileImage);
        Glide.with(context).load(mFeedDetails.getActual_image()).centerCrop().into(holder.actualImage);
        holder.nameTextView.setText(mFeedDetails.getName());
    }

    @Override
    public int getItemCount() {
        if(feedDetails.size()!=0)
        {
            return feedDetails.size();
        }
        else return 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView profileImage;
        private ImageView actualImage;
        private TextView nameTextView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImage=itemView.findViewById(R.id.feed_profile_image);
            actualImage=itemView.findViewById(R.id.feed_actual_image);
            nameTextView=itemView.findViewById(R.id.feed_name);
        }
    }
}
