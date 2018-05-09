package com.rxmuhammadyoussef.anabeesh.ui.questiondetails;

import android.support.v7.util.DiffUtil;

import com.rxmuhammadyoussef.anabeesh.store.model.question.QuestionViewModel;
import com.rxmuhammadyoussef.core.component.activity.BaseActivityScreen;

public interface QuestionDetailsScreen extends BaseActivityScreen {

    void setupToolbar();

    void setupRecyclerView();

    void updateUi(DiffUtil.DiffResult diffResult);
}
