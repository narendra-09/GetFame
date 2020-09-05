package com.example.getfame.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.getfame.Models.ImageDetails;
import com.example.getfame.ViewModels.PhotosViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PhotosRepository {
    private FirebaseFirestore firestore;
    private FirebaseAuth firebaseAuth;
    private ImageDetails imageDetails;
    private ArrayList<ImageDetails> arrayList;
    public MutableLiveData<ArrayList<ImageDetails>> requestPhotos()
    {
        firebaseAuth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
        final MutableLiveData<ArrayList<ImageDetails>> mutableLiveData=new MutableLiveData<>();
        arrayList=new ArrayList<>();
        firestore.collection("Accounts")
                 .document(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid())
                 .collection("DownloadUrls")
                 .get()
                 .addOnCompleteListener(task -> {
                   if(task.isSuccessful())
                   {
                       for(QueryDocumentSnapshot snapshot: Objects.requireNonNull(task.getResult()))
                       {
                           imageDetails=snapshot.toObject(ImageDetails.class);
                           arrayList.add(new ImageDetails(imageDetails.getImageUrl(),imageDetails.getImageName()));
                       }
                       mutableLiveData.setValue(arrayList);
                   }
                 });
        return mutableLiveData;
    }
}
