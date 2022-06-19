package com.example.movie_quizz.util

import com.example.movie_quizz.R
import com.example.movie_quizz.util.colorPicker.colors
import com.example.movie_quizz.util.colorPicker.currentColor

object iconPicker {

    val icons = arrayOf(
        R.drawable.icon1,
        R.drawable.icon2,
        R.drawable.icon3
    )

    var currentIcon = 0

    fun getIcon(): Int {
        currentIcon = (currentIcon + 1) % icons.size
        return icons[currentIcon]
    }
}