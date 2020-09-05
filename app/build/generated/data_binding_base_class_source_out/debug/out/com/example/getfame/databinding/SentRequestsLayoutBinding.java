// Generated by view binder compiler. Do not edit!
package com.example.getfame.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import com.example.getfame.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class SentRequestsLayoutBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final Button sentButton;

  @NonNull
  public final TextView sentEmail;

  @NonNull
  public final TextView sentName;

  @NonNull
  public final ImageView sentProfilePic;

  private SentRequestsLayoutBinding(@NonNull RelativeLayout rootView, @NonNull Button sentButton,
      @NonNull TextView sentEmail, @NonNull TextView sentName, @NonNull ImageView sentProfilePic) {
    this.rootView = rootView;
    this.sentButton = sentButton;
    this.sentEmail = sentEmail;
    this.sentName = sentName;
    this.sentProfilePic = sentProfilePic;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static SentRequestsLayoutBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static SentRequestsLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.sent_requests_layout, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static SentRequestsLayoutBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.sent_button;
      Button sentButton = rootView.findViewById(id);
      if (sentButton == null) {
        break missingId;
      }

      id = R.id.sent_email;
      TextView sentEmail = rootView.findViewById(id);
      if (sentEmail == null) {
        break missingId;
      }

      id = R.id.sent_name;
      TextView sentName = rootView.findViewById(id);
      if (sentName == null) {
        break missingId;
      }

      id = R.id.sent_profile_pic;
      ImageView sentProfilePic = rootView.findViewById(id);
      if (sentProfilePic == null) {
        break missingId;
      }

      return new SentRequestsLayoutBinding((RelativeLayout) rootView, sentButton, sentEmail,
          sentName, sentProfilePic);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
