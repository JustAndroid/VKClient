package com.test.vkclient.view.activity;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.test.vkclient.R;
import com.test.vkclient.view.fragment.login.LoginFragment;
import com.test.vkclient.view.fragment.mainscreen.MainFragment;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements MainActivityMethods {

    private List<Fragment> fragmentsList = new ArrayList<Fragment>();
    private boolean backOperation = false;
    private boolean isLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        if (!VKSdk.isLoggedIn()) {
            switchFragment(new LoginFragment());
        }
        else {
            switchFragment(new MainFragment());
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            VKSdk.logout();
            switchFragment(new LoginFragment());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Методы для переключения фрагментов со своим стеком фрагментов
    @Override
    public void switchFragment(Fragment fragment) {
        switchFragment(fragment, true);
    }

    @Override
    public void switchFragment(Fragment fragment, boolean addToFragmentList) {

        if (addToFragmentList) {
            if (!fragmentsList.contains(fragment)) {
                fragmentsList.add(fragment);
            }
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.flContent, fragment).commit();
        backOperation = false;
    }

    @Override
    public void removeFragment(Fragment fragment) {
        if (fragmentsList.contains(fragment)) {
            fragmentsList.remove(fragment);
        }
    }

    @Override
    public void back() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        int fragmentsListSize = 0;
        if ((fragmentsListSize = fragmentsList.size()) - 1 > 0) {
            /**
             * remove last saved fragment in list
             */
            fragmentsList.remove(fragmentsListSize - 1);
            /**
             * set flag backOperation to true
             * switch to last after removing
             */
            backOperation = true;
            switchFragment(fragmentsList.get(fragmentsListSize - 2));
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public void checkConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (!(netInfo != null && netInfo.isConnectedOrConnecting())) {
            Toast.makeText(this, R.string.connection, Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this, R.string.servererror, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (isLogin) {
            switchFragment(new MainFragment());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                isLogin = true;
            }
            @Override
            public void onError(VKError error) {
                // User didn't pass Authorization
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
