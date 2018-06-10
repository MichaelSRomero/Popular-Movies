package com.example.android.popularmovies.utils;

import android.text.TextUtils;
import android.util.Log;

import com.example.android.popularmovies.model.Review;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.android.popularmovies.activities.MainActivity.LOG_TAG;

public class ReviewQuery {

    /** Private constructor to prevent from creating an instance of this class*/
    private ReviewQuery() {}

    /**
     *
     * @param reviewUrl          used to parse to JSONObject
     * @return                   a list of Review objects
     */
    private static List<Review> extractReview(String reviewUrl) {

        if (TextUtils.isEmpty(reviewUrl)) {
            return null;
        }

        List<Review> reviewList = new ArrayList<>();

        try {
            // Create a JSONObject from the JSON response string
            JSONObject baseJson = new JSONObject(reviewUrl);

            JSONArray reviewArray = baseJson.getJSONArray("results");

            // For each review in the reviewArray, create a review object
            for (int i = 0; i < reviewArray.length(); i++) {

                // Get a single review at position i within the list of reviews
                JSONObject currentReview = reviewArray.getJSONObject(i);

                String author = currentReview.getString("author");
                String content = currentReview.getString("content");

                Review review = new Review(author, content);

                reviewList.add(review);
            }

        } catch (JSONException e) {
            Log.e("Review Utils", "Problem parsing the review JSON results", e);
        }

        return reviewList;
    }

    /**
     *
     * @param requestUrl        used to query data
     * @return                  a list of Review objects
     */
    public static List<Review> fetchReviewData(String requestUrl) {
        URL url = QueryUtils.createUrl(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = QueryUtils.getResponseFromHttpUrl(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract details from the JSON response and create a list of Reviews
        List<Review> reviews = extractReview(jsonResponse);

        return reviews;
    }
}
