package com.watermark.entity;

/**
 * Created by inazlim on 03/04/17.
 */
public class Document {

    private String title;
    private String author;
    private String watermark;

    String getClassName() {
        return this.getClass().getSimpleName().toLowerCase();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getWatermark() {
        return watermark;
    }

    public void setWatermark(String watermark) {
        this.watermark = watermark;
    }
}
