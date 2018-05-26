package com.example.android.popularmovies.utils;

import android.text.TextUtils;
import android.util.Log;

import com.example.android.popularmovies.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.android.popularmovies.activities.MainActivity.LOG_TAG;

public class MovieQuery {

    /** Private constructor to prevent from creating an instance of this class*/
    private MovieQuery() {}

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
     * @param requestUrl        used to query data
     * @return                  a list of Movie objects
     */
    public static List<Movie> fetchMovieData(String requestUrl) {
        URL url = QueryUtils.createUrl(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = QueryUtils.getResponseFromHttpUrl(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract details from the JSON response and create a list of Movies
        List<Movie> movies = extractResultsFromJson(jsonResponse);

        return movies;
    }
}
