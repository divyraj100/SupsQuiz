package com.example.movie_quizz.models

data class Question (
    var description : String = "",
    var option1 : String = "",
    var option2 : String = "",
    var option3 : String = "",
    var option4 : String = "",
    var userAnswer : String = "",
    var answer : String = ""
)
