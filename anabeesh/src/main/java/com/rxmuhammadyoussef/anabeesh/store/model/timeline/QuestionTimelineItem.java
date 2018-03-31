package com.rxmuhammadyoussef.anabeesh.store.model.timeline;

import com.rxmuhammadyoussef.anabeesh.store.model.question.QuestionViewModel;

/**
 This class represents a vertical scrolling list of matches,typically all the matched except fot today's and tomorrows's matches
 it's immutable and can only be instantiated/accessed within this package scope
 */

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
