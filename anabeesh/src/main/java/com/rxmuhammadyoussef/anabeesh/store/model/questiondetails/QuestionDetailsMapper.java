package com.rxmuhammadyoussef.anabeesh.store.model.questiondetails;

import android.util.Pair;

import com.rxmuhammadyoussef.anabeesh.store.model.TimelineItem;
import com.rxmuhammadyoussef.anabeesh.store.model.answer.AnswerViewModel;
import com.rxmuhammadyoussef.anabeesh.store.model.question.QuestionViewModel;
import com.rxmuhammadyoussef.core.di.scope.ApplicationScope;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@ApplicationScope
public class QuestionDetailsMapper {

    @Inject
    QuestionDetailsMapper() {
    }

    public List<TimelineItem> toTimelineItems(Pair<QuestionViewModel, List<AnswerViewModel>> questionAnswersPair) {
        List<TimelineItem> timelineItems = new ArrayList<>();
        timelineItems.add(new QuestionTimelineItem(questionAnswersPair.first));
        for (AnswerViewModel answerViewModel : questionAnswersPair.second) {
            timelineItems.add(new AnswerTimelineItem(answerViewModel));
        }
        return timelineItems;
    }
}
