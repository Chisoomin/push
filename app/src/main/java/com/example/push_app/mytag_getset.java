package com.example.push_app;

public class mytag_getset {
    String mytag, tagdate;

    public mytag_getset(String mytag, String tagdate) {
        this.mytag = mytag;
        this.tagdate = tagdate;
    }

    public String getMytag() {
        return mytag;
    }

    public void setMytag(String mytag) {
        this.mytag = mytag;
    }

    public String getTagdate() {
        return tagdate;
    }

    public void setTagdate(String tagdate) {
        this.tagdate = tagdate;
    }
}
