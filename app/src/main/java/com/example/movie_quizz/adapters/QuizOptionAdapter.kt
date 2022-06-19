package com.example.movie_quizz.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_quizz.R
import com.example.movie_quizz.models.Question

class QuizOptionAdapter(val context: Context, val question: Question) :
    RecyclerView.Adapter<QuizOptionAdapter.OptionViewHolder>() {

    private var options: List<String> =
            listOf(question.option1, question.option2, question.option3, question.option4)

    inner class OptionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var quizOption = itemView.findViewById<TextView>(R.id.tvQuizOption)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.option_quiz_item, parent, false)
        return OptionViewHolder(view)
    }

    override fun onBindViewHolder(holder: OptionViewHolder, position: Int) {
        holder.quizOption.text = options[position]
        holder.itemView.setOnClickListener {
            question.userAnswer = options[position]
            notifyDataSetChanged()
        }
        if (question.userAnswer == options[position]) {
            holder.itemView.setBackgroundResource(R.drawable.bg_option_item_selected)
        } else {
            holder.itemView.setBackgroundResource(R.drawable.bg_option_item)
        }
    }

    override fun getItemCount(): Int {
        return options.size
    }
}