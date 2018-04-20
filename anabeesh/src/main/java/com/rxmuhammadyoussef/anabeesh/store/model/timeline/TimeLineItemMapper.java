package com.rxmuhammadyoussef.anabeesh.store.model.timeline;

import com.rxmuhammadyoussef.anabeesh.R;
import com.rxmuhammadyoussef.anabeesh.store.model.article.ArticleViewModel;
import com.rxmuhammadyoussef.anabeesh.store.model.question.QuestionViewModel;
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

    public List<TimelineItem> toTimelineItems(List<ArticleViewModel> articleViewModels, List<QuestionViewModel> questionViewModels) {
        List<TimelineItem> timelineItems = new ArrayList<>();
        if (!articleViewModels.isEmpty()) {
            timelineItems.add(new TitleTimelineItem(resourcesUtil.getString(R.string.articles)));
            timelineItems.add(new ArticleListTimelineItem(articleViewModels));
        }
        List<QuestionViewModel> topRatedViewModels = new ArrayList<>();
        List<QuestionViewModel> newestViewModels = new ArrayList<>();
        for (QuestionViewModel questionViewModel : questionViewModels) {
            if (questionViewModel.isTopRated()) {
                topRatedViewModels.add(questionViewModel);
            } else {
                newestViewModels.add(questionViewModel);
            }
        }
        if (!topRatedViewModels.isEmpty()) {
            timelineItems.add(new TitleTimelineItem(resourcesUtil.getString(R.string.top_rated_questions)));
            for (QuestionViewModel questionViewModel : topRatedViewModels) {
                timelineItems.add(new QuestionTimelineItem(questionViewModel));
            }
        }
        if (!newestViewModels.isEmpty()) {
            timelineItems.add(new TitleTimelineItem(resourcesUtil.getString(R.string.newest_questions)));
            for (QuestionViewModel questionViewModel : newestViewModels) {
                timelineItems.add(new QuestionTimelineItem(questionViewModel));
            }
        }
        return timelineItems;
    }

    public List<TimelineItem> toTimelineItems(List<QuestionViewModel> questionViewModels) {
        List<TimelineItem> timelineItems = new ArrayList<>();
        for (QuestionViewModel questionViewModel : questionViewModels) {
            timelineItems.add(new QuestionTimelineItem(questionViewModel));
        }
        return timelineItems;
    }
}
