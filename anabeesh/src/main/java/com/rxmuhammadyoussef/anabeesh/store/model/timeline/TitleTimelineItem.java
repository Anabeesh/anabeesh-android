package com.rxmuhammadyoussef.anabeesh.store.model.timeline;

/**
 This class represents timeline title, it's immutable and can only be instantiated/accessed within this package scope
 */

public class TitleTimelineItem extends TimelineItem {

    private final String title;

    TitleTimelineItem(String title) {this.title = title;}

    public String getTitle() {
        return title;
    }

    @Override
    public int getType() {
        return TimeLineItemType.TITLE;
    }
}
