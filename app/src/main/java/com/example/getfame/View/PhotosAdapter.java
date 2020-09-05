package com.example.getfame.View;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.getfame.Models.ImageDetails;
import com.example.getfame.R;

import java.util.ArrayList;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.MyViewHolder> {
    private ArrayList<ImageDetails> imageDetails;
    private Context context;
    public PhotosAdapter(Context context,ArrayList<ImageDetails> imageDetails) {
        this.context=context;
        this.imageDetails=imageDetails;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.photos_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.d("adapter",""+imageDetails.get(position).getImageName());
        Glide.with(context).load(imageDetails.get(position).getImageUrl()).centerCrop().into(holder.photosImageView);
    }

    @Override
    public int getItemCount() {
        return imageDetails.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView photosImageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            photosImageView=itemView.findViewById(R.id.photos);
        }
    }
}
