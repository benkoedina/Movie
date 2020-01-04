package com.example.movie.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movie.R;

import androidx.recyclerview.widget.RecyclerView;

public class MoviesAdapterFailure extends RecyclerView.Adapter<MoviesAdapterFailure.MovieViewHolder> {
    //if not works the api
    public MoviesAdapterFailure() {
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 50;
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        public MovieViewHolder(View itemView) {
            super(itemView);
        }
    }
}
