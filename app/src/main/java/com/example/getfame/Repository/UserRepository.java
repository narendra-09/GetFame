package com.example.getfame.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.getfame.Models.AccountDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class UserRepository {
    private FirebaseFirestore firestore;
    private FirebaseAuth firebaseAuth;
    private AccountDetails accountDetails;
    public MutableLiveData<AccountDetails> getUser()
    {
        firebaseAuth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
        final MutableLiveData<AccountDetails> mutableLiveData=new MutableLiveData<>();
        firestore.collection("Accounts")
                 .document(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid())
                 .get()
                 .addOnCompleteListener(task -> {
                     if(task.isSuccessful())
                     {
                         accountDetails= Objects.requireNonNull(task.getResult()).toObject(AccountDetails.class);
                         mutableLiveData.setValue(accountDetails);
                     }
                 });
        return mutableLiveData;
    }
}
