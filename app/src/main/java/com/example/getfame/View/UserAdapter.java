package com.example.getfame.View;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.getfame.Models.AccountDetails;
import com.example.getfame.R;


import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {
    private ArrayList<AccountDetails> myArrayList;
    private Context context;
    private OnItemClickListener mListener;
    private int requestStatus;
    public interface OnItemClickListener
    {
        void onItemClick(int position);
    }

    public void setRequestStatus(int requestStatus) {
        this.requestStatus = requestStatus;
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mListener=listener;
    }
    public UserAdapter(ArrayList<AccountDetails> myArrayList, Context context) {
        this.myArrayList = myArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.searched_user,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        AccountDetails user=myArrayList.get(position);
        Glide.with(context).load(user.getDefaultProfile()).apply(RequestOptions.circleCropTransform()).into(holder.profileImage);
        holder.usernameTextView.setText(user.getUsername());
        holder.emailTextView.setText(user.getEmail());
        if(requestStatus==100)
        {
            holder.addButton.setEnabled(false);
            holder.addButton.setBackground(ContextCompat.getDrawable(context,R.drawable.button_disable));
            holder.addButton.setText("Pending");
        }
        else if(requestStatus==200) {
            holder.addButton.setEnabled(true);
            holder.addButton.setBackground(ContextCompat.getDrawable(context,R.drawable.button_style));
            holder.addButton.setText("Add");
        }
        else if(requestStatus==300)
        {
            holder.addButton.setEnabled(false);
            holder.addButton.setBackground(ContextCompat.getDrawable(context,R.drawable.button_disable));
            holder.addButton.setText("Requested");
        }
        else if(requestStatus==1000)
        {
            holder.addButton.setEnabled(false);
            holder.addButton.setBackground(ContextCompat.getDrawable(context,R.drawable.button_disable));
            holder.addButton.setText("Friends");
        }
        holder.addButton.setOnClickListener(view -> {
            mListener.onItemClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return myArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView profileImage;
        private TextView usernameTextView;
        private TextView emailTextView;
        private Button addButton;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImage=itemView.findViewById(R.id.search_profile_pic);
            usernameTextView=itemView.findViewById(R.id.search_user_name);
            emailTextView=itemView.findViewById(R.id.search_email);
            addButton=itemView.findViewById(R.id.search_add);
        }
    }
}
