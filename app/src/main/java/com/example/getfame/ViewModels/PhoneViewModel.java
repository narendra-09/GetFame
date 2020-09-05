package com.example.getfame.ViewModels;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;

import com.example.getfame.R;
import com.example.getfame.databinding.FragmentPhoneBinding;

public class PhoneViewModel extends AndroidViewModel {
    private Application application;
    public PhoneViewModel(@NonNull Application application) {
        super(application);
        this.application=application;
    }

    public void validatePhone(String phone, FragmentPhoneBinding phoneBinding) {
        if(TextUtils.isEmpty(phone))
        {
            phoneBinding.next.setEnabled(false);
            phoneBinding.next.setBackground(ContextCompat.getDrawable(application, R.drawable.button_disable));
        }
        else if(phone.length()!=10)
        {
            phoneBinding.next.setEnabled(false);
            phoneBinding.next.setBackground(ContextCompat.getDrawable(application, R.drawable.button_disable));
        }
        else {
            phoneBinding.next.setEnabled(true);
            phoneBinding.next.setBackground(ContextCompat.getDrawable(application, R.drawable.button_style));
        }
    }
}
