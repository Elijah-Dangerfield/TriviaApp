package com.dangerfield.triviaapp.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dangerfield.triviaapp.model.Question

interface QuestionsRepository {
    fun getQuestions(): MutableLiveData<List<Question>>
}