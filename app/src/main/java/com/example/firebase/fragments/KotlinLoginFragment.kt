package com.example.firebase.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.firebase.MainActivity
import com.example.firebase.R
import com.google.firebase.auth.FirebaseAuth


class KotlinLoginFragment : Fragment() {

    private lateinit var loginBtn: Button
    private lateinit var email: EditText
    private lateinit var password: EditText

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_kotlin_login, container, false)

        auth = FirebaseAuth.getInstance()

        loginBtn = view.findViewById(R.id.loginBtn)
        email = view.findViewById(R.id.email)
        password = view.findViewById(R.id.password)

        loginBtn.setOnClickListener {
            val emailStr = email.text.toString().trim()
            val passwordStr = password.text.toString()

            if (emailStr.isNotEmpty() && passwordStr.isNotEmpty()){
                auth.signInWithEmailAndPassword(emailStr, passwordStr)
                    .addOnCompleteListener{ task ->
                        if (task.isSuccessful) {
                            val intent = Intent(activity, MainActivity::class.java)
                            startActivity(intent)
                            requireActivity().finish()
                            Toast.makeText(activity, "Login Successful", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

        return view
    }

}