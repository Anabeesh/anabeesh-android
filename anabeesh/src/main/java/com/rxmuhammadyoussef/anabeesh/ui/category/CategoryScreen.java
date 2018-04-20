package com.rxmuhammadyoussef.anabeesh.ui.category;

import android.support.v7.util.DiffUtil;

public interface CategoryScreen {

    void setupRecyclerView();

    void setupRefreshLayout();

    void showErrorMessage(String message);

    void showLoadingAnimation();

    void hideLoadingAnimation();

    void updateUi(DiffUtil.DiffResult diffResult);

    void notifyCategoryChanged(int position);
}
