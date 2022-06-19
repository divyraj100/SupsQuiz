package com.example.movie_quizz.activity

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movie_quizz.R
import com.example.movie_quizz.adapters.RvQuizAdapter
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.navigation.NavigationBarItemView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import com.example.movie_quizz.models.Quiz as Quiz1

class MainActivity : AppCompatActivity() {

    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var adapter: RvQuizAdapter
    private var quizList = mutableListOf<Quiz1>()
    lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = ""
        setUpView()
    }

    private fun setUpRecyclerView() {
        adapter = RvQuizAdapter(this, quizList)
        rvQuiz.layoutManager = GridLayoutManager(this, 2)
        rvQuiz.adapter = adapter
    }

    private fun setUpView() {
        setDrawerLayout()
        setUpFireStore()
        setUpRecyclerView()
        setUpDatePicker()
    }

    @SuppressLint("SimpleDateFormat")
    private fun setUpDatePicker() {
        btnDatePicker.setOnClickListener {
            val datePicker: MaterialDatePicker<Long> =
                    MaterialDatePicker.Builder.datePicker().build()
            datePicker.show(supportFragmentManager, "DatePicker")
            datePicker.addOnPositiveButtonClickListener {
                Log.d("DATEPICKER", datePicker.headerText)
                val dateFormatter = SimpleDateFormat("dd-MM-yyyy")
                val date = dateFormatter.format(Date(it))
                val intent = Intent(this, QuizActivity::class.java)
                intent.putExtra("DATE", date)
                startActivity(intent)
            }
            datePicker.addOnNegativeButtonClickListener {
                Log.d("DATEPICKER", datePicker.headerText)

            }
            datePicker.addOnCancelListener {
                Log.d("DATEPICKER", "DatePicker Cancelled")
            }
        }
    }

    private fun setUpFireStore() {
        firestore = FirebaseFirestore.getInstance()

        val collectionReference: CollectionReference = firestore.collection("quizzes")
        collectionReference.addSnapshotListener { value, error ->

            if (value == null || error != null) {
                Toast.makeText(this, "SomeThing Went Wrong, Please Try Later", Toast.LENGTH_SHORT)
                    .show()
                return@addSnapshotListener
            }
            Log.d("DATACODE", value.toObjects(Quiz1::class.java).toString())

            quizList.clear()
            quizList.addAll(value.toObjects(Quiz1::class.java))
            adapter.notifyDataSetChanged()
        }
    }

    private fun setDrawerLayout() {
        setSupportActionBar(appBar)
        actionBarDrawerToggle =
                ActionBarDrawerToggle(this, appDrawer, R.string.app_name, R.string.app_name)
        actionBarDrawerToggle.syncState()

        navigationView.setNavigationItemSelectedListener {
            val intent = Intent(this, UserProfileActivity::class.java)
            startActivity(intent)
            appDrawer.closeDrawers()
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}