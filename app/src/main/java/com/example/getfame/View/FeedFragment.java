package com.example.getfame.View;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.getfame.Models.FeedDetails;
import com.example.getfame.R;
import com.example.getfame.ViewModels.FeedViewModel;
import com.example.getfame.databinding.FragmentFeedBinding;

import java.util.ArrayList;
import java.util.Objects;

public class FeedFragment extends Fragment {
private FragmentFeedBinding feedBinding;
private FeedViewModel feedViewModel;
private AppCompatActivity appCompatActivity;
private FeedAdapter feedAdapter;
    public FeedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        feedBinding=FragmentFeedBinding.inflate(inflater,container,false);
        View view=feedBinding.getRoot();
        appCompatActivity=(AppCompatActivity)getActivity();
        assert appCompatActivity != null;
        feedViewModel=new FeedViewModel(appCompatActivity.getApplication());
        appCompatActivity.setSupportActionBar(feedBinding.toolbar);
        Objects.requireNonNull(appCompatActivity.getSupportActionBar()).setDisplayShowCustomEnabled(true);
        appCompatActivity.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        appCompatActivity.getSupportActionBar().setCustomView(R.layout.toolbar_layout);
        View view1=appCompatActivity.getSupportActionBar().getCustomView();
        ImageView imageView=view1.findViewById(R.id.toolbar_image);
        imageView.setOnClickListener(view2 -> {
            startActivity(new Intent(appCompatActivity,FriendsActivity.class));
        });
        feedViewModel.getUids();
        feedViewModel.getMutData().observe(appCompatActivity, feedDetails -> {
            feedBinding.recyclerView.setLayoutManager(new LinearLayoutManager(appCompatActivity));
            feedAdapter=new FeedAdapter(feedDetails,appCompatActivity);
            feedBinding.recyclerView.setAdapter(feedAdapter);
        });
        return view;
    }
}