package com.rxmuhammadyoussef.anabeesh.ui.home;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.signature.ObjectKey;
import com.makeramen.roundedimageview.RoundedImageView;
import com.rxmuhammadyoussef.anabeesh.R;
import com.rxmuhammadyoussef.anabeesh.store.model.question.QuestionViewModel;
import com.rxmuhammadyoussef.anabeesh.store.model.timeline.QuestionTimelineItem;
import com.rxmuhammadyoussef.anabeesh.store.model.timeline.TimeLineItemType;
import com.rxmuhammadyoussef.anabeesh.store.model.timeline.TitleTimelineItem;
import com.rxmuhammadyoussef.anabeesh.ui.article.ArticleActivity;
import com.rxmuhammadyoussef.anabeesh.util.GlideApp;
import com.rxmuhammadyoussef.core.di.qualifier.ForFragment;
import com.rxmuhammadyoussef.core.di.scope.FragmentScope;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@FragmentScope
class TimelineRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final LayoutInflater layoutInflater;
    private final HomePresenter presenter;
    private final Context context;

    @Inject
    TimelineRecyclerAdapter(@ForFragment Context context, HomePresenter presenter) {
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
        if (viewType == TimeLineItemType.QUESTION_ITEM) {
            return new QuestionViewHolder(layoutInflater.inflate(R.layout.item_question, new ConstraintLayout(context), false));
        } else if (viewType == TimeLineItemType.TITLE) {
            return new TitleViewHolder(layoutInflater.inflate(R.layout.item_section_title, new ConstraintLayout(context), false));
        } else {
            return new ArticleListViewHolder(layoutInflater.inflate(R.layout.item_article_list, new ConstraintLayout(context), false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof QuestionViewHolder) {
            ((QuestionViewHolder) holder).bind(((QuestionTimelineItem) presenter.getTimeLineItems().get(position)).getQuestionViewModel());
        } else if (holder instanceof TitleViewHolder) {
            ((TitleViewHolder) holder).bind(((TitleTimelineItem) presenter.getTimeLineItems().get(position)).getTitle());
        } else if (holder instanceof ArticleListViewHolder) {
            ((ArticleListViewHolder) holder).bind();
        }
    }

    @Override
    public int getItemCount() {
        return presenter.getTimeLineItems().size();
    }

    class TitleViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title)
        TextView titleTextView;

        TitleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(String title) {
            titleTextView.setText(title);
        }
    }

    class ArticleListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.rv_articles)
        RecyclerView articleRecyclerView;

        ArticleListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind() {
            ArticleRecyclerAdapter adapter = new ArticleRecyclerAdapter(context, presenter);
            articleRecyclerView.setHasFixedSize(true);
            articleRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            articleRecyclerView.setAdapter(adapter);
        }

        @OnClick(R.id.tv_show_more)
        void onShowMoreClick() {
            context.startActivity(new Intent(context, ArticleActivity.class));
        }
    }

    class QuestionViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_header)
        TextView headerTextView;
        @BindView(R.id.tv_body)
        TextView bodyTextView;
        @BindView(R.id.iv_cover)
        RoundedImageView coverImageView;

        QuestionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(QuestionViewModel questionViewModel) {
            headerTextView.setText(questionViewModel.getHeadline());
            bodyTextView.setText(questionViewModel.getDescription());
            GlideApp.with(itemView)
                    .load(questionViewModel.getCoverUrl())
                    .placeholder(R.color.colorTextSecondaryLight)
                    .signature(new ObjectKey(questionViewModel.getId()))
                    .centerCrop()
                    .into(coverImageView);
        }
    }
}
