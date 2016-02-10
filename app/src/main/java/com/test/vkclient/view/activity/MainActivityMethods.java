package com.test.vkclient.view.activity;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;

public interface MainActivityMethods {
    public void switchFragment(Fragment fragment);
    public void switchFragment(Fragment fragment, boolean addToFragmentList);
    public void removeFragment(Fragment fragment);
    public void back();
    public void checkConnection();
}
