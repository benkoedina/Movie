package com.example.movie.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.movie.R;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class LoginFragment extends Fragment {
    public LoginFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        final EditText et_name = view.findViewById(R.id.et_username);
        final EditText et_password = view.findViewById(R.id.et_password);

        Button bt_login = view.findViewById(R.id.bt_login);
        Button bt_register = view.findViewById(R.id.bt_register);

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(et_name.getText().toString().isEmpty() || et_password.getText().toString().isEmpty())
                {
                    Toast.makeText(getContext(),"Fileds cannot be empty",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String username = et_name.getText().toString();
                    String password = et_password.getText().toString();

                    HomeFragment homeFragment = new HomeFragment();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.container,homeFragment);
                    ft.addToBackStack(null);
                    ft.commit();
                }
            }
        });

        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
