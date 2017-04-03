package com.watermark.entity;

/**
 * Created by inazlim on 03/04/17.
 */
public class Book extends Document {

    private String topic;

    private String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public String toString() {
        return "{" +
                "content:\"" + getClassName() + "\"," +
                " title:\"" + getTitle() + "\"," +
                " author:\"" + getAuthor() + "\"," +
                " topic:\"" + getTopic() + "\"" +
                "}";
    }
}
