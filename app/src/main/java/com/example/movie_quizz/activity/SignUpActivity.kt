package com.example.movie_quizz.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.movie_quizz.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        firebaseAuth = FirebaseAuth.getInstance()

        btnSignUp.setOnClickListener {
            signUpUser()
        }
        btnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun signUpUser() {

        val email: String = etEmail.text.toString()
        val password: String = etPassword.text.toString()
        val confirmPassword: String = etConfirmPassword.text.toString()



        if (email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
            Toast.makeText(this, "Please Enter Email and Password", Toast.LENGTH_LONG).show()
            return
        }

        if (password != confirmPassword) {
            Toast.makeText(this, "Password and ConfirmPassword does not match", Toast.LENGTH_LONG)
                .show()
            return
        }

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Login Successful", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show()
                }
            }
    }
}









