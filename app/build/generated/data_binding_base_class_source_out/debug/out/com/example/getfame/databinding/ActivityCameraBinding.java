// Generated by view binder compiler. Do not edit!
package com.example.getfame.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.view.PreviewView;
import androidx.viewbinding.ViewBinding;
import com.example.getfame.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityCameraBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final ImageView addImages;

  @NonNull
  public final ImageView closeCamera;

  @NonNull
  public final ImageView flipCameras;

  @NonNull
  public final ImageView imageCapture;

  @NonNull
  public final PreviewView previewView;

  @NonNull
  public final ImageView torchImage;

  @NonNull
  public final ImageView touchImage;

  @NonNull
  public final SeekBar zoomSeekBar;

  private ActivityCameraBinding(@NonNull RelativeLayout rootView, @NonNull ImageView addImages,
      @NonNull ImageView closeCamera, @NonNull ImageView flipCameras,
      @NonNull ImageView imageCapture, @NonNull PreviewView previewView,
      @NonNull ImageView torchImage, @NonNull ImageView touchImage, @NonNull SeekBar zoomSeekBar) {
    this.rootView = rootView;
    this.addImages = addImages;
    this.closeCamera = closeCamera;
    this.flipCameras = flipCameras;
    this.imageCapture = imageCapture;
    this.previewView = previewView;
    this.torchImage = torchImage;
    this.touchImage = touchImage;
    this.zoomSeekBar = zoomSeekBar;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityCameraBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityCameraBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_camera, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityCameraBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.add_images;
      ImageView addImages = rootView.findViewById(id);
      if (addImages == null) {
        break missingId;
      }

      id = R.id.close_camera;
      ImageView closeCamera = rootView.findViewById(id);
      if (closeCamera == null) {
        break missingId;
      }

      id = R.id.flip_cameras;
      ImageView flipCameras = rootView.findViewById(id);
      if (flipCameras == null) {
        break missingId;
      }

      id = R.id.image_capture;
      ImageView imageCapture = rootView.findViewById(id);
      if (imageCapture == null) {
        break missingId;
      }

      id = R.id.previewView;
      PreviewView previewView = rootView.findViewById(id);
      if (previewView == null) {
        break missingId;
      }

      id = R.id.torch_image;
      ImageView torchImage = rootView.findViewById(id);
      if (torchImage == null) {
        break missingId;
      }

      id = R.id.touch_image;
      ImageView touchImage = rootView.findViewById(id);
      if (touchImage == null) {
        break missingId;
      }

      id = R.id.zoomSeekBar;
      SeekBar zoomSeekBar = rootView.findViewById(id);
      if (zoomSeekBar == null) {
        break missingId;
      }

      return new ActivityCameraBinding((RelativeLayout) rootView, addImages, closeCamera,
          flipCameras, imageCapture, previewView, torchImage, touchImage, zoomSeekBar);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
