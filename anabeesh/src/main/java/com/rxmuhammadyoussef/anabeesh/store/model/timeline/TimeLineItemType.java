package com.rxmuhammadyoussef.anabeesh.store.model.timeline;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.rxmuhammadyoussef.anabeesh.store.model.timeline.TimeLineItemType.ARTICLE_LIST;
import static com.rxmuhammadyoussef.anabeesh.store.model.timeline.TimeLineItemType.TITLE;
import static com.rxmuhammadyoussef.anabeesh.store.model.timeline.TimeLineItemType.QUESTION_ITEM;

/**
 This annotation defines a set of time line items to make sure there is no room for mistakes
 */

@Retention(RetentionPolicy.RUNTIME)
@IntDef({TITLE, ARTICLE_LIST, QUESTION_ITEM})
public @interface TimeLineItemType {
    int TITLE = 0;
    int ARTICLE_LIST = 1;
    int QUESTION_ITEM = 2;
}
