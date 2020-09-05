package com.example.getfame.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.getfame.Models.AccountDetails;
import com.example.getfame.ViewModels.SearchViewModel;
import com.example.getfame.databinding.FragmentSearchBinding;

import java.util.ArrayList;

public class SearchFragment extends Fragment {
private FragmentSearchBinding searchBinding;
private SearchViewModel searchViewModel;
private AppCompatActivity appCompatActivity;
private UserAdapter userAdapter;
private ArrayList<AccountDetails> myArrayList;
public final Integer mIntegerOne= 100;
public final Integer mIntegerTwo=200;
public final Integer mIntegerThree=300;
public final Integer friendsAlready=1000;
    public SearchFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        searchBinding=FragmentSearchBinding.inflate(inflater,container,false);
        View view=searchBinding.getRoot();
        myArrayList=new ArrayList<>();
        appCompatActivity=(AppCompatActivity)getActivity();
        assert appCompatActivity != null;
        searchViewModel=new SearchViewModel(appCompatActivity.getApplication());
        userAdapter=new UserAdapter(myArrayList,appCompatActivity);
        buildRecyclerView(userAdapter);
        searchBinding.searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                myArrayList.clear();
                userAdapter.notifyDataSetChanged();
                searchViewModel.getSearchedUser(searchBinding.searchBox.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        searchViewModel.getmAccountDetails().observe(appCompatActivity, accountDetails -> {

            if(accountDetails==null)
            {
                myArrayList.clear();
                userAdapter.notifyDataSetChanged();
            }
            else
            {
                searchViewModel.getUserUID().observe(appCompatActivity, mUid -> {
                    searchViewModel.checkIfAlreadySent(mUid);
                    searchViewModel.getAlreadySent().observe(appCompatActivity, integer -> {
                        if(integer.equals(mIntegerOne) && searchBinding.searchBox.getText().toString().equals(accountDetails.getEmail()))
                        {
                            updateList(accountDetails);
                            userAdapter.setRequestStatus(mIntegerOne);
                            userAdapter.notifyDataSetChanged();
                        }
                        else if(integer.equals(mIntegerTwo) && searchBinding.searchBox.getText().toString().equals(accountDetails.getEmail()))
                        {
                            updateList(accountDetails);
                            userAdapter.setRequestStatus(mIntegerTwo);
                            userAdapter.notifyDataSetChanged();

                            userAdapter.setOnItemClickListener(position -> {
                                searchViewModel.addToSentRequests(mUid);
                                searchViewModel.addToPendingRequestOfUser();
                            });
                        }
                        else if(integer.equals(mIntegerThree) && searchBinding.searchBox.getText().toString().equals(accountDetails.getEmail()))
                        {
                            updateList(accountDetails);
                            userAdapter.setRequestStatus(mIntegerThree);
                            userAdapter.notifyDataSetChanged();
                        }
                        else if(integer.equals(friendsAlready) && searchBinding.searchBox.getText().toString().equals(accountDetails.getEmail()))
                        {
                            updateList(accountDetails);
                            userAdapter.setRequestStatus(friendsAlready);
                            userAdapter.notifyDataSetChanged();
                        }
                    });
                });

            }
        });
        return view;
    }
    private void updateList(AccountDetails accountDetails)
    {
        myArrayList.clear();
        myArrayList.add(accountDetails);
    }
    private void buildRecyclerView(UserAdapter adapter)
    {
        searchBinding.recyclerView.setLayoutManager(new LinearLayoutManager(appCompatActivity));
        searchBinding.recyclerView.setAdapter(adapter);
    }
}