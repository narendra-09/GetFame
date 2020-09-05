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
import com.example.getfame.ViewModels.PhoneViewModel;
import com.example.getfame.databinding.FragmentPhoneBinding;

public class PhoneFragment extends Fragment {
private FragmentPhoneBinding phoneBinding;
private Bundle bundle;
    public PhoneFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        phoneBinding=FragmentPhoneBinding.inflate(inflater,container,false);
        View view=phoneBinding.getRoot();
        bundle=getArguments();
        AppCompatActivity appCompatActivity=(AppCompatActivity)getActivity();
        assert appCompatActivity != null;
        PhoneViewModel phoneViewModel=new PhoneViewModel(appCompatActivity.getApplication());
        phoneBinding.next.setEnabled(false);
        phoneBinding.next.setBackground(ContextCompat.getDrawable(appCompatActivity,R.drawable.button_disable));
        phoneBinding.phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
             phoneViewModel.validatePhone(phoneBinding.phone.getText().toString(),phoneBinding);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        phoneBinding.next.setOnClickListener(view1 -> openPasswordFragment());
        return view;
    }
    private void openPasswordFragment()
    {
        bundle.putString("phone",phoneBinding.phone.getText().toString());
        FragmentManager fragmentManager=getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        PasswordFragment passwordFragment=new PasswordFragment();
        passwordFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.frame_layout,passwordFragment);
        fragmentTransaction.addToBackStack("Phone Fragment");
        fragmentTransaction.commit();
    }
}