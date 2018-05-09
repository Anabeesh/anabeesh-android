package com.rxmuhammadyoussef.anabeesh.util.diffutil;

import android.support.v7.util.DiffUtil;
import android.util.Pair;

import com.rxmuhammadyoussef.anabeesh.store.model.TimelineItem;
import com.rxmuhammadyoussef.anabeesh.store.model.questiondetails.AnswerTimelineItem;
import com.rxmuhammadyoussef.anabeesh.store.model.questiondetails.QuestionTimelineItem;

import java.util.Collections;
import java.util.List;

public class QuestionDetailsDiffCallback extends DiffUtil.Callback {

    private final List<TimelineItem> oldItems;
    private final List<TimelineItem> newItems;

    public QuestionDetailsDiffCallback(Pair<List<TimelineItem>, List<TimelineItem>> oldNewListsPair) {
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
        if (oldTimeLineItem instanceof QuestionTimelineItem && newTimeLineItem instanceof QuestionTimelineItem) {
            return true;
        } else if (oldTimeLineItem instanceof AnswerTimelineItem && newTimeLineItem instanceof AnswerTimelineItem) {
            return true;
        }
        return false;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        TimelineItem oldTimeLineItem = oldItems.get(oldItemPosition);
        TimelineItem newTimeLineItem = newItems.get(newItemPosition);
        if (oldTimeLineItem instanceof QuestionTimelineItem && newTimeLineItem instanceof QuestionTimelineItem) {
            return areQuestionsTheSame((QuestionTimelineItem) oldTimeLineItem, (QuestionTimelineItem) newTimeLineItem);
        } else if (oldTimeLineItem instanceof AnswerTimelineItem && newTimeLineItem instanceof AnswerTimelineItem) {
            return areAnswersTheSame((AnswerTimelineItem) oldTimeLineItem, (AnswerTimelineItem) newTimeLineItem);
        }
        return false;
    }

    private boolean areAnswersTheSame(AnswerTimelineItem oldTimeLineItem, AnswerTimelineItem newTimeLineItem) {
        return oldTimeLineItem.getAnswerViewModel().equals(newTimeLineItem.getAnswerViewModel());
    }

    private boolean areQuestionsTheSame(QuestionTimelineItem oldTimeLineItem, QuestionTimelineItem newTimeLineItem) {
        return oldTimeLineItem.getQuestionViewModel().equals(newTimeLineItem.getQuestionViewModel());
    }

    public List<TimelineItem> getNewItems() {
        return Collections.unmodifiableList(newItems);
    }

    public List<TimelineItem> getOldItems() {
        return Collections.unmodifiableList(oldItems);
    }
}
