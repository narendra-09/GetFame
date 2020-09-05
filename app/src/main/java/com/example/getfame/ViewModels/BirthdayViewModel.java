package com.example.getfame.ViewModels;

import android.app.Application;
import android.text.TextUtils;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;

import com.example.getfame.R;
import com.example.getfame.databinding.FragmentBirthdayBinding;

import java.util.Calendar;

public class BirthdayViewModel extends AndroidViewModel {
    private Application application;
    public BirthdayViewModel(@NonNull Application application) {
        super(application);
        this.application=application;
    }
    public void selectDate(DatePicker datePicker, EditText birthday)
    {
        Calendar calendar = Calendar.getInstance();
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), (datePicker1, year, month, dayOfMonth) -> {
            int mMonth=month+1;
            String finalDate=dayOfMonth+"/"+mMonth+"/"+year;
            birthday.setText(finalDate);
        });
    }

    public void validateBirthday(String birthday, FragmentBirthdayBinding birthdayBinding) {
        if(TextUtils.isEmpty(birthday))
        {
            birthdayBinding.next.setEnabled(false);
            birthdayBinding.next.setBackground(ContextCompat.getDrawable(application, R.drawable.button_disable));
        }
        else {
            birthdayBinding.next.setEnabled(true);
            birthdayBinding.next.setBackground(ContextCompat.getDrawable(application, R.drawable.button_style));
        }
    }
}
