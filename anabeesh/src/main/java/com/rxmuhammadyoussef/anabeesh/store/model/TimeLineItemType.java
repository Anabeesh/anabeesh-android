package com.rxmuhammadyoussef.anabeesh.store.model;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.rxmuhammadyoussef.anabeesh.store.model.TimeLineItemType.ANSWER_ITEM;
import static com.rxmuhammadyoussef.anabeesh.store.model.TimeLineItemType.ARTICLE_LIST;
import static com.rxmuhammadyoussef.anabeesh.store.model.TimeLineItemType.DIVIDER;
import static com.rxmuhammadyoussef.anabeesh.store.model.TimeLineItemType.QUESTION_ITEM;
import static com.rxmuhammadyoussef.anabeesh.store.model.TimeLineItemType.TITLE;

/**
 This annotation defines a set of time line items to make sure there is no room for mistakes
 */

@Retention(RetentionPolicy.RUNTIME)
@IntDef({TITLE, ARTICLE_LIST, QUESTION_ITEM, DIVIDER, ANSWER_ITEM})
public @interface TimeLineItemType {
    int TITLE = 0;
    int ARTICLE_LIST = 1;
    int QUESTION_ITEM = 2;
    int DIVIDER = 3;
    int ANSWER_ITEM = 4;
}
