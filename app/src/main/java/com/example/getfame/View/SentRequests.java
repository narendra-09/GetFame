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
import com.example.getfame.ViewModels.SentRequestsViewModel;
import com.example.getfame.databinding.FragmentSentRequestsBinding;

import java.util.ArrayList;

public class SentRequests extends Fragment {
    private FragmentSentRequestsBinding sentRequestsBinding;
    private SentRequestsViewModel sentRequestsViewModel;
    private AppCompatActivity appCompatActivity;
    private SentAdapter sentAdapter;
    public SentRequests() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        sentRequestsBinding=FragmentSentRequestsBinding.inflate(inflater,container,false);
        View view=sentRequestsBinding.getRoot();
        appCompatActivity=(AppCompatActivity)getActivity();
        assert appCompatActivity != null;
        sentRequestsViewModel=new SentRequestsViewModel(appCompatActivity.getApplication());
        sentRequestsViewModel.getSentRequests();
        sentRequestsViewModel.getArrayListMutableLiveData().observe(appCompatActivity, accountDetails -> {
            sentRequestsBinding.recyclerView.setLayoutManager(new LinearLayoutManager(appCompatActivity));
            sentAdapter=new SentAdapter(accountDetails,appCompatActivity);
            sentRequestsBinding.recyclerView.setAdapter(sentAdapter);
        });
        return view;
    }
}