package com.example.android.popularmovies.utils;

import android.text.TextUtils;
import android.util.Log;

import com.example.android.popularmovies.model.Video;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.android.popularmovies.activities.MainActivity.LOG_TAG;

public class VideoQuery {

    /** Private constructor to prevent from creating an instance of this class*/
    private VideoQuery() {}

    /**
     *
     * @param videoUrl          used to parse to JSONObject
     * @return                  a list of Video objects
     */
    private static List<Video> extractVideo(String videoUrl) {

        if (TextUtils.isEmpty(videoUrl)) {
            return null;
        }

        List<Video> videoList = new ArrayList<>();

        try {
            // Create a JSONObject from the JSON response string
            JSONObject baseJson = new JSONObject(videoUrl);

            JSONArray videoArray = baseJson.getJSONArray("results");

            // For each video in the videoArray, create a video object
            for (int i = 0; i < videoArray.length(); i++) {

                // Get a single movie at position i within the list of videos
                JSONObject currentVideo = videoArray.getJSONObject(i);

                String key = currentVideo.getString("key");
                String name = currentVideo.getString("name");
                String site = currentVideo.getString("site");
                int size = currentVideo.getInt("size");

                Video video = new Video(key,
                        name,
                        site,
                        size);

                videoList.add(video);
            }

        } catch (JSONException e) {
            Log.e("Video Utils", "Problem parsing the video JSON results", e);
        }

        return videoList;
    }

    /**
     *
     * @param requestUrl        used to query data
     * @return                  a list of Video objects
     */
    public static List<Video> fetchVideoData(String requestUrl) {
        URL url = QueryUtils.createUrl(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = QueryUtils.getResponseFromHttpUrl(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract details from the JSON response and create a list of Videos
        List<Video> videos = extractVideo(jsonResponse);

        return videos;
    }
}
