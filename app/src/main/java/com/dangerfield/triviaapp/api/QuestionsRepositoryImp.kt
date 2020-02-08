package com.dangerfield.triviaapp.api

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.dangerfield.triviaapp.model.Question
import com.google.firebase.firestore.FirebaseFirestore

class QuestionsRepositoryImp: QuestionsRepository {
    private val db = FirebaseFirestore.getInstance()

    override fun getQuestions(): MutableLiveData<List<Question>> {
        var result = MutableLiveData<List<Question>>()
        db.collection("questions").get().addOnSuccessListener {
            result.postValue(it.documents.map {doc ->  doc.toObject(Question::class.java)!! })
            Log.d("Elijah", "repository got: " + result.toString())
        }
        return result
    }
}