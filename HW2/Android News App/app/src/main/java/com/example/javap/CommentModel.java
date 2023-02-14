package com.example.javap;

public class CommentModel {
    int id;
    int newsId;
    String username;
    String comment;

    public CommentModel(int id, int newsId, String username, String comment) {
        this.id = id;
        this.newsId = newsId;
        this.username = username;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public int getNewsId() {
        return newsId;
    }

    public String getUsername() {
        return username;
    }

    public String getComment() {
        return comment;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
