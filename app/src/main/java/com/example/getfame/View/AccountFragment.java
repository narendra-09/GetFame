package com.example.getfame.View;

import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.getfame.Models.AccountDetails;
import com.example.getfame.Models.ImageDetails;
import com.example.getfame.R;
import com.example.getfame.ViewModels.AccountViewModel;
import com.example.getfame.databinding.FragmentAccountBinding;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class AccountFragment extends Fragment {
    private FragmentAccountBinding accountBinding;
    private AccountViewModel accountViewModel;
    private PhotosAdapter photosAdapter;
    private final int REQUEST_CODE=1;
    private Uri mImageRef;
    public AccountFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        accountBinding=FragmentAccountBinding.inflate(inflater,container,false);
        View view=accountBinding.getRoot();
        AppCompatActivity appCompatActivity=(AppCompatActivity)getActivity();
        assert appCompatActivity != null;
        accountViewModel=new AccountViewModel(appCompatActivity.getApplication());
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(appCompatActivity,2);
        accountBinding.recyclerView.setLayoutManager(layoutManager);
        accountBinding.recyclerView.addItemDecoration(new SpacesItemDecoration(5));
        accountViewModel.getPhotos().observe(appCompatActivity, imageDetails -> {
            photosAdapter=new PhotosAdapter(appCompatActivity,imageDetails);
            accountBinding.recyclerView.setAdapter(photosAdapter);
        });
        accountViewModel.getUser().observe(appCompatActivity, accountDetails -> {
            Glide.with(appCompatActivity)
                    .load(accountDetails.getDefaultProfile())
                    .apply(RequestOptions.circleCropTransform())
                    .into(accountBinding.profile);
            accountBinding.name.setText(accountDetails.getUsername());
        });
        accountBinding.profile.setOnClickListener(view1 -> {
            Intent intent=new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent,REQUEST_CODE);
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
                                   @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            outRect.left = 0;
            outRect.right = space;
            outRect.bottom = 10;
            outRect.top=10;
        }
    }
    //On Activity Result

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE && resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
            mImageRef=data.getData();
            Glide.with(this)
                    .load(mImageRef)
                    .apply(RequestOptions.circleCropTransform())
                    .into(accountBinding.profile);
            accountViewModel.storeUserProfilePicture(mImageRef);
        }
    }
}