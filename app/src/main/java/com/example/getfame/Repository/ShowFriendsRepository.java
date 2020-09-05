package com.example.getfame.Repository;

import android.util.Log;


import androidx.lifecycle.MutableLiveData;

import com.example.getfame.Models.AccountDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class ShowFriendsRepository {
    private FirebaseFirestore firestore;
    private FirebaseAuth firebaseAuth;
    private MutableLiveData<ArrayList<AccountDetails>> mutableLiveData;
    private ArrayList<String> mUids;

    public ShowFriendsRepository() {
        mutableLiveData=new MutableLiveData<>();
    }

    public MutableLiveData<ArrayList<AccountDetails>> getMutableLiveData() {
        return mutableLiveData;
    }

    public void getFriendsUids()
    {
        firebaseAuth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
        mUids=new ArrayList<>();
        ArrayList<AccountDetails> arrayList = new ArrayList<>();
        firestore.collection("Accounts")
                 .document(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid())
                 .collection("Friends")
                 .get()
                 .addOnCompleteListener(task -> {
                     if(task.isSuccessful())
                     {
                         for(QueryDocumentSnapshot snapshot: Objects.requireNonNull(task.getResult()))
                         {
                             if(!snapshot.getId().equals(firebaseAuth.getCurrentUser().getUid())) {
                                 mUids.add(snapshot.getId());
                             }
                         }
                         if(mUids.size()!=0) {
                             getFriends(mUids);
                         }

                     }
                 });
    }
    public void getFriends(ArrayList<String> uids)
    {
        firestore.collection("Accounts")
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful())
                    {
                        ArrayList<AccountDetails> mDetails=new ArrayList<>();
                        for(QueryDocumentSnapshot snapshot: Objects.requireNonNull(task.getResult()))
                        {
                            for(String fresto:uids)
                            {
                                if(fresto.equals(snapshot.getId()))
                                {
                                    AccountDetails accountDetails=snapshot.toObject(AccountDetails.class);
                                    mDetails.add(accountDetails);
                                }
                            }
                        }
                        mutableLiveData.setValue(mDetails);
                    }
                });
    }
}
