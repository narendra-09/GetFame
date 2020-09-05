package com.example.getfame.ViewModels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.getfame.Models.AccountDetails;
import com.example.getfame.Models.UserUid;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;


import java.util.ArrayList;
import java.util.Objects;

public class PendingRequestsViewModel extends AndroidViewModel {
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;
    private ArrayList<String > mUids;
    private MutableLiveData<ArrayList<AccountDetails>> arrayListMutableLiveData;
    private String deletedUserUid;
    public PendingRequestsViewModel(@NonNull Application application) {
        super(application);
        firebaseAuth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
        mUids=new ArrayList<>();
        arrayListMutableLiveData=new MutableLiveData<>();
    }

    public MutableLiveData<ArrayList<AccountDetails>> getArrayListMutableLiveData() {
        return arrayListMutableLiveData;
    }

    public void getUids()
    {
        firestore.collection("Accounts")
                 .document(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid())
                 .collection("Pending Requests")
                 .get()
                 .addOnCompleteListener(task -> {
                     if(task.isSuccessful())
                     {
                         for(QueryDocumentSnapshot snapshot: Objects.requireNonNull(task.getResult()))
                         {
                             Log.d("Cheers",""+snapshot.getId());
                             if(!snapshot.getId().equals(firebaseAuth.getCurrentUser().getUid()))
                             {
                             mUids.add(snapshot.getId());}
                         }
                         if(mUids.size()!=0) {
                             getUsers(mUids);
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

    public void getDeletedUserUid(String email) {
        firestore.collection("Accounts")
                 .get()
                 .addOnCompleteListener(task -> {
                     if(task.isSuccessful())
                     {
                         for(QueryDocumentSnapshot snapshot: Objects.requireNonNull(task.getResult()))
                         {
                             AccountDetails accountDetails=snapshot.toObject(AccountDetails.class);
                             if(accountDetails.getEmail().equals(email))
                             {
                                 deletedUserUid=snapshot.getId();
                                 deleteUserFromPendingRequests(deletedUserUid);
                                 deleteFromSentRequestsOfUser(deletedUserUid);
                                 addToFriends(deletedUserUid);
                                 return;
                             }
                         }
                     }
                 });
    }

    private void addToFriends(String deletedUserUid) {
        UserUid userUid=new UserUid(deletedUserUid);
        firestore.collection("Accounts")
                 .document(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid())
                 .collection("Friends")
                 .document(deletedUserUid)
                 .set(userUid)
                 .addOnSuccessListener(aVoid -> {});
        UserUid userUid1=new UserUid(firebaseAuth.getCurrentUser().getUid());
        firestore.collection("Accounts")
                 .document(deletedUserUid)
                 .collection("Friends")
                 .document(firebaseAuth.getCurrentUser().getUid())
                 .set(userUid1)
                 .addOnSuccessListener(aVoid -> {});
    }

    private void deleteFromSentRequestsOfUser(String deletedUserUid) {
        firestore.collection("Accounts")
                 .document(deletedUserUid)
                 .collection("Sent Requests")
                 .document(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid())
                 .delete()
                 .addOnSuccessListener(aVoid -> {});

    }

    private void deleteUserFromPendingRequests(String deletedUserUid) {
        firestore.collection("Accounts")
                 .document(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid())
                 .collection("Pending Requests")
                 .document(deletedUserUid)
                 .delete()
                 .addOnSuccessListener(aVoid -> {});
    }
}
