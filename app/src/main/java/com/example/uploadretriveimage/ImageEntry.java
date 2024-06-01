package com.example.uploadretriveimage;

public class ImageEntry {
    private String id;
    private String imageUrl;
    private String caption;

    public ImageEntry() {
        // Required empty constructor for Firebase
    }

    public ImageEntry(String id, String imageUrl, String caption) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.caption = caption;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}