package com.example.getfame.ViewModels;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.getfame.R;
import com.example.getfame.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class LoginViewModel extends AndroidViewModel {
    private Application application;
    private FirebaseAuth firebaseAuth;
    public MutableLiveData<Integer> mSuccess;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        this.application=application;
        mSuccess=new MutableLiveData<>();
        firebaseAuth=FirebaseAuth.getInstance();
    }


    public MutableLiveData<Integer> getmSuccess() {
        return mSuccess;
    }

    public void validateEmail(String email, ActivityLoginBinding loginBinding) {
        if(TextUtils.isEmpty(email))
        {
            loginBinding.login.setEnabled(false);
            loginBinding.login.setBackground(ContextCompat.getDrawable(application, R.drawable.button_disable));
        }
        else if(!isValid(email))
        {
            loginBinding.login.setEnabled(false);
            loginBinding.login.setBackground(ContextCompat.getDrawable(application, R.drawable.button_disable));
        }
        else {
            loginBinding.login.setEnabled(true);
            loginBinding.login.setBackground(ContextCompat.getDrawable(application, R.drawable.button_style));
        }
    }
    private boolean isValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
    public void login(ActivityLoginBinding loginBinding)
    {
        firebaseAuth.signInWithEmailAndPassword(loginBinding.email.getText().toString(),
                                                loginBinding.password.getText().toString())
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful())
                        {
                            mSuccess.setValue(100);
                            Log.d("Success","100");
                        }
                    }).addOnFailureListener(e -> {
                        mSuccess.setValue(200);
                        Log.d("Failure","200");
                    });
    }
}
