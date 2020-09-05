package com.example.getfame.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.getfame.R;
import com.example.getfame.ViewModels.EmailViewModel;
import com.example.getfame.databinding.FragmentEmailBinding;

public class EmailFragment extends Fragment {
private FragmentEmailBinding emailBinding;
private Bundle bundle;
    public EmailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        emailBinding=FragmentEmailBinding.inflate(inflater,container,false);
        View view=emailBinding.getRoot();
        bundle=getArguments();
        AppCompatActivity appCompatActivity=(AppCompatActivity)getActivity();
        assert appCompatActivity != null;
        EmailViewModel emailViewModel=new EmailViewModel(appCompatActivity.getApplication());
        emailBinding.next.setEnabled(false);
        emailBinding.next.setBackground(ContextCompat.getDrawable(appCompatActivity,R.drawable.button_disable));
        emailBinding.email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
             emailViewModel.validateEmail(emailBinding.email.getText().toString(),emailBinding);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        emailBinding.next.setOnClickListener(view1 -> openPhoneFragment());
        return view;
    }
    private void openPhoneFragment()
    {
        bundle.putString("email",emailBinding.email.getText().toString());
        FragmentManager fragmentManager=getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        PhoneFragment phoneFragment=new PhoneFragment();
        phoneFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.frame_layout,phoneFragment);
        fragmentTransaction.addToBackStack("Email Fragment");
        fragmentTransaction.commit();
    }
}