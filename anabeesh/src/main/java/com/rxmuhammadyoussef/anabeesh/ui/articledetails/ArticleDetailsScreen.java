package com.rxmuhammadyoussef.anabeesh.ui.articledetails;

import com.rxmuhammadyoussef.anabeesh.store.model.article.ArticleViewModel;
import com.rxmuhammadyoussef.core.component.activity.BaseActivityScreen;

public interface ArticleDetailsScreen extends BaseActivityScreen {

    void setupToolbar();

    void updateUi(ArticleViewModel articleViewModel);
}
