package com.test.vkclient.model;

import android.provider.ContactsContract;

/**
 * Created by Павел on 09.02.2016.
 * Дополнения к постам: фото, видео, аудио, т.д
 */
public class Attachments {
    private String type;
    private Photo photo;

    public String getType() {
        return type;
    }

    public Photo getPhoto() {
        return photo;
    }
}
