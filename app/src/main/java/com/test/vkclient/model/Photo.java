package com.test.vkclient.model;

import android.text.TextUtils;

import org.w3c.dom.Text;

/**
 * Created by Павел on 09.02.2016.
 */
public class Photo {
    private int id;
    private String photo_75;
    private String photo_130;
    private String photo_604;
    private String photo_807;

    public int getId() {
        return id;
    }

    public String getPhoto() {
        if (!TextUtils.isEmpty(photo_807)) {
            return photo_807;
        }
        else if (!TextUtils.isEmpty(photo_604)) {
            return photo_604;
        }
        else if (!TextUtils.isEmpty(photo_130)) {
            return photo_130;
        }
        else {
            return photo_75;
        }
    }
}
