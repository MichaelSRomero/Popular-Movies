package com.example.android.popularmovies.data;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.example.android.popularmovies.model.Video;
import com.example.android.popularmovies.utils.VideoQuery;

import java.util.List;

public class VideoLoader extends AsyncTaskLoader<List<Video>> {

    /** Query URL*/
    private String mUrl;

    public VideoLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Video> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        return VideoQuery.fetchVideoData(mUrl);
    }
}
