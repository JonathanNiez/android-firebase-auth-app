package com.example.firebase.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.example.firebase.MainActivity
import com.example.firebase.R
import com.example.firebase.firebase.FirebaseHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class KotlinRegisterFragment : Fragment() {

    private val TAG = "RegisterKotlinFragment"
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var fstore: FirebaseFirestore

    private lateinit var registerBtn: Button
    private lateinit var fname: EditText
    private lateinit var lname: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var confirmPassword: EditText
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register_kotlin, container, false)

        fstore = FirebaseFirestore.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()

        registerBtn = view.findViewById(R.id.registerBtn)
        fname = view.findViewById(R.id.fname)
        lname = view.findViewById(R.id.lname)
        email = view.findViewById(R.id.email)
        password = view.findViewById(R.id.password)
        confirmPassword = view.findViewById(R.id.confirmPassword)
        progressBar = view.findViewById(R.id.progressBar)

        progressBar.visibility = View.GONE

        registerBtn.setOnClickListener {
            val fnameStr = fname.text.toString().trim()
            val lnameStr = lname.text.toString().trim()
            val emailStr = email.text.toString().trim()
            val passwordStr = password.text.toString()
            val confirmPasswordStr = confirmPassword.text.toString()

            if (fnameStr.isEmpty() ||
                lnameStr.isEmpty() ||
                emailStr.isEmpty() ||
                passwordStr.isEmpty() ||
                confirmPasswordStr.isEmpty()
            ) {
                Toast.makeText(context, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
            } else if (confirmPasswordStr != passwordStr) {
                Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
            } else {
                registerBtn.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
                firebaseAuth.createUserWithEmailAndPassword(emailStr, passwordStr)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user = hashMapOf(
                                "userID" to firebaseAuth.currentUser!!.uid,
                                "fname" to fnameStr,
                                "lname" to lnameStr,
                                "email" to emailStr
                            )
                            fstore.collection(FirebaseHelper.USER_COLLECTION)
                                .document(firebaseAuth.currentUser!!.uid)
                                .set(user).addOnCompleteListener { task1 ->
                                    if (task1.isSuccessful) {
                                        val intent = Intent(context, MainActivity::class.java)
                                        startActivity(intent)
                                        requireActivity().finish()
                                        Toast.makeText(
                                            context,
                                            "Registered successfully",
                                            Toast.LENGTH_SHORT
                                        )
                                            .show()
                                        Log.d(TAG, "createUserWithEmail:success")
                                    } else {
                                        registerBtn.visibility = View.VISIBLE
                                        progressBar.visibility = View.GONE
                                        Log.e(TAG, "Error adding document", task1.exception)
                                    }
                                }
                        } else {
                            registerBtn.visibility = View.VISIBLE
                            progressBar.visibility = View.GONE
                            Toast.makeText(
                                context,
                                "Error: ${task.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                            Log.e(TAG, "createUserWithEmail:failed")
                        }
                    }
            }
        }

        return view
    }

}