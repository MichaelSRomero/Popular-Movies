package com.example.android.popularmovies.utils;

import android.text.TextUtils;
import android.util.Log;

import com.example.android.popularmovies.model.Movie;
import com.example.android.popularmovies.model.Video;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.example.android.popularmovies.activities.MainActivity.LOG_TAG;

public final class QueryUtils {

    /** Private constructor to prevent from creating an instance of this class*/
    private QueryUtils() {}

    /**
     *
     * @param movieJson         used to parse to JSONObject
     * @return                  a list of Movie objects
     */
    private static List<Movie> extractResultsFromJson(String movieJson) {

        if (TextUtils.isEmpty(movieJson)) {
            return null;
        }

        List<Movie> movieList = new ArrayList<>();

        try {
            // Create a JSONObject from the JSON response string
            JSONObject baseJson = new JSONObject(movieJson);

            JSONArray movieArray = baseJson.getJSONArray("results");

            // For each movie in the moveArray, create a movie object
            for (int i = 0; i < movieArray.length(); i++) {

                // Get a single movie at position i within the list of movies
                JSONObject currentMovie = movieArray.getJSONObject(i);

                int movieId = currentMovie.getInt("id");
                String voteAverage = currentMovie.getString("vote_average");
                String posterPath = currentMovie.getString("poster_path");
                String originalTitle = currentMovie.getString("title");
                String overview = currentMovie.getString("overview");
                String releaseDate = currentMovie.getString("release_date");
                String backdrop = currentMovie.getString("backdrop_path");

                Movie movie = new Movie(movieId,
                        originalTitle,
                        posterPath,
                        overview,
                        voteAverage,
                        releaseDate,
                        backdrop);

                movieList.add(movie);
            }

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the movie JSON results", e);
        }

        return movieList;
    }

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

                Video video = new Video(key,
                        name,
                        site);

                videoList.add(video);
            }

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the video JSON results", e);
        }

        return videoList;
    }

    /**
     * Returns new URL object from the given string URL
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     *
     * @param url                   to fetch the HTTP response from
     * @return                      the contents of the HTTP response
     * @throws IOException          related to network and stream reading
     */
    private static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream inputStream = urlConnection.getInputStream();

            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    /**
     *
     * @param requestUrl        used to query data
     * @return                  a list of Movie objects
     */
    public static List<Movie> fetchMovieData(String requestUrl) {
        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = getResponseFromHttpUrl(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract details from the JSON response and create a list of Movies
        List<Movie> movies = extractResultsFromJson(jsonResponse);

        return movies;
    }

    /**
     *
     * @param requestUrl        used to query data
     * @return                  a list of Video objects
     */
    public static List<Video> fetchVideoData(String requestUrl) {
        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = getResponseFromHttpUrl(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract details from the JSON response and create a list of Movies
        List<Video> videos = extractVideo(jsonResponse);

        return videos;
    }
}
