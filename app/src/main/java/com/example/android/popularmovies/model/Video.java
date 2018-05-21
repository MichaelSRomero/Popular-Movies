package com.example.android.popularmovies.model;

public class Video {

    private String mVideoKey;
    private String mName;
    private String mSite;

    private final String YOUTUBE_BASE_URL = "https://www.youtube.com/watch?v=";

    public Video(String key, String name, String site) {
        mVideoKey = key;
        mName = name;
        mSite = site;
    }

    public String getVideo() {
        return mVideoKey;
    }

    public String getName() {
        return mName;
    }

    public String getSite() {
        return mSite;
    }
}
