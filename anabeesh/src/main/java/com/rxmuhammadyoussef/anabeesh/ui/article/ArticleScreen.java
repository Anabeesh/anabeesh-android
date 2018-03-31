package com.rxmuhammadyoussef.anabeesh.ui.article;

import android.support.v7.util.DiffUtil;

import com.rxmuhammadyoussef.core.component.activity.BaseActivityScreen;

public interface ArticleScreen extends BaseActivityScreen{

    void setupRecyclerView();

    void setupRefreshLayout();

    void setupToolbar();

    void updateUi(DiffUtil.DiffResult diffResult);
}
