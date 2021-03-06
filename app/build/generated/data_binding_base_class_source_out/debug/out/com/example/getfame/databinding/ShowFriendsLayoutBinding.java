// Generated by view binder compiler. Do not edit!
package com.example.getfame.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public final class ShowFriendsLayoutBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final TextView friendsEmail;

  @NonNull
  public final TextView friendsName;

  @NonNull
  public final ImageView friendsProfilePic;

  private ShowFriendsLayoutBinding(@NonNull RelativeLayout rootView, @NonNull TextView friendsEmail,
      @NonNull TextView friendsName, @NonNull ImageView friendsProfilePic) {
    this.rootView = rootView;
    this.friendsEmail = friendsEmail;
    this.friendsName = friendsName;
    this.friendsProfilePic = friendsProfilePic;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ShowFriendsLayoutBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ShowFriendsLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.show_friends_layout, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ShowFriendsLayoutBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.friends_email;
      TextView friendsEmail = rootView.findViewById(id);
      if (friendsEmail == null) {
        break missingId;
      }

      id = R.id.friends_name;
      TextView friendsName = rootView.findViewById(id);
      if (friendsName == null) {
        break missingId;
      }

      id = R.id.friends_profile_pic;
      ImageView friendsProfilePic = rootView.findViewById(id);
      if (friendsProfilePic == null) {
        break missingId;
      }

      return new ShowFriendsLayoutBinding((RelativeLayout) rootView, friendsEmail, friendsName,
          friendsProfilePic);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
