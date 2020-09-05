package com.example.getfame.ViewModels;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.example.getfame.R;
import com.example.getfame.databinding.FragmentUsernameBinding;

public class UsernameViewModel extends AndroidViewModel {
    private Application application;
    public UsernameViewModel(@NonNull Application application) {
        super(application);
        this.application=application;
    }

    public void validateFirstName(String firstName, FragmentUsernameBinding usernameBinding) {
        if(TextUtils.isEmpty(firstName))
        {
            usernameBinding.next.setEnabled(false);
            usernameBinding.next.setBackground(ContextCompat.getDrawable(application,R.drawable.button_disable));
        }
        else
        {
            usernameBinding.next.setEnabled(true);
            usernameBinding.next.setBackground(ContextCompat.getDrawable(application,R.drawable.button_style));
        }

    }
}
