package com.example.getfame.ViewModels;

import android.os.Environment;

import androidx.lifecycle.ViewModel;

import java.io.File;

public class CameraViewModel extends ViewModel {
    public boolean flash=false;
    public boolean frontCam=false;
    private File mFile;
    public void torchOn() { flash=true; }
    public void torchOff() { flash=false; }
    public void frontCamera() { frontCam=true;  }
    public void backCamera() { frontCam=false;  }
}
