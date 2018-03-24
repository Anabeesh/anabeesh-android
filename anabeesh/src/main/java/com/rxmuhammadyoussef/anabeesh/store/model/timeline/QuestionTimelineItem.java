package com.rxmuhammadyoussef.anabeesh.store.model.timeline;

/**
 This class represents a vertical scrolling list of matches,typically all the matched except fot today's and tomorrows's matches
 it's immutable and can only be instantiated/accessed within this package scope
 */

public class QuestionTimelineItem extends TimelineItem {

    @Override
    public int getType() {
        return TimeLineItemType.QUESTION_ITEM;
    }
}
