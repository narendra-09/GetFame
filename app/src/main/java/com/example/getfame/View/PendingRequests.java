package com.example.getfame.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.getfame.Models.AccountDetails;
import com.example.getfame.R;
import com.example.getfame.ViewModels.PendingRequestsViewModel;
import com.example.getfame.databinding.FragmentPendingRequestsBinding;

import java.util.ArrayList;
import java.util.Objects;

public class PendingRequests extends Fragment {
    private FragmentPendingRequestsBinding pendingRequestsBinding;
    private PendingRequestsViewModel requestsViewModel;
    private AppCompatActivity appCompatActivity;
    private RequestsAdapter requestsAdapter;
    public PendingRequests() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        pendingRequestsBinding=FragmentPendingRequestsBinding.inflate(inflater,container,false);
        View view=pendingRequestsBinding.getRoot();
        appCompatActivity=(AppCompatActivity)getActivity();
        assert appCompatActivity != null;
        requestsViewModel=new PendingRequestsViewModel(appCompatActivity.getApplication());
        requestsViewModel.getUids();
        requestsViewModel.getArrayListMutableLiveData().observe(appCompatActivity, accountDetails -> {
            pendingRequestsBinding.recyclerView.setLayoutManager(new LinearLayoutManager(appCompatActivity));
            requestsAdapter=new RequestsAdapter(accountDetails,appCompatActivity);
            requestsAdapter.setmSetOnItemClickListener(new RequestsAdapter.SetOnItemClickListener() {
                @Override
                public void onClick(int position) {
                    requestsViewModel.getDeletedUserUid(accountDetails.get(position).getEmail());
                }

                @Override
                public void setButton(Button button) {
                  button.setText("Accepted");
                  button.setEnabled(false);
                  button.setBackground(ContextCompat.getDrawable(appCompatActivity,R.drawable.button_disable));
                }
            });
            pendingRequestsBinding.recyclerView.setAdapter(requestsAdapter);
        });
        return view;
    }
}