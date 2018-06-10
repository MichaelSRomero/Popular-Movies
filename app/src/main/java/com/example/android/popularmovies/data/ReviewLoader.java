package com.example.android.popularmovies.data;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;

import com.example.android.popularmovies.model.Review;
import com.example.android.popularmovies.utils.ReviewQuery;

import java.util.List;

public class ReviewLoader extends AsyncTaskLoader<List<Review>> {

    /** Query URL*/
    private String mUrl;

    public ReviewLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Review> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        List<Review> reviewList = ReviewQuery.fetchReviewData(mUrl);
        return reviewList;
    }
}
