package com.coreen.popularmovies.utility

enum class Constants(val value: String) {
    MOVIE_DB_BASE_URL("http://api.themoviedb.org/"),
    API_KEY_QUERY("api_key"),

    IMAGE_BASE_URL("http://image.tmdb.org/t/p/"),
    IMAGE_DEFAULT_POSTER_SIZE("w185"),

    YOUTUBE_BASE_URL("https://www.youtube.com/watch"),
    VIDEO_KEY_QUERY("v"),

    EXTRA_MOVIE("movie"),
    EXTRA_MOVIE_ID("movieId")
}