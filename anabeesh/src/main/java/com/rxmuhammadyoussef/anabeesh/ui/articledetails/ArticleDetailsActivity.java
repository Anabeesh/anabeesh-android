package com.rxmuhammadyoussef.anabeesh.ui.articledetails;

import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.bumptech.glide.signature.ObjectKey;
import com.makeramen.roundedimageview.RoundedImageView;
import com.rxmuhammadyoussef.anabeesh.AnabeeshApplication;
import com.rxmuhammadyoussef.anabeesh.R;
import com.rxmuhammadyoussef.anabeesh.di.activity.ActivityModule;
import com.rxmuhammadyoussef.anabeesh.store.model.article.ArticleViewModel;
import com.rxmuhammadyoussef.anabeesh.util.GlideApp;
import com.rxmuhammadyoussef.core.component.activity.BaseActivity;
import com.rxmuhammadyoussef.core.di.scope.ActivityScope;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

@ActivityScope
public class ArticleDetailsActivity extends BaseActivity implements ArticleDetailsScreen {

    @BindView(R.id.tb_details)
    Toolbar toolbar;
    @BindView(R.id.tv_title)
    TextView title;
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

    @Inject
    ArticleDetailsPresenter presenter;

    @Override
    protected void onCreateActivityComponents() {
        AnabeeshApplication.getComponent(this)
                .plus(new ActivityModule(this))
                .inject(this);
        ButterKnife.bind(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_article_details;
    }

    @Override
    public void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        title.setText(R.string.articles_details);
    }

    @Override
    public void updateUi(ArticleViewModel articleViewModel) {
        headerTextView.setText(articleViewModel.getHeading());
        bodyTextView.setText(articleViewModel.getBody());
        userNameTextView.setText(articleViewModel.getUserName());
        GlideApp.with(this)
                .load(articleViewModel.getUserAvatarUrl())
                .placeholder(R.mipmap.ic_launcher)
                .centerCrop()
                .into(userAvatarImageView);
        GlideApp.with(this)
                .load(articleViewModel.getCoverUrl())
                .placeholder(R.color.colorTextSecondaryLight)
                .signature(new ObjectKey(articleViewModel.getId()))
                .centerCrop()
                .into(coverImageView);
    }
}
