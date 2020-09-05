package com.example.getfame.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.example.getfame.R;
import com.example.getfame.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {
private ActivitySignUpBinding signUpBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signUpBinding=ActivitySignUpBinding.inflate(getLayoutInflater());
        View view=signUpBinding.getRoot();
        setContentView(view);
        openUsernameFragment();
    }
    private void openUsernameFragment()
    {
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        UsernameFragment usernameFragment=new UsernameFragment();
        fragmentTransaction.add(signUpBinding.frameLayout.getId(),usernameFragment);
        fragmentTransaction.commit();

    }
}