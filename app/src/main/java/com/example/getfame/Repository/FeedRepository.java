package com.example.getfame.Repository;

import androidx.lifecycle.MutableLiveData;

import com.example.getfame.Models.FeedDetails;
import com.example.getfame.Models.FeedImages;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class FeedRepository {
    public MutableLiveData<ArrayList<FeedDetails>> getDisplayDetails()
    {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        final MutableLiveData<ArrayList<FeedDetails>> mutableLiveData=new MutableLiveData<>();
        firestore.collection("Accounts")
                 .document(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid())
                 .collection("DisplayFeed")
                 .get()
                 .addOnCompleteListener(task -> {
                     if(task.isSuccessful())
                     {
                         ArrayList<FeedDetails> mFeedDetails=new ArrayList<>();
                         if(task.getResult()!=null)
                         {
                             for(QueryDocumentSnapshot snapshot: task.getResult())
                             {
                                 FeedImages feedImages=snapshot.toObject(FeedImages.class);
                                 mFeedDetails.add(new FeedDetails(
                                         feedImages.getName(),
                                         feedImages.getProfile_pic(),
                                         feedImages.getImage(),
                                         Long.parseLong(feedImages.getImage_name())
                                 ));
                             }
                         }
                         /*for(QueryDocumentSnapshot snapshot: task.getResult())
                         {
                             FeedImages feedImages=snapshot.toObject(FeedImages.class);
                             mFeedDetails.add(new FeedDetails(
                                     feedImages.getName(),
                                     feedImages.getProfile_pic(),
                                     feedImages.getImage(),
                                     Long.parseLong(feedImages.getImage_name())
                             ));
                         }*/
                         Collections.sort(mFeedDetails, (t, t1) -> (int) (t.getImageName()-t1.getImageName()));
                         Collections.reverse(mFeedDetails);
                         mutableLiveData.setValue(mFeedDetails);
                     }
                 });
    return mutableLiveData;
    }
}
