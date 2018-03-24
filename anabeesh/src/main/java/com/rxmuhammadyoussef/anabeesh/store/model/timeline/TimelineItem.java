package com.rxmuhammadyoussef.anabeesh.store.model.timeline;

/**
 This class represents an abstract form of a timeline item. It can be any thing, title, list, etc...
 */

public abstract class TimelineItem {

    /**
     @return the type of that item that is represented by {@link TimeLineItemType}
     */
    @TimeLineItemType
    abstract public int getType();
}
