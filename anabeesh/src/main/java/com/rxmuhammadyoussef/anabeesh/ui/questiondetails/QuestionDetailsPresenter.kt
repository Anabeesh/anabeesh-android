package com.rxmuhammadyoussef.anabeesh.ui.questiondetails

import com.rxmuhammadyoussef.core.component.activity.BaseActivityPresenter
import com.rxmuhammadyoussef.core.di.scope.ActivityScope
import javax.inject.Inject

@ActivityScope
class QuestionDetailsPresenter @Inject constructor(private val questionDetailsScreen: QuestionDetailsScreen) : BaseActivityPresenter(questionDetailsScreen) {

    private val args by lazy {
        QuestionDetailsActivityArgs.deserializeFrom(intent)
    }

    override fun onCreate() {
        questionDetailsScreen.setupToolbar()
        questionDetailsScreen.updateUi(args.questionViewModel)
    }
}