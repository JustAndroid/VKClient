package com.test.vkclient.model;

import java.util.ArrayList;

/**
 * Created by Павел on 08.02.2016.
 * Обьект ответа от wall.get
 */
public class WallResponse {
    private int count;
    private ArrayList<Post> items;
    private ArrayList<User> profiles;

    public int getCount() {
        return count;
    }

    public ArrayList<Post> getItems() {
        return items;
    }

    public ArrayList<User> getProfiles() {
        return profiles;
    }
}
