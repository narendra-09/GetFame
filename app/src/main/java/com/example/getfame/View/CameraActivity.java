package com.example.getfame.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.camera2.Camera2Config;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.CameraXConfig;
import androidx.camera.core.FocusMeteringAction;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.MeteringPoint;
import androidx.camera.core.MeteringPointFactory;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.getfame.R;
import com.example.getfame.ViewModels.CameraViewModel;
import com.example.getfame.databinding.ActivityCameraBinding;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CameraActivity extends AppCompatActivity implements CameraXConfig.Provider {
private ActivityCameraBinding cameraBinding;
private CameraViewModel cameraViewModel;
private int REQUEST_CODE_PERMISSIONS = 1;
private final String[] REQUIRED_PERMISSIONS = new String[]{"android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE"};
private CameraSelector cameraSelector;
private Camera camera;
private ImageCapture imageCapture;
private Executor executor = Executors.newSingleThreadExecutor();
    @NonNull
    @Override
    public CameraXConfig getCameraXConfig() {
        return Camera2Config.defaultConfig();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cameraBinding=ActivityCameraBinding.inflate(getLayoutInflater());
        View view=cameraBinding.getRoot();
        setContentView(view);
        //ViewModel
        cameraViewModel=new ViewModelProvider(this).get(CameraViewModel.class);
        //ViewModel ends
        //Permission
        checkingPermissions();
        //Permission Check Ended
        cameraBinding.closeCamera.setOnClickListener(view1 -> onBackPressed());//Closes Camera and Returns
        cameraBinding.torchImage.setOnClickListener(view1 -> torch());//Flash On and Off
        cameraBinding.addImages.setOnClickListener(view1 -> addImages());
        cameraBinding.imageCapture.setOnClickListener(view1 -> {
            cameraBinding.imageCapture.setEnabled(false);
            createDir();
            takePictures();
        });//Taking Pictures
        showFocus();//Focus call
        setUpZoomSlider();//Zoom In and Zoom Out

    }
    //Permission Methods
    private void checkingPermissions()
    {
        if(allPermissionsGranted())
        {
            startCamera();
        }
        else
        {
            ActivityCompat.requestPermissions(this,REQUIRED_PERMISSIONS,REQUEST_CODE_PERMISSIONS);
        }
    }


    private boolean allPermissionsGranted(){

        for(String permission : REQUIRED_PERMISSIONS){
            if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode== REQUEST_CODE_PERMISSIONS)
        {
            if(allPermissionsGranted())
            {
                startCamera();
            }
            else{
                Toast.makeText(this, "Not granted", Toast.LENGTH_SHORT).show();
            }
        }
    }
    //Permission Methods Ends
    //The Camera
    private void startCamera()
    {
        final ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider=cameraProviderFuture.get();
                bindPreview(cameraProvider);
            }
            catch (InterruptedException | ExecutionException e)
            {
                Log.d("Error","error");
            }
        },ContextCompat.getMainExecutor(this));
    }
    //Starting Camera Ends
    //Binds Preview
    private void bindPreview(ProcessCameraProvider cameraProvider) {
        Preview preview=new Preview.Builder().build();
        cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();
        ImageCapture.Builder builder = new ImageCapture.Builder();
        imageCapture=builder
                .setTargetRotation(this.getWindowManager().getDefaultDisplay().getRotation())
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY)
                .build();
        preview.setSurfaceProvider(cameraBinding.previewView.createSurfaceProvider());
        camera=cameraProvider.bindToLifecycle(this,cameraSelector,preview,imageCapture);
        cameraBinding.flipCameras.setOnClickListener(view -> {
            if(!cameraViewModel.frontCam)
            {
                cameraBinding.torchImage.setEnabled(false);
                cameraBinding.torchImage.setVisibility(View.INVISIBLE);
                cameraViewModel.frontCamera();
                buildCamera(cameraProvider,CameraSelector.LENS_FACING_FRONT);
            }
            else
            {
                cameraBinding.torchImage.setVisibility(View.VISIBLE);
                cameraBinding.torchImage.setEnabled(true);
                buildCamera(cameraProvider,CameraSelector.LENS_FACING_BACK);
                if(cameraViewModel.flash)
                {
                    camera.getCameraControl().enableTorch(true);
                }
                cameraViewModel.backCamera();
            }
        });
    }
    //Binds Preview Ends
    //Focus
    @SuppressLint("ClickableViewAccessibility")
    private void showFocus()
    {

        cameraBinding.previewView.setOnTouchListener((view, motionEvent) -> {

            if(motionEvent.getAction()!= MotionEvent.ACTION_DOWN)
            {
                return false;
            }
            else if(motionEvent.getAction()== MotionEvent.ACTION_DOWN)
            {
                float xCoordinate = (float) (motionEvent.getX() - 80.0);
                float yCoordinate = (float) (motionEvent.getY() - 80.0);
                MeteringPointFactory factory = cameraBinding.previewView.getMeteringPointFactory();
                MeteringPoint focusPoint = factory.createPoint(motionEvent.getX(), motionEvent.getY());
                FocusMeteringAction.Builder builder1 = new FocusMeteringAction.Builder(focusPoint, FocusMeteringAction.FLAG_AF);
                FocusMeteringAction action = builder1.disableAutoCancel().build();
                camera.getCameraControl().startFocusAndMetering(action);
                cameraBinding.touchImage.setX(xCoordinate);
                cameraBinding.touchImage.setY(yCoordinate);
                cameraBinding.touchImage.setVisibility(View.VISIBLE);
                new Thread(() -> {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    cameraBinding.touchImage.setVisibility(View.INVISIBLE);
                }).start();
                return true;
            }
            return false;
        });


    }
    //Focus Ends
    //Zoom Slider
    private void setUpZoomSlider()
    {
        cameraBinding.zoomSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                camera.getCameraControl().setZoomRatio((float)(i/10.0));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
    }
    //Zoom Slider Ends
    //Flash
    private void torch()
    {
     if(!cameraViewModel.flash)
     {
        cameraViewModel.torchOn();
        camera.getCameraControl().enableTorch(true);
        cameraBinding.torchImage.setImageResource(R.drawable.flash_on);
     }
     else {
         cameraViewModel.torchOff();
         camera.getCameraControl().enableTorch(false);
         cameraBinding.torchImage.setImageResource(R.drawable.flash_off);
     }
    }
    //Flash Ends

    //Building Camera Front and Back one
    private void buildCamera(ProcessCameraProvider cameraProvider,int lensFacing) {
        cameraProvider.unbindAll();
        Preview preview=new Preview.Builder().build();
        cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(lensFacing)
                .build();
        ImageCapture.Builder builder = new ImageCapture.Builder();
        imageCapture=builder
                .setTargetRotation(this.getWindowManager().getDefaultDisplay().getRotation())
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY)
                .build();
        preview.setSurfaceProvider(cameraBinding.previewView.createSurfaceProvider());
        camera=cameraProvider.bindToLifecycle(this,cameraSelector,preview,imageCapture);
    }
    //Building Camera Front and Back one ends
    //Take Pictures
    private void takePictures()
    {
        SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
        File file = new File(getBatchDirectoryName(), mDateFormat.format(new Date())+ ".jpg");
        ImageCapture.OutputFileOptions outputFileOptions = new ImageCapture.OutputFileOptions.Builder(file).build();
        imageCapture.takePicture(outputFileOptions, executor, new ImageCapture.OnImageSavedCallback () {
            @Override
            public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                openCameraImage();
            }
            @Override
            public void onError(@NonNull ImageCaptureException error) {
                error.printStackTrace();
            }
        });
    }
    //Take Pictures Ends
    //Create Directory
    private void createDir()
    {
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.Q)
        {
            File file=new File(Environment.getExternalStorageDirectory()+File.separator+"GetFame","Images");
            if(!file.exists()){
                boolean result=file.mkdirs();
            }
        }
    }
    //Creating Directory Ends
    //Get Storage location
    private String getBatchDirectoryName() {
        return Environment.getExternalStorageDirectory().toString() + "/GetFame/Images";
    }
    //Storage location ends
    private void openCameraImage()
    {
        Intent intent=new Intent(CameraActivity.this,CameraImageDisplay.class);
        startActivity(intent);
    }
    //Add Images
    private void addImages()
    {
        startActivity(new Intent(CameraActivity.this,CameraImageDisplay.class).putExtra("AddImage","one"));
    }
    //Adding Images ends
    //Callback
    @Override
    protected void onResume() {
        super.onResume();
        cameraBinding.torchImage.setImageResource(R.drawable.flash_off);
        cameraBinding.imageCapture.setEnabled(true);
    }
}