package com.example.getfame.Models;

public class FeedImages {
    String name;
    String profile_pic;
    String image;
    String image_name;
    public FeedImages() {
    }

    public FeedImages(String name, String profile_pic, String image) {
        this.name = name;
        this.profile_pic = profile_pic;
        this.image = image;
    }

    public FeedImages(String name, String profile_pic, String image, String image_name) {
        this.name = name;
        this.profile_pic = profile_pic;
        this.image = image;
        this.image_name = image_name;
    }

    public String getName() {
        return name;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public String getImage() {
        return image;
    }

    public String getImage_name() {
        return image_name;
    }
}
