package com.example.getfame.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.getfame.R;
import com.example.getfame.databinding.ActivityFriendsBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FriendsActivity extends AppCompatActivity {
private ActivityFriendsBinding friendsBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        friendsBinding=ActivityFriendsBinding.inflate(getLayoutInflater());
        View view=friendsBinding.getRoot();
        setContentView(view);
        friendsBinding.friendsNav.setItemIconTintList(null);
        openPendingRequestFragment();
        friendsBinding.friendsNav.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId())
            {
                case R.id.item1:
                    openPendingRequestFragment();
                    break;
                case R.id.item2:
                    openSentRequestsFragment();
                    break;
                case R.id.item3:
                    openShowFriendsFragment();
                    break;
            }
            return true;
        });
    }
    private void openPendingRequestFragment()
    {
       FragmentManager fragmentManager=getSupportFragmentManager();
       FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        PendingRequests pendingRequests=new PendingRequests();
        fragmentTransaction.replace(R.id.friends_frame,pendingRequests);
        fragmentTransaction.commit();
    }
    private void openSentRequestsFragment()
    {
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        SentRequests sentRequests=new SentRequests();
        fragmentTransaction.replace(R.id.friends_frame,sentRequests);
        fragmentTransaction.commit();
    }
    private void openShowFriendsFragment()
    {
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        ShowFriends showFriends=new ShowFriends();
        fragmentTransaction.replace(R.id.friends_frame,showFriends);
        fragmentTransaction.commit();
    }
}