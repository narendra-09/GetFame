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
import com.example.getfame.Models.AccountDetails;
import com.example.getfame.R;

import java.util.ArrayList;

public class SentAdapter extends RecyclerView.Adapter<SentAdapter.MyViewHolder> {
    private ArrayList<AccountDetails> accountDetails;
    private Context context;

    public SentAdapter(ArrayList<AccountDetails> accountDetails, Context context) {
        this.accountDetails = accountDetails;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.sent_requests_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        AccountDetails mDetails=accountDetails.get(position);
        Glide.with(context).load(mDetails.getDefaultProfile()).apply(RequestOptions.circleCropTransform()).into(holder.profileImage);
        holder.nameTextView.setText(mDetails.getUsername());
        holder.emailTextView.setText(mDetails.getEmail());
    }

    @Override
    public int getItemCount() {
        return accountDetails.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView profileImage;
        private TextView nameTextView;
        private TextView emailTextView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImage=itemView.findViewById(R.id.sent_profile_pic);
            nameTextView=itemView.findViewById(R.id.sent_name);
            emailTextView=itemView.findViewById(R.id.sent_email);
        }
    }
}
