package com.example.getfame.View;

import android.app.Application;
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
import com.example.getfame.ViewModels.BirthdayViewModel;
import com.example.getfame.databinding.FragmentBirthdayBinding;

public class BirthdayFragment extends Fragment {
private FragmentBirthdayBinding birthdayBinding;
private Bundle bundle;
    public BirthdayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        birthdayBinding=FragmentBirthdayBinding.inflate(inflater,container,false);
        View view=birthdayBinding.getRoot();
        bundle=getArguments();
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        assert appCompatActivity != null;
        BirthdayViewModel birthdayViewModel=new BirthdayViewModel(appCompatActivity.getApplication());
        birthdayBinding.next.setEnabled(false);
        birthdayBinding.next.setBackground(ContextCompat.getDrawable(appCompatActivity,R.drawable.button_disable));
        birthdayViewModel.selectDate(birthdayBinding.datePicker,birthdayBinding.birthday);
        birthdayBinding.birthday.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
              birthdayViewModel.validateBirthday(birthdayBinding.birthday.getText().toString(),birthdayBinding);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        birthdayBinding.next.setOnClickListener(view1 -> openEmailFragment());
        return view;
    }
    private void openEmailFragment()
    {
        bundle.putString("birthday",birthdayBinding.birthday.getText().toString());
        FragmentManager fragmentManager=getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        EmailFragment emailFragment=new EmailFragment();
        emailFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.frame_layout,emailFragment);
        fragmentTransaction.addToBackStack("Birhday Fragment");
        fragmentTransaction.commit();

    }
}