package com.example.getfame.ViewModels;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;

import com.example.getfame.R;
import com.example.getfame.databinding.FragmentEmailBinding;

import java.util.regex.Pattern;

public class EmailViewModel extends AndroidViewModel {
    private Application application;
    public EmailViewModel(@NonNull Application application) {
        super(application);
        this.application=application;
    }

    public void validateEmail(String email, FragmentEmailBinding emailBinding) {
        if(TextUtils.isEmpty(email))
        {
            emailBinding.next.setEnabled(false);
            emailBinding.next.setBackground(ContextCompat.getDrawable(application, R.drawable.button_disable));
        }
        else if(!isValid(email))
        {
            emailBinding.next.setEnabled(false);
            emailBinding.next.setBackground(ContextCompat.getDrawable(application, R.drawable.button_disable));
        }
        else {
            emailBinding.next.setEnabled(true);
            emailBinding.next.setBackground(ContextCompat.getDrawable(application, R.drawable.button_style));
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
}
