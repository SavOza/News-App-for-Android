package com.sabanciuniv.cs310news;

import java.time.LocalDateTime;

public class News {
    private int id;
    private String title, text, imagePath, category;
    private LocalDateTime date;

    public News(int id, String title, String text, String dateString, String imagePath, String category) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.imagePath = imagePath;
        this.category = category;

        this.date = LocalDateTime.parse(dateString.substring(0,dateString.length()-6));
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
