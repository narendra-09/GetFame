package com.example.getfame.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.getfame.Models.AccountDetails;
import com.example.getfame.R;

import java.util.ArrayList;

public class RequestsAdapter extends RecyclerView.Adapter<RequestsAdapter.MyViewHolder> {
    private ArrayList<AccountDetails> accountDetails;
    private Context context;
    private SetOnItemClickListener mSetOnItemClickListener;
    public interface SetOnItemClickListener
    {
        void onClick(int position);
        void setButton(Button button);
    }


    public void setmSetOnItemClickListener(SetOnItemClickListener mSetOnItemClickListener) {
        this.mSetOnItemClickListener = mSetOnItemClickListener;
    }

    public RequestsAdapter(ArrayList<AccountDetails> accountDetails, Context context) {
        this.accountDetails = accountDetails;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.pending_requests_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
      AccountDetails mDetails=accountDetails.get(position);
        Glide.with(context).load(mDetails.getDefaultProfile()).apply(RequestOptions.circleCropTransform()).into(holder.profileImage);
        holder.nameTextView.setText(mDetails.getUsername());
        holder.emailTextView.setText(mDetails.getEmail());
        holder.acceptButton.setOnClickListener(view -> {
            mSetOnItemClickListener.onClick(position);
            mSetOnItemClickListener.setButton(holder.acceptButton);
        });

    }

    @Override
    public int getItemCount() {
        if(accountDetails!=null)
        {
        return accountDetails.size();}
        else return 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView profileImage;
        private TextView nameTextView;
        private TextView emailTextView;
        private Button acceptButton;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImage=itemView.findViewById(R.id.pending_profile_pic);
            nameTextView=itemView.findViewById(R.id.pending_name);
            emailTextView=itemView.findViewById(R.id.pending_email);
            acceptButton=itemView.findViewById(R.id.pending_accept);
        }
    }
}
