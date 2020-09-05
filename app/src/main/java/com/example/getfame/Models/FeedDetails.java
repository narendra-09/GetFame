package com.example.getfame.Models;

import java.io.Serializable;

public class FeedDetails implements Serializable {
    String name;
    String profile_pic;
    String actual_image;
    long imageName;
    long likes;
    public FeedDetails() {
    }

    public FeedDetails(String name, String profile_pic, String actual_image) {
        this.name = name;
        this.profile_pic = profile_pic;
        this.actual_image = actual_image;
    }

    public FeedDetails(String name, String profile_pic, String actual_image, long imageName) {
        this.name = name;
        this.profile_pic = profile_pic;
        this.actual_image = actual_image;
        this.imageName = imageName;
    }

    public FeedDetails(String name, String profile_pic, String actual_image, long imageName, long likes) {
        this.name = name;
        this.profile_pic = profile_pic;
        this.actual_image = actual_image;
        this.imageName = imageName;
        this.likes = likes;
    }

    public String getName() {
        return name;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public String getActual_image() {
        return actual_image;
    }

    public long getLikes() {
        return likes;
    }

    public long getImageName() {
        return imageName;
    }

}
