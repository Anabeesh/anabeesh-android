package com.rxmuhammadyoussef.anabeesh.store.model.timeline;

import com.rxmuhammadyoussef.anabeesh.store.model.TimeLineItemType;
import com.rxmuhammadyoussef.anabeesh.store.model.TimelineItem;
import com.rxmuhammadyoussef.anabeesh.store.model.article.ArticleViewModel;

import java.util.List;

/**
 This class represents a horizontal scrolling list of matches,typically represents today's/tomorrow's matches,
 it's immutable and can only be instantiated/accessed within this package scope
 */
public class ArticleListTimelineItem extends TimelineItem {

    private final List<ArticleViewModel> articleViewModels;

    ArticleListTimelineItem(List<ArticleViewModel> articleViewModels) {this.articleViewModels = articleViewModels;}

    public List<ArticleViewModel> getArticleViewModels() {
        return articleViewModels;
    }

    @Override
    public int getType() {
        return TimeLineItemType.ARTICLE_LIST;
    }
}
