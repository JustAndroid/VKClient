package com.test.vkclient.view.fragment.mainscreen;

import com.test.vkclient.model.User;
import com.test.vkclient.model.WallResponse;

/**
 * Created by Павел on 09.02.2016.
 */
public interface MainFragmentView {
     void setUserInfo(User user);
     void stopRefresh();
     void setWallAdapter(WallResponse wallResponse);
     void notifyAdapter();
}
