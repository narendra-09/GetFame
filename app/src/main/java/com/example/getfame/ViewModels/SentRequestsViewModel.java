package com.example.getfame.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.getfame.Models.AccountDetails;
import com.example.getfame.Models.UserUid;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class SentRequestsViewModel extends AndroidViewModel {
    private FirebaseFirestore firestore;
    private FirebaseAuth firebaseAuth;
    private ArrayList<String > mUids;
    private MutableLiveData<ArrayList<AccountDetails>> arrayListMutableLiveData;
    public SentRequestsViewModel(@NonNull Application application) {
        super(application);
        firebaseAuth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
        mUids=new ArrayList<>();
        arrayListMutableLiveData=new MutableLiveData<>();
    }
    public MutableLiveData<ArrayList<AccountDetails>> getArrayListMutableLiveData() {
        return arrayListMutableLiveData;
    }
    public void getSentRequests()
    {
        firestore.collection("Accounts")
                 .document(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid())
                 .collection("Sent Requests")
                 .get()
                 .addOnCompleteListener(task -> {
                     if(task.isSuccessful())
                     {
                         for(QueryDocumentSnapshot snapshot: Objects.requireNonNull(task.getResult()))
                         {
                             if(!snapshot.getId().equals(firebaseAuth.getCurrentUser().getUid()))
                             {
                                 mUids.add(snapshot.getId());
                             }
                             if(mUids.size()!=0) {
                                 getUsers(mUids);
                             }
                         }
                     }
                 });
    }
    public void getUsers(ArrayList<String> uids)
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
                        arrayListMutableLiveData.setValue(mDetails);
                    }
                });
    }
}
