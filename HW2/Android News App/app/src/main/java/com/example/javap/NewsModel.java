package com.example.javap;

class NewsModel {
    int id;
    String title;
    String details;
    String categoryName;
    String imagePath;
    String date;

    public NewsModel(int id, String title, String details, String categoryName, String imageField, String date) {
        this.id = id;
        this.title = title;
        this.details = details;
        this.categoryName = categoryName;
        this.imagePath = imageField;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
