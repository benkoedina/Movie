package com.example.movie.Fragments;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.movie.Database.DatabaseHelper;
import com.example.movie.Model.User;
import com.example.movie.R;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import static android.app.Activity.RESULT_OK;

public class RegisterFragment extends Fragment {

    private static final int RESULT_LOAD_IMAGE=1;
    private static String path;
    ImageView image;
    public  RegisterFragment (){}
    DatabaseHelper db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register, container, false);

        final EditText et_username = view.findViewById(R.id.et_username);
        final EditText et_password = view.findViewById(R.id.et_password);


        image = view.findViewById(R.id.image);
        Button bt_register = view.findViewById(R.id.bt_register);
        Button bt_upload = view.findViewById(R.id.bt_upload);

        db = new DatabaseHelper(getContext());
        final  List<User> allUsers = db.getAllUsers();

        bt_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallery,RESULT_LOAD_IMAGE);
            }
        });

        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_username.getText().toString().isEmpty() || et_password.getText().toString().isEmpty())
                {
                    Toast.makeText(getContext(),"Fileds cannot be empty",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String username = et_username.getText().toString();
                    String password = et_password.getText().toString();

                    User user = new User(checkMaxId(allUsers)+1,username,password,path);
                    db.createUser(user);

                    LoginFragment loginFragment = new LoginFragment();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.container,loginFragment);
                    ft.addToBackStack(null);
                    ft.commit();
                }
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
            path = getPath(selectedImage);
            image.setImageURI(selectedImage);
        }
    }

    private int checkMaxId(List<User> allUsers)
    {
        int id=0;
        for (User u: allUsers) {
            if(u.getUser_id()>id)
            {
                id=u.getUser_id();
            }
        }
        return id;
    }
    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().getApplicationContext().getContentResolver().query(uri, projection, null,null,null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
}
