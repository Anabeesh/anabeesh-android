package com.rxmuhammadyoussef.anabeesh.ui.home;

import android.support.v7.util.DiffUtil;

public interface HomeScreen {

    void setupRecyclerView();

    void setupRefreshLayout();

    void showErrorMessage(String message);

    void showLoadingAnimation();

    void hideLoadingAnimation();

    void updateUi(DiffUtil.DiffResult diffResult);
}
