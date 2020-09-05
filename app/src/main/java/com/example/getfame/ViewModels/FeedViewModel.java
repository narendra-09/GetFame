package com.example.getfame.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.getfame.Models.AccountDetails;
import com.example.getfame.Models.FeedDetails;
import com.example.getfame.Models.FeedImages;
import com.example.getfame.Models.ImageDetails;
import com.example.getfame.Repository.FeedRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class FeedViewModel extends AndroidViewModel {
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;
    private ArrayList<String> mUids;
    private FeedRepository feedRepository;
    public FeedViewModel(@NonNull Application application) {
        super(application);
        firebaseAuth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
        mUids=new ArrayList<>();
        feedRepository=new FeedRepository();
    }
    public void getUids()
    {
        firestore.collection("Accounts")
                 .document(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid())
                 .collection("Friends")
                 .get()
                 .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                     @Override
                     public void onComplete(@NonNull Task<QuerySnapshot> task) {
                         if(task.isSuccessful())
                         {
                             for(QueryDocumentSnapshot snapshot: Objects.requireNonNull(task.getResult()))
                             {
                                 if(!snapshot.getId().equals(firebaseAuth.getCurrentUser().getUid()))
                                 {
                                     mUids.add(snapshot.getId());
                                 }
                             }
                             if(mUids.size()!=0)
                             {
                             getFeedDetails(mUids);}
                         }
                     }
                 });
    }
    public void getFeedDetails(ArrayList<String> friendsId)
    {
        firestore.collection("Accounts")
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful())
                    {
                        ArrayList<AccountDetails> mDetails=new ArrayList<>();
                        ArrayList<ImageDetails> mImageDetails=new ArrayList<>();
                        for(QueryDocumentSnapshot snapshot: Objects.requireNonNull(task.getResult()))
                        {
                            for(String fresto:friendsId)
                            {
                                if(fresto.equals(snapshot.getId()))
                                {
                                    AccountDetails accountDetails=snapshot.toObject(AccountDetails.class);
                                    mDetails.add(accountDetails);

                                    //Getting Images
                                    firestore.collection("Accounts")
                                             .document(snapshot.getId())
                                             .collection("DownloadUrls")
                                             .get()
                                             .addOnCompleteListener(task1 -> {
                                                 if(task1.isSuccessful())
                                                 {
                                                     if(task1.getResult()!=null){
                                                         for(QueryDocumentSnapshot snapshot1: task1.getResult())
                                                         {
                                                             mImageDetails.add(snapshot1.toObject(ImageDetails.class));
                                                             ImageDetails imageDetails=snapshot1.toObject(ImageDetails.class);
                                                             FeedImages feedImages=new FeedImages(
                                                                     accountDetails.getUsername(),
                                                                     accountDetails.getDefaultProfile(),
                                                                     imageDetails.getImageUrl(),
                                                                     imageDetails.getImageName());
                                                             firestore.collection("Accounts")
                                                                     .document(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid())
                                                                     .collection("DisplayFeed")
                                                                     .document(snapshot.getId())
                                                                     .set(feedImages)
                                                                     .addOnSuccessListener(aVoid -> {});
                                                         }
                                                     }
                                                 }
                                             });
                                }
                            }
                        }

                    }
                });
    }
   public  MutableLiveData<ArrayList<FeedDetails>> getMutData()
   {
       return feedRepository.getDisplayDetails();
   }
}
