package com.example.sachin.hackernews;

public class HackerFeed {

    private String title;
    private String external_url;
    private String date_pub;
    private String author;

    public HackerFeed(String title, String author, String date_pub, String external_url) {
        this.title = title;
        this.external_url = external_url;
        this.date_pub = date_pub;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }


    public String getExternal_url() {
        return external_url;
    }


    public String getDate_pub() {
        return date_pub;
    }


    public String getAuthor() {
        return author;
    }


    @Override
    public String toString() {
        return "HackerFeed{" +
                "title='" + title + '\'' +
                ", external_url='" + external_url + '\'' +
                ", date_pub='" + date_pub + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
