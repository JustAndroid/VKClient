package com.test.vkclient.model;

/**
 * Created by Павел on 08.02.2016.
 */
public class City {
    private int id;
    private String title;

    public City (String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
