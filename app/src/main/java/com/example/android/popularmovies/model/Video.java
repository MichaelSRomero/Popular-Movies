package com.example.android.popularmovies.model;

public class Video {

    private String mVideoKey;
    private String mName;
    private String mSite;
    private int mSize;

    private final String YOUTUBE_BASE_URL = "https://www.youtube.com/watch?v=";
    private final String THUMBNAIL_BASE_URL = "https://img.youtube.com/vi/";
    private final String THUMBNAIL_QUALITY_URL = "/hqdefault.jpg";

    public Video(String key, String name, String site, int size) {
        mVideoKey = key;
        mName = name;
        mSite = site;
        mSize = size;
    }

    public String getVideo() {
        return YOUTUBE_BASE_URL + mVideoKey;
    }

    public String getVideoName() {
        return mName;
    }

    public String getVideoSite() {
        return mSite;
    }

    public int getVideoSize() {
        return mSize;
    }

    public String getThumbnail() {
        return THUMBNAIL_BASE_URL + mVideoKey + THUMBNAIL_QUALITY_URL;
    }
}
