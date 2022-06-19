package com.example.movie_quizz.adapters


import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_quizz.R
import com.example.movie_quizz.activity.QuizActivity
import com.example.movie_quizz.models.Quiz
import com.example.movie_quizz.util.colorPicker
import com.example.movie_quizz.util.iconPicker
import com.example.movie_quizz.util.iconPicker.icons


class RvQuizAdapter(val context: Context, private val quizzs: List<Quiz>) :
    RecyclerView.Adapter<RvQuizAdapter.QuizViewHolder>() {


    inner class QuizViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var textViewTitle: TextView = itemView.findViewById(R.id.quizTitle)
        var iconView: ImageView = itemView.findViewById(R.id.quizIcon)
        var cardContainer: CardView = itemView.findViewById(R.id.cardContainer)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {

        val view: View = LayoutInflater.from(context).inflate(R.layout.rvquiz_iteam, parent, false)
        return QuizViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        holder.textViewTitle.text = quizzs[position].title
        holder.cardContainer.setCardBackgroundColor(Color.parseColor(colorPicker.getColor()))
        holder.iconView.setImageResource(iconPicker.getIcon())
        holder.itemView.setOnClickListener {
            Toast.makeText(context, quizzs[position].title, Toast.LENGTH_SHORT).show()
            val intent = Intent(context, QuizActivity::class.java)
            intent.putExtra("DATE", quizzs[position].title)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return quizzs.size
    }
}
