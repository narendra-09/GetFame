// Generated by view binder compiler. Do not edit!
package com.example.getfame.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import com.example.getfame.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentPhoneBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final Button next;

  @NonNull
  public final EditText phone;

  private FragmentPhoneBinding(@NonNull LinearLayout rootView, @NonNull Button next,
      @NonNull EditText phone) {
    this.rootView = rootView;
    this.next = next;
    this.phone = phone;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentPhoneBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentPhoneBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_phone, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentPhoneBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.next;
      Button next = rootView.findViewById(id);
      if (next == null) {
        break missingId;
      }

      id = R.id.phone;
      EditText phone = rootView.findViewById(id);
      if (phone == null) {
        break missingId;
      }

      return new FragmentPhoneBinding((LinearLayout) rootView, next, phone);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
