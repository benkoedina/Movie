package com.example.movie.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.movie.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class HomeFragment extends Fragment {

    public HomeFragment() {
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Bundle bundle = this.getArguments();
        final int id= bundle.getInt("id");

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
