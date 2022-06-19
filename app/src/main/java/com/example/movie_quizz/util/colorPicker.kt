package com.example.movie_quizz.util

object colorPicker {

    val colors = arrayOf(
        "#0288D1"
    )
    var currentColor = 0

    fun getColor(): String {
        currentColor = (currentColor + 1) % colors.size
        return colors[currentColor]
    }
}