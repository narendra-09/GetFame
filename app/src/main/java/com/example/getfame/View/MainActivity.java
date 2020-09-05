package com.example.getfame.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.getfame.R;
import com.example.getfame.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
private ActivityMainBinding mainBinding;
private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding=ActivityMainBinding.inflate(getLayoutInflater());
        View view=mainBinding.getRoot();
        setContentView(view);
        firebaseAuth=FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!=null)
        {
            startActivity(new Intent(this,HomeActivity.class));
            finishAffinity();
        }
        mainBinding.login.setOnClickListener(view1 -> openLoginActivity() );
        mainBinding.signUp.setOnClickListener(view1 -> openSignUpActivity() );
    }
    private void openLoginActivity()
    {
          startActivity(new Intent(MainActivity.this,LoginActivity.class));
    }
    private void openSignUpActivity()
    {
            startActivity(new Intent(MainActivity.this,SignUpActivity.class));
    }
}