package com.rxmuhammadyoussef.anabeesh.ui.articledetails

import com.rxmuhammadyoussef.core.component.activity.BaseActivityPresenter
import com.rxmuhammadyoussef.core.di.scope.ActivityScope
import javax.inject.Inject

@ActivityScope
class ArticleDetailsPresenter @Inject constructor(private val articleDetailsScreen: ArticleDetailsScreen) : BaseActivityPresenter(articleDetailsScreen) {

    private val args by lazy {
        ArticleDetailsActivityArgs.deserializeFrom(intent)
    }

    override fun onCreate() {
        articleDetailsScreen.setupToolbar()
        articleDetailsScreen.updateUi(args.articleViewModel)
    }
}