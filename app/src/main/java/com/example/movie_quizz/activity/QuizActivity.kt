package com.example.movie_quizz.activity


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movie_quizz.R
import com.example.movie_quizz.adapters.QuizOptionAdapter
import com.example.movie_quizz.models.Question
import com.example.movie_quizz.models.Quiz
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_quiz.*

class QuizActivity : AppCompatActivity() {

    var quizzes: MutableList<Quiz>? = null
    var questions: MutableMap<String, Question>? = null
    var indexQuestion = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        setUpEventListener()
        setUpFireStore()
        supportActionBar?.title = "Quizzs"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun setUpEventListener() {

        btnPrevious.setOnClickListener {
            indexQuestion--
            bindView()
        }

        btnNext.setOnClickListener {
            indexQuestion++
            bindView()
        }

        btnSubmit.setOnClickListener {
            Log.d("LASTQUESTION", questions.toString())


            val intent = Intent(this, ResultActivity::class.java)
            startActivity(intent)
            val json: String = Gson().toJson(quizzes!![0])
            intent.putExtra("QUIZ", json)
            startActivity(intent)
        }
    }

    private fun setUpFireStore() {
        val firestore = FirebaseFirestore.getInstance()
        val date = intent.getStringExtra("DATE")

        if (date != null) {
            firestore.collection("quizzes").whereEqualTo("title", date)
                .get()
                .addOnSuccessListener {
                    if (it != null && !it.isEmpty) {
                        quizzes = it.toObjects(Quiz::class.java)
                        questions = quizzes!![0].questions
                        bindView()
                    }
                }
        }
    }

    private fun bindView() {
        btnNext.visibility = android.view.View.GONE
        btnPrevious.visibility = android.view.View.GONE
        btnSubmit.visibility = android.view.View.GONE

        if (indexQuestion == 1) {
            btnNext.visibility = android.view.View.VISIBLE
        } else if (indexQuestion == questions!!.size) {
            btnSubmit.visibility = android.view.View.VISIBLE
            btnPrevious.visibility = android.view.View.VISIBLE
        } else {
            btnPrevious.visibility = android.view.View.VISIBLE
            btnNext.visibility = android.view.View.VISIBLE
        }

        val question: Question? = questions!!["question$indexQuestion"]
        question.let {
            tvDescription.text = it!!.description
            val quizOptionAdapter = QuizOptionAdapter(this, it)
            rvQuizOption.layoutManager = LinearLayoutManager(this)
            rvQuizOption.adapter = quizOptionAdapter
            rvQuizOption.setHasFixedSize(true)
        }
    }
}