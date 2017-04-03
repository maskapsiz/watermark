package com.watermark.entity;

/**
 * Created by inazlim on 03/04/17.
 */
public class Journal extends Document {

    @Override
    public String toString() {
        return "{" +
                "content:\"" + getClassName() + "\"," +
                " title:\"" + getTitle() + "\"," +
                " author:\"" + getAuthor() + "\"" +
                "}";
    }
}
