package com.example.movie.Fragments;
import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.example.movie.Adapter.MoviesAdapter;
import com.example.movie.Adapter.MoviesAdapterFailure;
import com.example.movie.Adapter.MoviesRepository;
import com.example.movie.BuildConfig;
import com.example.movie.Database.DatabaseHelper;
import com.example.movie.Interfaces.OnGetMoviesCallBack;
import com.example.movie.Model.Movie;
import com.example.movie.Model.MovieResponse;
import com.example.movie.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    MoviesAdapter adapter;
    MoviesAdapterFailure failureadapter;
    private MoviesRepository moviesRepository;

    public HomeFragment() {
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_home, container, false);

        Bundle bundle = this.getArguments();
        final int id= bundle.getInt("id");

        recyclerView = view.findViewById(R.id.movies_list_rv);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //movies from the moviedb
        moviesRepository = MoviesRepository.getInstance();
        moviesRepository.getMovies(new OnGetMoviesCallBack() {
            @Override
            public void onSuccess(List<Movie> movies) {

                adapter = new MoviesAdapter(movies);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onError() {
               // failureadapter = new MoviesAdapterFailure();
                //recyclerView.setAdapter(failureadapter);
                Toast.makeText(getContext(), "Please check your internet connection.", Toast.LENGTH_SHORT).show();

            }
        });

      //  failureadapter = new MoviesAdapterFailure();
        //recyclerView.setAdapter(failureadapter);



        //the menu bar
        BottomNavigationView bottomNavigationView = view.findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.action_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case  R.id.action_home:
                        Toast.makeText(getContext(), "Home", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_fav:

                        break;
                    case R.id.action_profile:
                        Bundle bundleProfile = new Bundle();
                        bundleProfile.putInt("id",id);
                        Toast.makeText(getContext(), "Profile", Toast.LENGTH_SHORT).show();
                        ProfileFragment profileFragment = new ProfileFragment();
                        profileFragment.setArguments(bundleProfile);
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.container,profileFragment);
                        ft.addToBackStack(null);
                        ft.commit();
                        break;
                }
                return true;
            }
        });

        return view;
    }


}
