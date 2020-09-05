// Generated by view binder compiler. Do not edit!
package com.example.getfame.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import com.example.getfame.R;
import com.google.android.material.appbar.MaterialToolbar;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentPendingRequestsBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final RecyclerView recyclerView;

  @NonNull
  public final MaterialToolbar toolbar;

  private FragmentPendingRequestsBinding(@NonNull RelativeLayout rootView,
      @NonNull RecyclerView recyclerView, @NonNull MaterialToolbar toolbar) {
    this.rootView = rootView;
    this.recyclerView = recyclerView;
    this.toolbar = toolbar;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentPendingRequestsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentPendingRequestsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_pending_requests, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentPendingRequestsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.recycler_view;
      RecyclerView recyclerView = rootView.findViewById(id);
      if (recyclerView == null) {
        break missingId;
      }

      id = R.id.toolbar;
      MaterialToolbar toolbar = rootView.findViewById(id);
      if (toolbar == null) {
        break missingId;
      }

      return new FragmentPendingRequestsBinding((RelativeLayout) rootView, recyclerView, toolbar);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}