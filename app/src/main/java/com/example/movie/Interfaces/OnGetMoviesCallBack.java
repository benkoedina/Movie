package com.example.movie.Interfaces;

import com.example.movie.Model.Movie;

import java.util.List;

public interface OnGetMoviesCallBack {
    void onSuccess(List<Movie> movies);

    void onError();
}
