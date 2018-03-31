package com.rxmuhammadyoussef.anabeesh.ui.articledetails

import android.content.Context
import android.content.Intent
import com.rxmuhammadyoussef.anabeesh.store.model.article.ArticleViewModel
import com.rxmuhammadyoussef.anabeesh.ui.ActivityArgs

data class ArticleDetailsActivityArgs(val articleViewModel: ArticleViewModel) : ActivityArgs {

    override fun intent(activity: Context): Intent = Intent(
            activity, ArticleDetailsActivity::class.java)
            .apply { putExtra(KEY, articleViewModel) }

    companion object {
        fun deserializeFrom(intent: Intent): ArticleDetailsActivityArgs {
            return ArticleDetailsActivityArgs(
                    articleViewModel = intent.getParcelableExtra(KEY)
            )
        }
    }
}

private const val KEY = "KeyArticleDetails"
