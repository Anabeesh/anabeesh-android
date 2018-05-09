package com.rxmuhammadyoussef.anabeesh.store.model.questiondetails;

import com.rxmuhammadyoussef.anabeesh.store.model.TimeLineItemType;
import com.rxmuhammadyoussef.anabeesh.store.model.TimelineItem;
import com.rxmuhammadyoussef.anabeesh.store.model.question.QuestionViewModel;

public class QuestionTimelineItem extends TimelineItem {

    private final QuestionViewModel questionViewModel;

    QuestionTimelineItem(QuestionViewModel questionViewModel) {this.questionViewModel = questionViewModel;}

    public QuestionViewModel getQuestionViewModel() {
        return questionViewModel;
    }

    @Override
    public int getType() {
        return TimeLineItemType.QUESTION_ITEM;
    }
}
