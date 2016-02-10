package com.test.vkclient;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.test.vkclient.R;
import com.test.vkclient.view.fragment.mainscreen.MainFragment;

/**
 * Created by Павел on 10.02.2016.
 */
public class MainFragmentActivity extends FragmentActivity {

    public MainFragment myFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        myFragment = new MainFragment();
        fragmentTransaction.add(R.id.flContent, myFragment);
        fragmentTransaction.commit();
    }
}

