package com.dangerfield.triviaapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.dangerfield.triviaapp.R
import com.dangerfield.triviaapp.model.Question
import kotlinx.android.synthetic.main.fragment_questions.*

class QuestionsFragment : Fragment() {

    val questionsViewModel: QuestionsViewModel by viewModels()
    lateinit  var currentQuestion : Question
    var questionList: List<Question>? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_questions, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        observeAnsweredState()
        observeQuestions()
    }

    private fun observeQuestions() {
        questionsViewModel.getQuestions().observe(viewLifecycleOwner, Observer {
            //this code runs every time the data changes.
            questionList = it
            getNextQuestion()
        })
    }

    private fun observeAnsweredState() {
        questionsViewModel.getAnsweredState().observe(viewLifecycleOwner, Observer {
            Log.d("Elijah", "Answered State: " + it.name)
            when(it){
                AnsweredState.Answered -> showAnswered()
                AnsweredState.Answering -> showAnswering()
            }
        })
    }

    private fun showAnswering() {
        btn.text = "Submit"
        btn.setOnClickListener { handleSubmit() }
    }

    private  fun showAnswered() {
        btn.text = "Next"
        btn.setOnClickListener { handleNext() }
    }

    private fun updateQuestion() {
        tv_question.text = currentQuestion.question
        option_1.text = currentQuestion.options[0]
        option_2.text = currentQuestion.options[1]
        option_3.text = currentQuestion.options[2]
        option_4.text = currentQuestion.options[3]
    }

    private fun handleNext(){
        getNextQuestion()
        radioGroup.clearCheck()
        tv_wrong.visibility = View.INVISIBLE
        tv_correct.visibility = View.INVISIBLE
        questionsViewModel.setAnsweredState(AnsweredState.Answering)
    }

    private fun getNextQuestion() {
        if(questionsViewModel.currentQuestionIndex >= questionList?.size ?: 0) {
            questionsViewModel.currentQuestionIndex = 0
        }
        currentQuestion = questionList?.get(questionsViewModel.currentQuestionIndex++)  ?: return
        updateQuestion()
    }

    private fun handleSubmit() {
        val currentAnswer = getCurrentAnswer(radioGroup.checkedRadioButtonId)
        if(currentAnswer == null) {
            Toast.makeText(context, "Please make selection", Toast.LENGTH_LONG).show()
        }else {
            val correct = currentAnswer == currentQuestion.options[currentQuestion.correctIndex]
            showCorrect(correct)
            questionsViewModel.setAnsweredState(AnsweredState.Answered)
        }
    }

    private fun showCorrect(correct: Boolean) {
        if(correct){
            tv_correct.visibility = View.VISIBLE
            tv_wrong.visibility = View.INVISIBLE
        }else{
            tv_correct.visibility = View.INVISIBLE
            tv_wrong.visibility = View.VISIBLE
        }
    }

    private fun getCurrentAnswer(id: Int): String? {
         return when (id) {
           option_1.id -> option_1.text.toString()
            option_2.id -> option_2.text.toString()
            option_3.id -> option_3.text.toString()
             option_4.id -> option_4.text.toString()
             else -> null
        }
    }
}
