package com.example.getfame.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.getfame.Models.AccountDetails;
import com.example.getfame.R;
import com.example.getfame.ViewModels.ShowFriendsViewModel;
import com.example.getfame.databinding.FragmentShowFriendsBinding;

import java.util.ArrayList;

public class ShowFriends extends Fragment {
    private FragmentShowFriendsBinding showFriendsBinding;
    private AppCompatActivity appCompatActivity;
    private ShowFriendsViewModel showFriendsViewModel;
    private ShowFriendsAdapter showFriendsAdapter;
    public ShowFriends() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        showFriendsBinding=FragmentShowFriendsBinding.inflate(inflater,container,false);
        View view=showFriendsBinding.getRoot();
        appCompatActivity=(AppCompatActivity)getActivity();
        assert appCompatActivity != null;
        showFriendsViewModel=new ShowFriendsViewModel(appCompatActivity.getApplication());
        showFriendsViewModel.getFriends().observe(appCompatActivity, accountDetails -> {
            showFriendsBinding.recyclerView.setLayoutManager(new LinearLayoutManager(appCompatActivity));
            showFriendsAdapter=new ShowFriendsAdapter(accountDetails,appCompatActivity);
            showFriendsBinding.recyclerView.setAdapter(showFriendsAdapter);
        });
        return view;
    }
}