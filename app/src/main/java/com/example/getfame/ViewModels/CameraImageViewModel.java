package com.example.getfame.ViewModels;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.lifecycle.ViewModel;

import com.example.getfame.Models.ImageDetails;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class CameraImageViewModel extends ViewModel {
    private Bitmap bitmap;
    private FirebaseFirestore firestore;
    private  FirebaseAuth firebaseAuth;
    private File mFile;
    public void saveCapturedImage(Bitmap bitmap1)
    {
        bitmap=bitmap1;
        SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
        File file = new File(getBatchDirectoryName(), mDateFormat.format(new Date()) + ".jpg");
        try {
            FileOutputStream fileOutputStream=new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String getBatchDirectoryName() {

        return Environment.getExternalStorageDirectory().toString() + "/GetFame/CamImages";
    }
    public void createDir() {
        if(Build.VERSION.SDK_INT< Build.VERSION_CODES.Q)
        {
            File file=new File(Environment.getExternalStorageDirectory()+File.separator+"GetFame","CamImages");
            if(!file.exists()){
                boolean result=file.mkdirs();
            }
        }
    }
    public void deleteTemp() {
            File folder=new File(Environment.getExternalStorageDirectory()+"/GetFame/Images");
            if(folder.exists())
            {
                File[] allFiles=folder.listFiles();
                if(allFiles==null) return;
                for(File x:allFiles)
                {
                    boolean result=x.delete();
                }
            }
    }
    public void storeImageToFireStorage(Uri mImageRef)
    {
      firebaseAuth=FirebaseAuth.getInstance();
      firestore=FirebaseFirestore.getInstance();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
        String imageName = mDateFormat.format(new Date()) + ".jpg";
        String mImageName = mDateFormat.format(new Date());
        StorageReference mRef = storageReference.child("MyImages").child(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid()).child(imageName);
        UploadTask uploadTask=mRef.putFile(mImageRef);
        uploadTask.addOnSuccessListener(taskSnapshot -> { });
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
                addDownloadUrlToFireStore(downloadURL,mImageName);
            }
        });
    }

    private void addDownloadUrlToFireStore(String downloadURL, String mImageName) {
        ImageDetails imageDetails=new ImageDetails(downloadURL,mImageName);
        SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
        String downloadUrl = mDateFormat.format(new Date());
        firestore.collection("Accounts")
                .document(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid())
                .collection("DownloadUrls")
                .document(downloadUrl)
                .set(imageDetails)
                .addOnSuccessListener(aVoid -> {
                });
    }
    public File mGetImageOfCamera()
    {
        File folder=new File(Environment.getExternalStorageDirectory()+"/GetFame/Images");
        if(folder.exists())
        {
            File[] allFiles=folder.listFiles();
            assert allFiles != null;
            mFile=allFiles[0];
        }
        return mFile;
    }
    private String mGetBatchDirectoryName() {
        return Environment.getExternalStorageDirectory().toString() + "/GetFame/Images";
    }
}
