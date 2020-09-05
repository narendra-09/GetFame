package com.example.getfame.ViewModels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.getfame.Models.AccountDetails;
import com.example.getfame.Models.UserUid;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;
import java.util.regex.Pattern;

public class SearchViewModel extends AndroidViewModel {
    private FirebaseFirestore firestore;
    private FirebaseAuth firebaseAuth;
    private MutableLiveData<String> userUID;
    public MutableLiveData<AccountDetails> mAccountDetails;
    private String mUid2;
    private MutableLiveData<Integer> alreadySent;
    public SearchViewModel(@NonNull Application application) {
        super(application);
        firebaseAuth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
        userUID=new MutableLiveData<>();
        mAccountDetails=new MutableLiveData<>();
        alreadySent=new MutableLiveData<>();

    }

    public MutableLiveData<String> getUserUID() {
        return userUID;
    }

    public MutableLiveData<AccountDetails> getmAccountDetails() { return mAccountDetails; }

    public MutableLiveData<Integer> getAlreadySent() { return alreadySent; }

    public void getSearchedUser(String search_name) {
        firestore.collection("Accounts")
                 .get()
                 .addOnCompleteListener(task -> {
                     if(task.isSuccessful())
                     {
                         for(QueryDocumentSnapshot snapshot: Objects.requireNonNull(task.getResult()))
                         {
                             AccountDetails searchUser=snapshot.toObject(AccountDetails.class);
                             if(search_name.equals(searchUser.getEmail()) &&
                                     snapshot.getId().equals(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid())) {
                                /* AccountDetails accountDetails = new AccountDetails(searchUser.getUsername(),
                                                                                    searchUser.getBirthday(),
                                                                                    searchUser.getEmail(),
                                                                                    searchUser.getPhone(),
                                                                                     searchUser.getPassword(),
                                                                                     searchUser.getDefaultProfile());
                                 userUID.setValue(snapshot.getId());
                                 mAccountDetails.setValue(accountDetails);
                                 return; */
                                mAccountDetails.setValue(null);
                                return;
                             }
                             else if(search_name.equals(searchUser.getEmail()))
                             {
                                 AccountDetails accountDetails = new AccountDetails(searchUser.getUsername(),
                                         searchUser.getBirthday(),
                                         searchUser.getEmail(),
                                         searchUser.getPhone(),
                                         searchUser.getPassword(),
                                         searchUser.getDefaultProfile());
                                 userUID.setValue(snapshot.getId());
                                 mAccountDetails.setValue(accountDetails);
                                 return;
                             }
                         }
                         userUID.setValue(null);
                         mAccountDetails.setValue(null);
                     }
                 });
    }

    public void addToSentRequests(String Uid2) {
        mUid2=Uid2;
        UserUid userUid=new UserUid(Uid2);
        firestore.collection("Accounts")
                 .document(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid())
                 .collection("Sent Requests")
                 .document(Uid2)
                 .set(userUid)
                 .addOnCompleteListener(task -> {

                 });
    }

    public void addToPendingRequestOfUser() {
        UserUid userUid=new UserUid(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid());
        firestore.collection("Accounts")
                 .document(mUid2)
                 .collection("Pending Requests")
                 .document(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid())
                 .set(userUid)
                 .addOnSuccessListener(documentReference -> {

                 });
    }

    public void checkIfAlreadySent(String Uid2)
    {
        assert firebaseAuth.getCurrentUser()!=null;
        firestore.collection("Accounts")
                .document(firebaseAuth.getCurrentUser().getUid())
                .collection("Friends")
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful())
                    {
                        for(QueryDocumentSnapshot snapshot: Objects.requireNonNull(task.getResult()))
                        {
                            UserUid userUid=snapshot.toObject(UserUid.class);
                            if(userUid.getUserUid().equals(Uid2))
                            {
                                alreadySent.setValue(1000);
                                return;
                            }
                        }
                    }
                });
        firestore.collection("Accounts")
                .document(firebaseAuth.getCurrentUser().getUid())
                .collection("Pending Requests")
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful())
                    {
                        for(QueryDocumentSnapshot snapshot: Objects.requireNonNull(task.getResult()))
                        {
                            UserUid userUid=snapshot.toObject(UserUid.class);
                            if(userUid.getUserUid().equals(Uid2))
                            {
                                alreadySent.setValue(300);
                                return;
                            }
                        }
                    }
                });
        firestore.collection("Accounts")
                 .document(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid())
                 .collection("Sent Requests")
                 .get()
                 .addOnCompleteListener(task -> {
                     if(task.isSuccessful())
                     {
                         for(QueryDocumentSnapshot snapshot: Objects.requireNonNull(task.getResult()))
                         {
                             UserUid userUid=snapshot.toObject(UserUid.class);
                             if(userUid.getUserUid().equals(Uid2))
                             {
                                 alreadySent.setValue(100);
                                 return;
                             }
                         }
                     }
                     else
                     {
                         alreadySent.setValue(50);
                     }
                 });
        alreadySent.setValue(200);
    }
    public void mSleep()  {
        try{
        Thread.sleep(500);}catch (Exception ignored){}
    }
}
