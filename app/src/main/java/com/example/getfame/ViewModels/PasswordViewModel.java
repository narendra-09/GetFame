package com.example.getfame.ViewModels;

import android.app.Application;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.getfame.Models.AccountDetails;
import com.example.getfame.Models.UserUid;
import com.example.getfame.R;
import com.example.getfame.databinding.FragmentPasswordBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

public class PasswordViewModel extends AndroidViewModel {
    private Application application;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;
    public MutableLiveData<Integer> mSuccess;
    public PasswordViewModel(@NonNull Application application) {
        super(application);
        this.application=application;
        firebaseAuth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        mSuccess=new MutableLiveData<>();
    }


    public MutableLiveData<Integer> getmSuccess() {
        return mSuccess;
    }

    public void validatePassword(String password, FragmentPasswordBinding passwordBinding) {
        if(TextUtils.isEmpty(password))
        {
            passwordBinding.register.setEnabled(false);
            passwordBinding.register.setBackground(ContextCompat.getDrawable(application, R.drawable.button_disable));
        }
        else if(password.length()<8)
        {
            passwordBinding.register.setEnabled(false);
            passwordBinding.register.setBackground(ContextCompat.getDrawable(application, R.drawable.button_disable));
        }
        else
        {
            passwordBinding.register.setEnabled(true);
            passwordBinding.register.setBackground(ContextCompat.getDrawable(application, R.drawable.button_style));
        }
    }

    public void registerUser(AccountDetails user) {
      firebaseAuth.createUserWithEmailAndPassword(user.getEmail(),user.getPassword())
                  .addOnCompleteListener(task -> {
                   if(task.isSuccessful())
                   {
                       firestore.collection("Accounts")
                                .document(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid())
                                .set(user)
                                .addOnSuccessListener(aVoid -> {
                                        mSuccess.setValue(100);
                                });
                       UserUid userUid=new UserUid(firebaseAuth.getCurrentUser().getUid());
                       //Dummy Friend request
                       firestore.collection("Accounts")
                                .document(firebaseAuth.getCurrentUser().getUid())
                                .collection("Pending Requests")
                                .document(firebaseAuth.getCurrentUser().getUid())
                                .set(userUid)
                                .addOnSuccessListener(aVoid -> {

                                });
                       //Dummy Sent Requests
                       firestore.collection("Accounts")
                               .document(firebaseAuth.getCurrentUser().getUid())
                               .collection("Sent Requests")
                               .document(firebaseAuth.getCurrentUser().getUid())
                               .set(userUid)
                               .addOnSuccessListener(aVoid -> {

                               });
                       //Dummy Friend
                       firestore.collection("Accounts")
                               .document(firebaseAuth.getCurrentUser().getUid())
                               .collection("Friends")
                               .document(firebaseAuth.getCurrentUser().getUid())
                               .set(userUid)
                               .addOnSuccessListener(aVoid -> {

                               });
                   }
                  }).addOnFailureListener(e -> {
                      mSuccess.setValue(200);
                  });
    }
    public AccountDetails createUser(Bundle bundle,FragmentPasswordBinding passwordBinding)
    {
        return new AccountDetails(
                bundle.getString("username"),
                bundle.getString("birthday"),
                bundle.getString("email"),
                bundle.getString("phone"),
                passwordBinding.password.getText().toString(),
                getDefaultProfileUrl()
        );
    }
    private String getDefaultProfileUrl()
    {
       return "https://firebasestorage.googleapis.com/v0/b/getfame-2305b.appspot.com/o/DefaultProfile%2FProfile%2Flogo_two.png?alt=media&token=0a3972a3-6131-47e6-b8db-254dcd269c05";
    }
}
