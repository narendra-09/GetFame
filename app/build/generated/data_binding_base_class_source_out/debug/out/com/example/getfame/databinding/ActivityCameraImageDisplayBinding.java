// Generated by view binder compiler. Do not edit!
package com.example.getfame.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import com.example.getfame.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityCameraImageDisplayBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final ImageView clearImage;

  @NonNull
  public final ImageView displayCameraImage;

  @NonNull
  public final Button saveImage;

  private ActivityCameraImageDisplayBinding(@NonNull RelativeLayout rootView,
      @NonNull ImageView clearImage, @NonNull ImageView displayCameraImage,
      @NonNull Button saveImage) {
    this.rootView = rootView;
    this.clearImage = clearImage;
    this.displayCameraImage = displayCameraImage;
    this.saveImage = saveImage;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityCameraImageDisplayBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityCameraImageDisplayBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_camera_image_display, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityCameraImageDisplayBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.clear_image;
      ImageView clearImage = rootView.findViewById(id);
      if (clearImage == null) {
        break missingId;
      }

      id = R.id.display_camera_image;
      ImageView displayCameraImage = rootView.findViewById(id);
      if (displayCameraImage == null) {
        break missingId;
      }

      id = R.id.save_image;
      Button saveImage = rootView.findViewById(id);
      if (saveImage == null) {
        break missingId;
      }

      return new ActivityCameraImageDisplayBinding((RelativeLayout) rootView, clearImage,
          displayCameraImage, saveImage);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
