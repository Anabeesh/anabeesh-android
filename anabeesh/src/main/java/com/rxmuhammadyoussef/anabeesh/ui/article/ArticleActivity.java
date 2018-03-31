package com.rxmuhammadyoussef.anabeesh.ui.article;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.rxmuhammadyoussef.anabeesh.AnabeeshApplication;
import com.rxmuhammadyoussef.anabeesh.R;
import com.rxmuhammadyoussef.anabeesh.di.activity.ActivityModule;
import com.rxmuhammadyoussef.core.component.activity.BaseActivity;
import com.rxmuhammadyoussef.core.di.scope.ActivityScope;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

@ActivityScope
public class ArticleActivity extends BaseActivity implements ArticleScreen {

    @BindView(R.id.tv_title)
    TextView titleTextView;
    @BindView(R.id.tb_host)
    Toolbar toolbar;
    @BindView(R.id.rv_articles)
    RecyclerView articlesRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout refreshLayout;

    @Inject
    ArticlePresenter presenter;
    @Inject
    RecyclerAdapter adapter;

    @Override
    protected void onCreateActivityComponents() {
        AnabeeshApplication.getComponent(this)
                .plus(new ActivityModule(this))
                .inject(this);
        ButterKnife.bind(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_article;
    }

    @Override
    public void setupRecyclerView() {
        articlesRecyclerView.setHasFixedSize(true);
        articlesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        articlesRecyclerView.setAdapter(adapter);
    }

    @Override
    public void setupRefreshLayout() {
        refreshLayout.setOnRefreshListener(presenter::fetch);
    }

    @Override
    public void showLoadingAnimation() {
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoadingAnimation() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        titleTextView.setText(R.string.articles);
    }

    @Override
    public void updateUi(DiffUtil.DiffResult diffResult) {
        diffResult.dispatchUpdatesTo(adapter);
    }
}
