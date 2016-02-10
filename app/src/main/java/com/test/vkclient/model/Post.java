package com.test.vkclient.model;

import com.vk.sdk.api.VKApi;

import java.util.ArrayList;

/**
 * Created by Павел on 08.02.2016.
 * Обьект поста на стене
 */
public class Post {
    private int id;
    private int from_id;
    private int owner_id;
    private long date;
    private String text;
    private String post_type;
    private ArrayList<Attachments> attachments;

    public int getId() {
        return id;
    }

    public int getFrom_id() {
        return from_id;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public long getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    public String getPost_type() {
        return post_type;
    }

    public ArrayList<Attachments> getAttachments() {
        return attachments;
    }
}
