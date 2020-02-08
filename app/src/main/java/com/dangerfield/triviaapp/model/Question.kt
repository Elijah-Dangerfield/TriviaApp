package com.dangerfield.triviaapp.model

data class Question(val correctIndex: Int, val options: ArrayList<String>, val question: String) {
    constructor(): this(0, arrayListOf(), "")
}