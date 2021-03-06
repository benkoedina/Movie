package com.example.movie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.movie.Database.DatabaseHelper;
import com.example.movie.Fragments.LoginFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHelper db;
        db = new DatabaseHelper(this);
       // db.deleteAllUser();
        if (savedInstanceState == null)
        {
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.container, new LoginFragment()).commit();
        }

    }
}
