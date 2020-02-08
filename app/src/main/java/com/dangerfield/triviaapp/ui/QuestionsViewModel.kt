package com.dangerfield.triviaapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dangerfield.triviaapp.api.QuestionsRepository
import com.dangerfield.triviaapp.api.QuestionsRepositoryImp
import com.dangerfield.triviaapp.model.Question

class QuestionsViewModel : ViewModel() {
    private val repository: QuestionsRepository = QuestionsRepositoryImp()

    private var questions = MutableLiveData<List<Question>>()

    private var answeredState: MutableLiveData<AnsweredState> =
        MutableLiveData(AnsweredState.Answering)

    var currentQuestionIndex = 0

    fun getAnsweredState(): MutableLiveData<AnsweredState> {
        return answeredState
    }

    fun setAnsweredState(newState: AnsweredState){
        answeredState.value = newState
    }

    fun getQuestions(): LiveData<List<Question>> {
        if(questions.value.isNullOrEmpty()) {
            questions = repository.getQuestions()
        }
        return questions
    }
}