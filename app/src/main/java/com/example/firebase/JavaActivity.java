package com.example.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.firebase.fragments.JavaLoginFragment;
import com.example.firebase.fragments.JavaRegisterFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class JavaActivity extends AppCompatActivity {

    private Intent intent;
    private FrameLayout fragmentContainer;
    private FloatingActionButton fabLogin, fabRegister, fabNext;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onStart() {
        super.onStart();

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();

        if (currentUser != null) {
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        showFragment(new JavaLoginFragment());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_java);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        fragmentContainer = findViewById(R.id.fragmentContainer);
        fabLogin = findViewById(R.id.fabLogin);
        fabRegister = findViewById(R.id.fabRegister);
        fabNext = findViewById(R.id.fabNext);

        fabLogin.setOnClickListener(v -> showFragment(new JavaLoginFragment()));
        fabRegister.setOnClickListener(v -> showFragment(new JavaRegisterFragment()));
        fabNext.setOnClickListener(v -> {
             intent = new Intent(this, KotlinActivity.class);
            startActivity(intent);
            finish();
        });

    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }
}