package com.rxmuhammadyoussef.anabeesh.store.model.timeline;

import com.rxmuhammadyoussef.anabeesh.R;
import com.rxmuhammadyoussef.anabeesh.store.model.article.ArticleViewModel;
import com.rxmuhammadyoussef.core.di.scope.ApplicationScope;
import com.rxmuhammadyoussef.core.util.ResourcesUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@ApplicationScope
public class TimeLineItemMapper {

    private final ResourcesUtil resourcesUtil;

    @Inject
    TimeLineItemMapper(ResourcesUtil resourcesUtil) {
        this.resourcesUtil = resourcesUtil;
    }

    public List<TimelineItem> toTimelineItems(List<ArticleViewModel> articleViewModels) {
        List<TimelineItem> timelineItems = new ArrayList<>();
        if (!articleViewModels.isEmpty()) {
            timelineItems.add(new TitleTimelineItem(resourcesUtil.getString(R.string.articles)));
            timelineItems.add(new ArticleListTimelineItem(articleViewModels));
        }
        return timelineItems;
    }
}
