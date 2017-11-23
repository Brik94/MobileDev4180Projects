package com.example.test.hw05;

import java.io.Serializable;

/**
 * Assignment: HW5
 * Group 5: Scott Schreiber & Brianna Kirkpatrick
 */

public class Podcast implements Serializable, Cloneable {
    String title, summary, releaseDate, smallImgUrl, largeImgUrl, updated;
    int highlight;

    public Podcast(String title, String summary, String releaseDate, String updated, String smallImgUrl, String largeImgUrl) {
        this.title = title;
        this.summary = summary;
        this.releaseDate = releaseDate;
        this.updated = updated;
        this.smallImgUrl = smallImgUrl;
        this.largeImgUrl = largeImgUrl;
        highlight = 0;
    }
    public Podcast(){

    }

    public int getHighlight() {
        return highlight;
    }

    public void setHighlight(int highlight) {
        this.highlight = highlight;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "Podcast{" +
                "title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", updated='" + releaseDate + '\'' +
                ", smallImgUrl='" + smallImgUrl + '\'' +
                ", largeImgUrl='" + largeImgUrl + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }
}
