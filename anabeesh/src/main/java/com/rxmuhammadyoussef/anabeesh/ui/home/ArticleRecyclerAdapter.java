package com.rxmuhammadyoussef.anabeesh.ui.home;

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
import com.rxmuhammadyoussef.anabeesh.store.model.article.ArticleViewModel;
import com.rxmuhammadyoussef.anabeesh.util.GlideApp;
import com.rxmuhammadyoussef.core.di.qualifier.ForFragment;
import com.rxmuhammadyoussef.core.di.scope.FragmentScope;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

@FragmentScope
class ArticleRecyclerAdapter extends RecyclerView.Adapter<ArticleRecyclerAdapter.ArticleViewHolder> {

    private final LayoutInflater layoutInflater;
    private final HomePresenter presenter;
    private final Context context;

    @Inject
    ArticleRecyclerAdapter(@ForFragment Context context, HomePresenter presenter) {
        this.layoutInflater = LayoutInflater.from(context);
        this.presenter = presenter;
        this.context = context;
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ArticleViewHolder(layoutInflater.inflate(R.layout.item_article, new ConstraintLayout(context), false));
    }

    @Override
    public void onBindViewHolder(ArticleViewHolder holder, int position) {
        holder.bind(presenter.getArticleViewModels().get(position));
    }

    @Override
    public int getItemCount() {
        return presenter.getArticleViewModels().size();
    }

    class ArticleViewHolder extends RecyclerView.ViewHolder {

        private ArticleViewModel articleViewModel;

        @BindView(R.id.tv_header)
        TextView headerTextView;
        @BindView(R.id.tv_body)
        TextView bodyTextView;
        @BindView(R.id.tv_user_name)
        TextView userNameTextView;
        @BindView(R.id.iv_user)
        CircleImageView userAvatarImageView;
        @BindView(R.id.iv_cover)
        RoundedImageView coverImageView;

        ArticleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(ArticleViewModel articleViewModel) {
            this.articleViewModel = articleViewModel;
            headerTextView.setText(articleViewModel.getHeading());
            bodyTextView.setText(articleViewModel.getBody());
            userNameTextView.setText(articleViewModel.getUserName());
            GlideApp.with(itemView)
                    .load(articleViewModel.getUserAvatarUrl())
                    .placeholder(R.mipmap.ic_launcher)
                    .centerCrop()
                    .into(userAvatarImageView);
            GlideApp.with(itemView)
                    .load(articleViewModel.getCoverUrl())
                    .placeholder(R.color.colorTextSecondaryLight)
                    .signature(new ObjectKey(articleViewModel.getId()))
                    .centerCrop()
                    .into(coverImageView);
        }

        @OnClick(R.id.item_container)
        void onArticleClick() {
            //TODO navigate to article internal
        }
    }
}
