package com.example.firebase.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.firebase.MainActivity;
import com.example.firebase.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class JavaLoginFragment extends Fragment {

    private Intent intent;
    private Button loginBtn;
    private EditText email, password;

    private FirebaseAuth firebaseAuth;

    public static JavaLoginFragment newInstance(String param1, String param2) {
        JavaLoginFragment fragment = new JavaLoginFragment();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_java_login, container, false);

        firebaseAuth = FirebaseAuth.getInstance();

        loginBtn = view.findViewById(R.id.loginBtn);
        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);

        loginBtn.setOnClickListener(v -> {
            final String emailString = email.getText().toString().trim();
            final String passwordString = password.getText().toString();

            firebaseAuth.signInWithEmailAndPassword(emailString, passwordString)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                            Toast.makeText(getContext(), "Login Success", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }

                    });

        });

        return view;
    }
}