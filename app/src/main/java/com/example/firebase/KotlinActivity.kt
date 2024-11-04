package com.example.firebase

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.firebase.fragments.KotlinLoginFragment
import com.example.firebase.fragments.KotlinRegisterFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth

class KotlinActivity : AppCompatActivity() {

    private lateinit var fragmentContainer: FrameLayout
    private lateinit var fabRegister: FloatingActionButton
    private lateinit var fabLogin: FloatingActionButton
    private lateinit var fabNext: FloatingActionButton
    private lateinit var auth: FirebaseAuth

    override fun onStart() {
        super.onStart()

        showFragment(KotlinLoginFragment())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_kotlin)
        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.main)
        ) { v: View, insets: WindowInsetsCompat ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        fragmentContainer = findViewById(R.id.fragmentContainer)
        fabLogin = findViewById(R.id.fabLogin)
        fabRegister = findViewById(R.id.fabRegister)
        fabNext = findViewById(R.id.fabNext)

        fabLogin.setOnClickListener { showFragment(KotlinLoginFragment()) }
        fabRegister.setOnClickListener { showFragment(KotlinRegisterFragment()) }
        fabNext.setOnClickListener {
            val intent = Intent(this, JavaActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun showFragment (fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}