package com.example.getfame.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.getfame.R;
import com.example.getfame.databinding.ActivityHomeBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
private ActivityHomeBinding homeBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeBinding=ActivityHomeBinding.inflate(getLayoutInflater());
        View view=homeBinding.getRoot();
        setContentView(view);
        homeBinding.bottomNavigation.setItemIconTintList(null);
        openFeedFragment();
        homeBinding.bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId())
            {
                case R.id.item1:
                    openFeedFragment();
                    break;
                case R.id.item2:
                    openSearchFragment();
                    break;
                case R.id.item3:
                    openCameraActivity();
                    break;
                case R.id.item4:
                    break;
                case R.id.item5:
                    openAccountFragment();
            }
            return true;
        });
    }

    private void openAccountFragment() {
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        AccountFragment accountFragment=new AccountFragment();
        fragmentTransaction.replace(homeBinding.frame.getId(),accountFragment);
        fragmentTransaction.commit();
    }

    private void openCameraActivity()
    {
        startActivity(new Intent(this,CameraActivity.class));
    }
    private void openFeedFragment()
    {
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        FeedFragment feedFragment=new FeedFragment();
        fragmentTransaction.replace(homeBinding.frame.getId(),feedFragment);
        fragmentTransaction.commit();
    }
    private void openSearchFragment()
    {
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        SearchFragment searchFragment=new SearchFragment();
        fragmentTransaction.replace(homeBinding.frame.getId(),searchFragment);
        fragmentTransaction.commit();
    }
}