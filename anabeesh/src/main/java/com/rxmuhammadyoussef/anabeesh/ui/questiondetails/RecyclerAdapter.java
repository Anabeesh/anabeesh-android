package com.rxmuhammadyoussef.anabeesh.ui.questiondetails;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.signature.ObjectKey;
import com.makeramen.roundedimageview.RoundedImageView;
import com.rxmuhammadyoussef.anabeesh.R;
import com.rxmuhammadyoussef.anabeesh.store.model.TimeLineItemType;
import com.rxmuhammadyoussef.anabeesh.store.model.answer.AnswerViewModel;
import com.rxmuhammadyoussef.anabeesh.store.model.question.QuestionViewModel;
import com.rxmuhammadyoussef.anabeesh.store.model.questiondetails.AnswerTimelineItem;
import com.rxmuhammadyoussef.anabeesh.store.model.questiondetails.QuestionTimelineItem;
import com.rxmuhammadyoussef.anabeesh.util.GlideApp;
import com.rxmuhammadyoussef.core.di.qualifier.ForActivity;
import com.rxmuhammadyoussef.core.di.scope.ActivityScope;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

@ActivityScope
class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final LayoutInflater layoutInflater;
    private final QuestionDetailsPresenter presenter;
    private final Context context;

    @Inject
    RecyclerAdapter(@ForActivity Context context, QuestionDetailsPresenter presenter) {
        this.layoutInflater = LayoutInflater.from(context);
        this.presenter = presenter;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        return presenter.getTimeLineItems().get(position).getType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TimeLineItemType.ANSWER_ITEM) {
            return new AnswerViewHolder(layoutInflater.inflate(R.layout.item_answer, new ConstraintLayout(context), false));
        }
        return new QuestionViewHolder(layoutInflater.inflate(R.layout.item_question_details, new ConstraintLayout(context), false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof QuestionViewHolder) {
            ((QuestionViewHolder) holder).bind(((QuestionTimelineItem) presenter.getTimeLineItems().get(position)).getQuestionViewModel());
        } else if (holder instanceof AnswerViewHolder) {
            ((AnswerViewHolder) holder).bind(((AnswerTimelineItem) presenter.getTimeLineItems().get(position)).getAnswerViewModel());
        }
    }

    @Override
    public int getItemCount() {
        return presenter.getTimeLineItems().size();
    }

    class AnswerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_answer)
        TextView answerTextView;

        AnswerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(AnswerViewModel answerViewModel) {
            answerTextView.setText(answerViewModel.getText());
        }
    }

    class QuestionViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_header)
        TextView headerTextView;
        @BindView(R.id.tv_body)
        TextView bodyTextView;
        @BindView(R.id.iv_cover)
        RoundedImageView coverImageView;
        @BindView(R.id.tv_comment_counter)
        TextView commentCounterTextView;
        @BindView(R.id.tv_upvote_counter)
        TextView upDownVoteCounterTextView;

        QuestionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(QuestionViewModel questionViewModel) {
            headerTextView.setText(questionViewModel.getHeadline());
            bodyTextView.setText(questionViewModel.getDescription());
            commentCounterTextView.setText(questionViewModel.getNumberOfAnswers());
            //TODO calc percentage
            upDownVoteCounterTextView.setText("0%");
            GlideApp.with(itemView)
                    .load(questionViewModel.getCoverUrl())
                    .placeholder(R.color.colorTextSecondaryLight)
                    .signature(new ObjectKey(questionViewModel.getId()))
                    .centerCrop()
                    .into(coverImageView);
        }
    }
}
