package com.test.vkclient.model;

/**
 * Created by Павел on 08.02.2016.
 */
public class User {
    private int id;
    private String first_name;
    private String last_name;
    private City city;
    private String photo_max;
    private String photo_100;

    public int getId() {
        return id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public City getCity() {
        return city;
    }

    public String getPhoto_max() {
        return photo_max;
    }

    public String getPhoto_100() {
        return photo_100;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public void setPhoto_max(String photo_max) {
        this.photo_max = photo_max;
    }

    public void setPhoto_100(String photo_100) {
        this.photo_100 = photo_100;
    }
}
