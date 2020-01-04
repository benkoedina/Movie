package com.example.movie.Adapter;

import android.util.Log;

import com.example.movie.Interfaces.OnGetMoviesCallBack;
import com.example.movie.Interfaces.TMDbApi;
import com.example.movie.Model.MovieResponse;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MoviesRepository {
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String LANGUAGE = "en-US";

    private static MoviesRepository repository;

    private TMDbApi api;

    private MoviesRepository(TMDbApi api) {
        this.api = api;
    }

    public static MoviesRepository getInstance() {
        if (repository == null) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            repository = new MoviesRepository(retrofit.create(TMDbApi.class));

        }
        return repository;
    }

    public void getMovies(final OnGetMoviesCallBack callback) {

        api.getPopularMovies("e847333c689f1c2cfb34e96de790043e", LANGUAGE, 1).enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                        if (response.isSuccessful()) {

                            MovieResponse moviesResponse = response.body();
                            if (moviesResponse != null && moviesResponse.getResults() != null) {
                                callback.onSuccess(moviesResponse.getResults());
                            } else {
                                callback.onError();
                            }
                        } else {
                            callback.onError();

                        }
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {
                        callback.onError();

                    }
                });

    }
}
