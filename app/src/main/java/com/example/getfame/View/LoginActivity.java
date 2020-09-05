package com.example.getfame.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.getfame.R;
import com.example.getfame.ViewModels.LoginViewModel;
import com.example.getfame.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
private ActivityLoginBinding loginBinding;
private LoginViewModel loginViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding=ActivityLoginBinding.inflate(getLayoutInflater());
        View view=loginBinding.getRoot();
        setContentView(view);
        loginViewModel=new LoginViewModel(this.getApplication());
        loginBinding.login.setEnabled(false);
        loginBinding.login.setBackground(ContextCompat.getDrawable(this, R.drawable.button_disable));
        loginBinding.email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
              loginViewModel.validateEmail(loginBinding.email.getText().toString(),loginBinding);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        loginBinding.login.setOnClickListener(view1 -> {
            loginViewModel.login(loginBinding);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            getHome();
        });
    }
    public void getHome()
    {
        loginViewModel.getmSuccess().observe(this, integer -> {
            if(integer==100)
            {
                startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                finishAffinity();
            }
            else if(integer==200)
            {
                loginViewModel.mSuccess.setValue(50);
                Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}