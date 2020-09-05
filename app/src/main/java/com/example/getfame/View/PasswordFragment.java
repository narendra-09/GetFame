package com.example.getfame.View;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.getfame.R;
import com.example.getfame.ViewModels.PasswordViewModel;
import com.example.getfame.databinding.FragmentPasswordBinding;

public class PasswordFragment extends Fragment {
private FragmentPasswordBinding passwordBinding;
private PasswordViewModel passwordViewModel;
private AppCompatActivity appCompatActivity;
private Bundle bundle;
    public PasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        passwordBinding=FragmentPasswordBinding.inflate(inflater,container,false);
        View view=passwordBinding.getRoot();
        bundle=getArguments();
        appCompatActivity=(AppCompatActivity)getActivity();
        assert appCompatActivity != null;
        passwordViewModel=new PasswordViewModel(appCompatActivity.getApplication());
        passwordBinding.register.setEnabled(false);
        passwordBinding.register.setBackground(ContextCompat.getDrawable(appCompatActivity,R.drawable.button_disable));
        passwordBinding.password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
              passwordViewModel.validatePassword(passwordBinding.password.getText().toString(),passwordBinding);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        passwordBinding.register.setOnClickListener(view1 ->{
            passwordViewModel.registerUser(passwordViewModel.createUser(bundle,passwordBinding));
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        getHome();
        return view;
    }
    public void getHome()
    {
        passwordViewModel.getmSuccess().observe(this, integer -> {
            if(integer==100)
            {
                startActivity(new Intent(appCompatActivity,HomeActivity.class));
                appCompatActivity.finishAffinity();
            }
            else if(integer==200)
            {
                passwordViewModel.mSuccess.setValue(50);
                Toast.makeText(appCompatActivity, "Auth Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}