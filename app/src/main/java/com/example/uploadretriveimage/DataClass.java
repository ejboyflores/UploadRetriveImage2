package com.example.uploadretriveimage;
public class DataClass {
    private String imageUrl;
    private String caption;
    private String key;

    public DataClass() {
    }

    public DataClass(String imageUrl, String caption, String key) {
        this.imageUrl = imageUrl;
        this.caption = caption;
        this.key = key;
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
