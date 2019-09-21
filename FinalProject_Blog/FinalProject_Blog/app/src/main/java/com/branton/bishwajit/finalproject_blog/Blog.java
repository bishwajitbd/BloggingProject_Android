package com.branton.bishwajit.finalproject_blog;

public class Blog {
    public String title;
    public String description;
    public String image;
    public String timestamp;
    public String user_id;

    public Blog() {
    }

    public Blog(String title, String description, String image, String timestamp, String user_id) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.timestamp = timestamp;
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
