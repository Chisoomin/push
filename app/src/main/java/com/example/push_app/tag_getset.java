package com.example.push_app;

public class tag_getset {
    String tag, date;

    public tag_getset(String tag, String date) {
        this.tag = tag;
        this.date = date;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
