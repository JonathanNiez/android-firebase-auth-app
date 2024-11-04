package com.example.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private Intent intent;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;

    private TextView emailTextView;
    private Button logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();

        emailTextView = findViewById(R.id.emailTextView);
        logoutBtn = findViewById(R.id.logoutBtn);

        if (currentUser != null) {
            emailTextView.setText(currentUser.getEmail());
        } else {
            intent = new Intent(this, JavaActivity.class);
            startActivity(intent);
            finish();
        }

        logoutBtn.setOnClickListener(v -> logoutUser());
    }

    private void logoutUser (){
        if (currentUser != null) {
            firebaseAuth.signOut();
            intent = new Intent(this, JavaActivity.class);
            startActivity(intent);
            finish();
            Toast.makeText(this, "Logged out Successfully", Toast.LENGTH_SHORT).show();
        }
    }
}