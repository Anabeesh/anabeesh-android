package com.rxmuhammadyoussef.anabeesh.util.diffutil;

import android.support.v7.util.DiffUtil;
import android.util.Pair;

import com.rxmuhammadyoussef.anabeesh.store.model.timeline.ArticleListTimelineItem;
import com.rxmuhammadyoussef.anabeesh.store.model.timeline.QuestionTimelineItem;
import com.rxmuhammadyoussef.anabeesh.store.model.timeline.TimelineItem;
import com.rxmuhammadyoussef.anabeesh.store.model.timeline.TitleTimelineItem;

import java.util.Collections;
import java.util.List;

public class TimelineDiffCallback extends DiffUtil.Callback {

    private final List<TimelineItem> oldItems;
    private final List<TimelineItem> newItems;

    public TimelineDiffCallback(Pair<List<TimelineItem>, List<TimelineItem>> oldNewListsPair) {
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
        TimelineItem oldTimeLineItem = oldItems.get(oldItemPosition);
        TimelineItem newTimeLineItem = newItems.get(newItemPosition);
        if (oldTimeLineItem instanceof TitleTimelineItem && newTimeLineItem instanceof TitleTimelineItem) {
            return true;
        } else if (oldTimeLineItem instanceof QuestionTimelineItem && newTimeLineItem instanceof QuestionTimelineItem) {
            return true;
        } else if (oldTimeLineItem instanceof ArticleListTimelineItem && newTimeLineItem instanceof ArticleListTimelineItem) {
            return true;
        }
        return false;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        TimelineItem oldTimeLineItem = oldItems.get(oldItemPosition);
        TimelineItem newTimeLineItem = newItems.get(newItemPosition);
        if (oldTimeLineItem instanceof TitleTimelineItem && newTimeLineItem instanceof TitleTimelineItem) {
            return areTitlesTheSame((TitleTimelineItem) oldTimeLineItem, (TitleTimelineItem) newTimeLineItem);
        } else if (oldTimeLineItem instanceof QuestionTimelineItem && newTimeLineItem instanceof QuestionTimelineItem) {
            return areQuestionItemsContentTheSame((QuestionTimelineItem) oldTimeLineItem, (QuestionTimelineItem) newTimeLineItem);
        } else if (oldTimeLineItem instanceof ArticleListTimelineItem && newTimeLineItem instanceof ArticleListTimelineItem) {
            return areArticleListItemsContentTheSame((ArticleListTimelineItem) oldTimeLineItem, (ArticleListTimelineItem) newTimeLineItem);
        }
        return false;
    }

    private boolean areTitlesTheSame(TitleTimelineItem oldTimeLineItem, TitleTimelineItem newTimeLineItem) {
        return oldTimeLineItem.getTitle().contentEquals(newTimeLineItem.getTitle());
    }

    private boolean areQuestionItemsContentTheSame(QuestionTimelineItem oldTimeLineItem, QuestionTimelineItem newTimeLineItem) {
        return oldTimeLineItem.getType() == newTimeLineItem.getType();
    }

    private boolean areArticleListItemsContentTheSame(ArticleListTimelineItem oldTimeLineItem, ArticleListTimelineItem newTimeLineItem) {
        if (oldTimeLineItem.getArticleViewModels().size() == newTimeLineItem.getArticleViewModels().size()) {
            for (int i = 0; i < oldTimeLineItem.getArticleViewModels().size(); i++) {
                if (!(oldTimeLineItem.getArticleViewModels().get(i).equals(newTimeLineItem.getArticleViewModels().get(i)))) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public List<TimelineItem> getNewItems() {
        return Collections.unmodifiableList(newItems);
    }

    public List<TimelineItem> getOldItems() {
        return Collections.unmodifiableList(oldItems);
    }
}
