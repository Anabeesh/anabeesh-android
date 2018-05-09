package com.rxmuhammadyoussef.anabeesh.store.model.questiondetails;

import com.rxmuhammadyoussef.anabeesh.store.model.TimeLineItemType;
import com.rxmuhammadyoussef.anabeesh.store.model.TimelineItem;
import com.rxmuhammadyoussef.anabeesh.store.model.answer.AnswerViewModel;

public class AnswerTimelineItem extends TimelineItem {

    private final AnswerViewModel answerViewModel;

    AnswerTimelineItem(AnswerViewModel answerViewModel) {this.answerViewModel = answerViewModel;}

    public AnswerViewModel getAnswerViewModel() {
        return answerViewModel;
    }

    @Override
    public int getType() {
        return TimeLineItemType.ANSWER_ITEM;
    }
}
