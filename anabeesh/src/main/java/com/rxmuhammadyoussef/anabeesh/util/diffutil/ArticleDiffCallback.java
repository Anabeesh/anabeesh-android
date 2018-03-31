package com.rxmuhammadyoussef.anabeesh.util.diffutil;

import android.support.v7.util.DiffUtil;
import android.util.Pair;

import com.rxmuhammadyoussef.anabeesh.store.model.article.ArticleViewModel;
import com.rxmuhammadyoussef.anabeesh.store.model.category.CategoryViewModel;

import java.util.Collections;
import java.util.List;

public class ArticleDiffCallback extends DiffUtil.Callback {

    private final List<ArticleViewModel> oldItems;
    private final List<ArticleViewModel> newItems;

    public ArticleDiffCallback(Pair<List<ArticleViewModel>, List<ArticleViewModel>> oldNewListsPair) {
        this.oldItems = oldNewListsPair.first;
        this.newItems = oldNewListsPair.second;
    }

    @Override
    public int getOldListSize() {
        return oldItems.size();
    }

    @Override
    public int getNewListSize() {
        return newItems.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldItems.get(oldItemPosition).getId() == (oldItems.get(newItemPosition).getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldItems.get(oldItemPosition).equals(newItems.get(newItemPosition));
    }

    public List<ArticleViewModel> getNewItems() {
        return Collections.unmodifiableList(newItems);
    }

    public List<ArticleViewModel> getOldItems() {
        return Collections.unmodifiableList(oldItems);
    }
}
