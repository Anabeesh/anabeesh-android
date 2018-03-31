package com.rxmuhammadyoussef.anabeesh.ui.questiondetails

import android.content.Context
import android.content.Intent
import com.rxmuhammadyoussef.anabeesh.store.model.question.QuestionViewModel
import com.rxmuhammadyoussef.anabeesh.ui.ActivityArgs


data class QuestionDetailsActivityArgs(val questionViewModel: QuestionViewModel) : ActivityArgs {

    override fun intent(activity: Context): Intent = Intent(
            activity, QuestionDetailsActivity::class.java)
            .apply { putExtra(KEY, questionViewModel) }

    companion object {
        fun deserializeFrom(intent: Intent): QuestionDetailsActivityArgs {
            return QuestionDetailsActivityArgs(questionViewModel = intent.getParcelableExtra(KEY))
        }
    }
}

private const val KEY = "KeyQuestionDetails"
