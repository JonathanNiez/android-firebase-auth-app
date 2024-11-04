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

import com.example.firebase.JavaActivity;
import com.example.firebase.R;
import com.example.firebase.firebase.FirebaseHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class JavaRegisterFragment extends Fragment {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore fstore;
    private Button registerBtn;
    private EditText fname, lname, email, password, confirmPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_java, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        registerBtn = view.findViewById(R.id.registerBtn);
        fname = view.findViewById(R.id.fname);
        lname = view.findViewById(R.id.lname);
        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        confirmPassword = view.findViewById(R.id.confirmPassword);

        registerBtn.setOnClickListener(v -> {
            String fnameStr = fname.getText().toString().trim();
            String lnameStr = lname.getText().toString().trim();
            String emailStr = email.getText().toString().trim();
            String passwordStr = password.getText().toString();
            String confirmPasswordStr = confirmPassword.getText().toString();

            if (fnameStr.isEmpty() ||
                    lnameStr.isEmpty() ||
                    emailStr.isEmpty() ||
                    passwordStr.isEmpty() ||
                    confirmPasswordStr.isEmpty()) {
                Toast.makeText(getContext(), "Fields cannot be empty", Toast.LENGTH_SHORT).show();
            } else if (!confirmPasswordStr.equals(passwordStr)) {
                Toast.makeText(getContext(), "Password did not match", Toast.LENGTH_SHORT).show();
            } else {
                //create user
                firebaseAuth.createUserWithEmailAndPassword(emailStr, passwordStr)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {

                                //create user object
                                HashMap<String, Object> user = new HashMap<>();
                                user.put("userID", firebaseAuth.getCurrentUser().getUid());
                                user.put("fname", fnameStr);
                                user.put("lname", lnameStr);
                                user.put("email", firebaseAuth.getCurrentUser().getEmail());

                                //store user info in firestore
                                fstore.collection(FirebaseHelper.USER_COLLECTION)
                                        .document(firebaseAuth.getCurrentUser().getUid())
                                        .set(user).addOnCompleteListener(task1 -> {
                                            if (task1.isSuccessful()) {
                                                Toast.makeText(getContext(), "Registration successful", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(getContext(), JavaActivity.class);
                                                startActivity(intent);
                                                requireActivity().finish();
                                            } else {
                                                Toast.makeText(getContext(), "Registration failed: " + task1.getException(), Toast.LENGTH_SHORT).show();
                                            }
                                        });

                            } else {
                                Toast.makeText(getContext(), "Registration failed: " + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        return view;
    }
}