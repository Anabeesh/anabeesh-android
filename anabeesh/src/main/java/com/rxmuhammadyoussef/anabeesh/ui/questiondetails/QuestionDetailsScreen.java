package com.rxmuhammadyoussef.anabeesh.ui.questiondetails;

import com.rxmuhammadyoussef.anabeesh.store.model.question.QuestionViewModel;
import com.rxmuhammadyoussef.core.component.activity.BaseActivityScreen;

public interface QuestionDetailsScreen extends BaseActivityScreen {

    void setupToolbar();

    void updateUi(QuestionViewModel questionViewModel);
}
