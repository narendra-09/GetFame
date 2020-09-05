package com.example.getfame.View;

import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.getfame.Models.ImageDetails;
import com.example.getfame.R;
import com.example.getfame.ViewModels.PhotosViewModel;
import com.example.getfame.databinding.FragmentPhotosBinding;

import java.util.ArrayList;
import java.util.List;

public class PhotosFragment extends Fragment {
private FragmentPhotosBinding photosBinding;
    private AppCompatActivity appCompatActivity;
private PhotosAdapter photosAdapter;
    public PhotosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        photosBinding=FragmentPhotosBinding.inflate(inflater,container,false);
        View view=photosBinding.getRoot();
        appCompatActivity=(AppCompatActivity)getActivity();
        assert appCompatActivity != null;
        PhotosViewModel photosViewModel = new PhotosViewModel(appCompatActivity.getApplication());
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(appCompatActivity,3);
        photosBinding.recyclerView.addItemDecoration(new SpacesItemDecoration(2));
        photosBinding.recyclerView.setLayoutManager(layoutManager);
        photosViewModel.getPhotos().observe(appCompatActivity, imageDetails -> {
            photosAdapter=new PhotosAdapter(appCompatActivity,imageDetails);
            photosBinding.recyclerView.setAdapter(photosAdapter);
        });
        return view;
    }
    public static class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, @NonNull View view,
                                   RecyclerView parent, @NonNull RecyclerView.State state) {
            outRect.left = 0;
            outRect.right = space;
            outRect.bottom = space;

            // Add top margin only for the first item to avoid double space between items
            if (parent.getChildLayoutPosition(view) == 2) {
                outRect.right = 0;
            }
        }
    }
}