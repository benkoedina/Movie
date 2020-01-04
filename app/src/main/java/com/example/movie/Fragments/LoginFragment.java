package com.example.movie.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.movie.Database.DatabaseHelper;
import com.example.movie.Model.User;
import com.example.movie.R;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class LoginFragment extends Fragment {

    //Login

    DatabaseHelper db;
    public LoginFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);
        db = new DatabaseHelper(getContext());
        final EditText et_name = view.findViewById(R.id.et_username);
        final EditText et_password = view.findViewById(R.id.et_password);

        Button bt_login = view.findViewById(R.id.bt_login);
        Button bt_register = view.findViewById(R.id.bt_register);

        //button for login
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //checking the input
                if(et_name.getText().toString().isEmpty() || et_password.getText().toString().isEmpty())
                {
                    Toast.makeText(getContext(),"Fileds cannot be empty",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String username = et_name.getText().toString();
                    String password = et_password.getText().toString();

                    //checking if the user is registered
                    final List<User> userList = db.getAllUsers();
                    int b = 0;
                   int id=-1;
                    for (User u : userList)
                    {
                        if (u.getName().equals(username) && u.getPassword().equals(password))
                        {
                            b = 1;
                            id = u.getUser_id();

                        }
                    }

                    if (b == 1)
                    {
                        //if it is registered passing the id to the next fragment
                        Bundle bundle = new Bundle();
                        bundle.putInt("id",id);

                        //fragment change
                        HomeFragment homeFragment = new HomeFragment();
                        homeFragment.setArguments(bundle);
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.container,homeFragment);
                        ft.addToBackStack(null);
                        ft.commit();
                    }
                    else
                    {
                        // if the user is not registered
                        Toast.makeText(getContext(),"User not registered",Toast.LENGTH_SHORT).show();
                    }


                }
            }
        });

        //register button functionality
        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //change to the register fragment
                RegisterFragment registerFragment = new RegisterFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.container,registerFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        return view;
    }
}
