package com.example.movie_quizz.activity

import android.app.ProgressDialog
import android.os.AsyncTask
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import com.example.movie_quizz.R
import com.example.movie_quizz.models.Question
import com.example.movie_quizz.models.Quiz
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {

    lateinit var quiz: Quiz
    private lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        supportActionBar?.title =""
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        setUpViews()

        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Result..")
        progressDialog.setCancelable(true)
        progressDialog.progress = 3

        MyTask(mainActivity = this).execute()
        if(progressDialog.isShowing){
            progressDialog.dismiss()
        }
    }

    companion object{
        class MyTask(private val mainActivity: ResultActivity) : AsyncTask <Void,Void,Void>(){
            override fun doInBackground(vararg params: Void?): Void? {
                return null
            }

            override fun onPreExecute() {
                mainActivity.progressDialog.show()
            }
        }
    }

    private fun setUpViews() {
        val quizData: String? = intent.getStringExtra("QUIZ")
        quiz = Gson()!!.fromJson<Quiz>(quizData, Quiz::class.java)
        calculateScore()
        setAnswerView()
    }

    private fun setAnswerView() {
        val builder = StringBuilder("")
        for (entry in quiz.questions.entries) {
            val question = entry.value
            builder.append("<font color'#18206F'><b>Question: ${question.description}</b></font><br/><br/>")
            builder.append("<font color='#0288D1'>Answer: ${question.answer}</font><br/><br/>")
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvAnswer.text = Html.fromHtml(builder.toString(), Html.FROM_HTML_MODE_COMPACT);
        } else {
            tvAnswer.text = Html.fromHtml(builder.toString())
        }
    }

    private fun calculateScore() {
        var score = 0

        for (entry: MutableMap.MutableEntry<String, Question> in quiz.questions.entries) {
            val question: Question = entry.value

            if (question.answer == question.userAnswer) {
                score += 10
            }

            tvScore.text = "You Scored $score"
        }
    }
}


