package com.example.getfame.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.getfame.Models.AccountDetails;
import com.example.getfame.Repository.ShowFriendsRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ShowFriendsViewModel extends AndroidViewModel {
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;
    private ShowFriendsRepository showFriendsRepository;
    public ShowFriendsViewModel(@NonNull Application application) {
        super(application);
        firebaseAuth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
        showFriendsRepository=new ShowFriendsRepository();
        showFriendsRepository.getFriendsUids();
    }
    public LiveData<ArrayList<AccountDetails>> getFriends()
    {
        return showFriendsRepository.getMutableLiveData();
    }
}
