package com.example.movie.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movie.Database.DatabaseHelper;
import com.example.movie.Model.User;
import com.example.movie.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {
    DatabaseHelper db;
    ImageView image;
    private  static String path;
    private static final int RESULT_LOAD_IMAGE=1;
    private  static  User user;
    public ProfileFragment(){};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        TextView tv_name = view.findViewById(R.id.name);
        final TextView tv_password = view.findViewById(R.id.password);

        Button bt_modifyPhoto = view.findViewById(R.id.bt_modifyPhoto);
        Button bt_modifyPassword = view.findViewById(R.id.bt_modifyPw);

        image = view.findViewById(R.id.imageSet);
        String photoPath;
        db = new DatabaseHelper(getContext());


        Bundle bundle = this.getArguments();
        final int id= bundle.getInt("id");


        user = db.getUser(id);

        tv_name.setText(user.getName());
        tv_password.setText(user.getPassword());

        photoPath = user.getImage_path();
        Log.d("path1", photoPath+"0");
        Uri uri = Uri.parse(photoPath);
        image.setImageURI(uri);

        bt_modifyPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View viewDialog = LayoutInflater.from(getContext()).inflate(R.layout.alert_layout, null);
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setView(viewDialog);
                builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText et_pw = viewDialog.findViewById(R.id.newPassword);

                        user.setPassword(et_pw.getText().toString());
                        db.updateUserPassword(user);
                        tv_password.setText(et_pw.getText().toString());
                    }
                });

                AlertDialog d = builder.create();
                d.show();

            }
        });

        bt_modifyPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallery,RESULT_LOAD_IMAGE);

            }
        });
        BottomNavigationView bottomNavigationView = view.findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.action_profile);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        Bundle bundleHome = new Bundle();
                        bundleHome.putInt("id",id);
                        Toast.makeText(getContext(), "Home", Toast.LENGTH_SHORT).show();
                        HomeFragment homeFragment = new HomeFragment();
                        homeFragment.setArguments(bundleHome);
                        FragmentTransaction fh = getFragmentManager().beginTransaction();
                        fh.replace(R.id.container,homeFragment);
                        fh.addToBackStack(null);
                        fh.commit();
                        break;
                    case R.id.action_fav:

                        break;
                    case R.id.action_profile:
                        Toast.makeText(getContext(), "Profile", Toast.LENGTH_SHORT).show();
                        ProfileFragment profileFragment = new ProfileFragment();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data!=null)
        {
            Uri selectedImage = data.getData();
            path = selectedImage.toString();
            Log.d("path3", path +"0");
            image.setImageURI(selectedImage);
            user.setImage_path(path);
            Log.d("path2", path+"0");
            db.updateUserPhoto(user);
        }
    }
}
