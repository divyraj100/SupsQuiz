package com.example.movie_quizz.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.movie_quizz.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login_intro.*

class Login_Intro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_intro)
        val fireAuth: FirebaseAuth = FirebaseAuth.getInstance()

        if (fireAuth.currentUser != null) {
            redirect("MAIN")
        }

        btnLetsGo.setOnClickListener {
            redirect("LOGIN")
        }
    }

    private fun redirect(name: String) {

        val intent: Intent = when (name) {
            "LOGIN" -> Intent(this, LoginActivity::class.java)
            "MAIN" -> Intent(this, MainActivity::class.java)
            else -> throw Exception("Something Went Wrong")
        }
        startActivity(intent)
        finish()
    }
}