package com.example.getfame.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.getfame.Models.ImageDetails;
import com.example.getfame.Repository.PhotosRepository;

import java.util.ArrayList;
import java.util.List;

public class PhotosViewModel extends AndroidViewModel {
    private PhotosRepository photosRepository;
    private MutableLiveData<ArrayList<ImageDetails>> mutableLiveData;
    public PhotosViewModel(@NonNull Application application) {
        super(application);
        photosRepository=new PhotosRepository();
    }
    public LiveData<ArrayList<ImageDetails>> getPhotos()
    {
        if(mutableLiveData==null)
        {
            mutableLiveData=photosRepository.requestPhotos();
        }
        return mutableLiveData;
    }
}
