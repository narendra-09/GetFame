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

public class ShowFriendsAdapter extends RecyclerView.Adapter<ShowFriendsAdapter.MyViewHolder> {
    private ArrayList<AccountDetails> mAccountDetails;
    private Context context;

    public ShowFriendsAdapter(ArrayList<AccountDetails> mAccountDetails, Context context) {
        this.mAccountDetails = mAccountDetails;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.show_friends_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        AccountDetails details=mAccountDetails.get(position);
        Glide.with(context).load(details.getDefaultProfile()).apply(RequestOptions.circleCropTransform()).into(holder.friendsProfile);
        holder.friendsName.setText(details.getUsername());
        holder.friendsEmail.setText(details.getEmail());
    }

    @Override
    public int getItemCount() {
        if(mAccountDetails.size()!=0)
        {
            return mAccountDetails.size();
        }
        else return 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView friendsProfile;
        private TextView friendsName;
        private TextView friendsEmail;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            friendsProfile=itemView.findViewById(R.id.friends_profile_pic);
            friendsName=itemView.findViewById(R.id.friends_name);
            friendsEmail=itemView.findViewById(R.id.friends_email);
        }
    }
}
