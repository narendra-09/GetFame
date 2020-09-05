package com.example.getfame.Models;

public class ImageDetails {
    private String imageUrl;
    private String imageName;

    public ImageDetails() {
    }

    public ImageDetails(String imageUrl, String imageName) {
        this.imageUrl = imageUrl;
        this.imageName = imageName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getImageName() {
        return imageName;
    }
}
