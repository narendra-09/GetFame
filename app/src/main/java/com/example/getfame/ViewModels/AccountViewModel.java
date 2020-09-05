package com.example.getfame.ViewModels;

import android.app.Application;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.example.getfame.Models.AccountDetails;
import com.example.getfame.Models.ImageDetails;
import com.example.getfame.Repository.PhotosRepository;
import com.example.getfame.Repository.UserRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Objects;

public class AccountViewModel extends AndroidViewModel {
    private FirebaseFirestore firestore;
    private FirebaseStorage firebaseStorage;
    private FirebaseAuth firebaseAuth;
    private PhotosRepository photosRepository;
    private UserRepository userRepository;
    private MutableLiveData<ArrayList<ImageDetails>> mutableLiveData;
    private MutableLiveData<AccountDetails> accountDetailsMutableLiveData;
    public AccountViewModel(@NonNull Application application) {
        super(application);
        photosRepository=new PhotosRepository();
        userRepository=new UserRepository();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseStorage=FirebaseStorage.getInstance();
        firestore=FirebaseFirestore.getInstance();
    }
    public LiveData<ArrayList<ImageDetails>> getPhotos()
    {
        if(mutableLiveData==null)
        {
            mutableLiveData=photosRepository.requestPhotos();
        }
        return mutableLiveData;
    }
    public LiveData<AccountDetails> getUser()
    {
        if(accountDetailsMutableLiveData==null)
        {
            accountDetailsMutableLiveData=userRepository.getUser();
        }
        return accountDetailsMutableLiveData;
    }

    public void storeUserProfilePicture(Uri mImageRef) {
        StorageReference storageReference=firebaseStorage.getReference();
        StorageReference mRef=storageReference.child("Profile")
                                              .child(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid())
                                              .child(String.valueOf(System.currentTimeMillis()));
        UploadTask uploadTask=mRef.putFile(mImageRef);
        uploadTask.addOnCompleteListener(task -> {
        });

        Task<Uri> urlTask = uploadTask.continueWithTask(task -> {
            if (!task.isSuccessful()) {
                throw Objects.requireNonNull(task.getException());
            }
            // Continue with the task to get the download URL
            return mRef.getDownloadUrl();
        }).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Uri downloadUri = task.getResult();
                assert downloadUri != null;
                String downloadURL = downloadUri.toString();
                updateProfilePicture(downloadURL);
            }
        });
    }
    public void updateProfilePicture(String downloadUrl)
    {
        DocumentReference documentReference=firestore.collection("Accounts")
                                                      .document(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid());
        documentReference.update("defaultProfile",downloadUrl)
                         .addOnCompleteListener(task -> {
                             Log.d("Success","Uploaded");
                         });

    }
}
