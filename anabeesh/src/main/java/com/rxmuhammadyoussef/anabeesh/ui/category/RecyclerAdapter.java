package com.rxmuhammadyoussef.anabeesh.ui.category;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.signature.ObjectKey;
import com.makeramen.roundedimageview.RoundedImageView;
import com.rxmuhammadyoussef.anabeesh.R;
import com.rxmuhammadyoussef.anabeesh.events.operation.OperationAnimationListener;
import com.rxmuhammadyoussef.anabeesh.store.model.category.CategoryViewModel;
import com.rxmuhammadyoussef.anabeesh.util.GlideApp;
import com.rxmuhammadyoussef.core.di.qualifier.ForFragment;
import com.rxmuhammadyoussef.core.di.scope.FragmentScope;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@FragmentScope
class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.CategoryViewHolder> {

    private final LayoutInflater layoutInflater;
    private final CategoryPresenter presenter;
    private final Context context;

    @Inject
    RecyclerAdapter(@ForFragment Context context, CategoryPresenter presenter) {
        this.layoutInflater = LayoutInflater.from(context);
        this.presenter = presenter;
        this.context = context;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CategoryViewHolder(layoutInflater.inflate(R.layout.item_category, new ConstraintLayout(context), false));
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        holder.bind(presenter.getCategoryViewModels().get(position));
    }

    @Override
    public int getItemCount() {
        return presenter.getCategoryViewModels().size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {

        private CategoryViewModel categoryViewModel;

        @BindView(R.id.tv_title)
        TextView titleTextView;
        @BindView(R.id.btn_follow)
        TextView followButton;
        @BindView(R.id.iv_category)
        RoundedImageView categoryImageView;
        @BindView(R.id.pb_follow)
        ProgressBar followProgressBar;

        CategoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(CategoryViewModel categoryViewModel) {
            this.categoryViewModel = categoryViewModel;
            GlideApp.with(itemView)
                    .load(categoryViewModel.getImageUrl())
                    .placeholder(R.color.colorTextSecondaryLight)
                    .signature(new ObjectKey(categoryViewModel.getId()))
                    .centerCrop()
                    .into(categoryImageView);
            titleTextView.setText(categoryViewModel.getName());
            if (categoryViewModel.isFollowing()) {
                followButton.setText(context.getString(R.string.following));
                followButton.setActivated(false);
            } else {
                followButton.setText(context.getString(R.string.follow));
                followButton.setActivated(true);
            }
        }

        @OnClick(R.id.btn_follow)
        void onFollowClicked() {
            presenter.followOrUnFollowCategory(getAdapterPosition(), categoryViewModel, new OperationAnimationListener<CategoryViewModel>() {
                @Override
                public void onPreExecute() {
                    followProgressBar.setVisibility(View.VISIBLE);
                    followButton.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onPostExecute() {
                    followProgressBar.setVisibility(View.GONE);
                    followButton.setVisibility(View.VISIBLE);
                }
            });
        }
    }
}
