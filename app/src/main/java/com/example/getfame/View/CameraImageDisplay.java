package com.example.getfame.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.getfame.ViewModels.CameraImageViewModel;
import com.example.getfame.databinding.ActivityCameraImageDisplayBinding;

import java.io.File;


public class CameraImageDisplay extends AppCompatActivity {
private ActivityCameraImageDisplayBinding imageDisplayBinding;
private CameraImageViewModel imageViewModel;
    private final int REQUEST_CODE=1;
    private Uri mImageRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageDisplayBinding=ActivityCameraImageDisplayBinding.inflate(getLayoutInflater());
        View view=imageDisplayBinding.getRoot();
        setContentView(view);
        imageViewModel=new ViewModelProvider(this).get(CameraImageViewModel.class);
        imageViewModel.createDir();
        imageDisplayBinding.clearImage.setOnClickListener(view1 -> {onBackPressed();
            //Delete Temporary files
            imageViewModel.deleteTemp();});
        String addImageValue=getIntent().getStringExtra("AddImage");
        if(addImageValue==null)
        {
            //Set Image from Camera Here
            setImageOfCamera();
            //Save Image of Camera
            saveImageOfCamera();
        }
        else
        {
            Intent intent=new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent,REQUEST_CODE);
            //Save Image Selected
            proceedFurtherSelectedImage();
        }
    }
    private void proceedFurtherSelectedImage() {
        imageDisplayBinding.saveImage.setOnClickListener(view -> imageViewModel.storeImageToFireStorage(mImageRef));
    }
    private void saveImageOfCamera() {
        imageDisplayBinding.saveImage.setOnClickListener(view -> {
            Bitmap bitmap=((BitmapDrawable)imageDisplayBinding.displayCameraImage.getDrawable()).getBitmap();
            imageViewModel.saveCapturedImage(bitmap);
            imageViewModel.storeImageToFireStorage(mImageRef);
            //Delete Temporary files
            imageViewModel.deleteTemp();
        });
    }
    private void setImageOfCamera() {
        File file=imageViewModel.mGetImageOfCamera();
        mImageRef=Uri.fromFile(file);
        Glide.with(this).load(mImageRef).centerCrop().into(imageDisplayBinding.displayCameraImage);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE && resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
            mImageRef=data.getData();
            Glide.with(this).load(mImageRef).centerCrop().into(imageDisplayBinding.displayCameraImage);
        }
        else {
            this.finish();
        }
    }
}