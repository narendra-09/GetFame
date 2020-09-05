package com.example.getfame.View;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.getfame.R;
import com.example.getfame.ViewModels.UsernameViewModel;
import com.example.getfame.databinding.FragmentUsernameBinding;

public class UsernameFragment extends Fragment {
private FragmentUsernameBinding usernameBinding;
private UsernameViewModel usernameViewModel;
private Bundle bundle;
    public UsernameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        usernameBinding=FragmentUsernameBinding.inflate(inflater,container,false);
        View view=usernameBinding.getRoot();
        bundle=new Bundle();
        usernameBinding.next.setEnabled(false);
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        assert appCompatActivity != null;
        usernameBinding.next.setBackground(ContextCompat.getDrawable(appCompatActivity,R.drawable.button_disable));
        usernameViewModel=new UsernameViewModel(appCompatActivity.getApplication());
        usernameBinding.firstname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
              usernameViewModel.validateFirstName(usernameBinding.firstname.getText().toString(),
                                                   usernameBinding);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        usernameBinding.next.setOnClickListener(view1 ->  openBirthdayFragment() );
        return view;
    }
    private void openBirthdayFragment()
    {
        bundle.putString("username",usernameBinding.firstname.getText().toString()+usernameBinding.lastname.getText().toString());
        FragmentManager fragmentManager=getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        BirthdayFragment birthdayFragment=new BirthdayFragment();
        birthdayFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.frame_layout,birthdayFragment);
        fragmentTransaction.addToBackStack("UsernameFragment");
        fragmentTransaction.commit();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}