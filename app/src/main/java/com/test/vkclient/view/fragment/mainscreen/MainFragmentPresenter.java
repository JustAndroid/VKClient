package com.test.vkclient.view.fragment.mainscreen;

import com.test.vkclient.utils.DataBaseHelper;

/**
 * Created by Павел on 09.02.2016.
 */
public interface MainFragmentPresenter {
    void callGetUserInfo(DataBaseHelper dataBaseHelper);
    void callGetWall(int count, int offset);
}
