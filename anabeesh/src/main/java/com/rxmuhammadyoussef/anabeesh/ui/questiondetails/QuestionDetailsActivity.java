package com.rxmuhammadyoussef.anabeesh.ui.questiondetails;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.rxmuhammadyoussef.anabeesh.AnabeeshApplication;
import com.rxmuhammadyoussef.anabeesh.R;
import com.rxmuhammadyoussef.anabeesh.di.activity.ActivityModule;
import com.rxmuhammadyoussef.anabeesh.widget.LinearLayoutManagerWrapper;
import com.rxmuhammadyoussef.anabeesh.widget.SimpleDividerItemDecoration;
import com.rxmuhammadyoussef.core.component.activity.BaseActivity;
import com.rxmuhammadyoussef.core.di.scope.ActivityScope;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

@ActivityScope
public class QuestionDetailsActivity extends BaseActivity implements QuestionDetailsScreen {

    @BindView(R.id.tb_details)
    Toolbar toolbar;
    @BindView(R.id.tv_title)
    TextView titleTextView;
    @BindView(R.id.rv_content)
    RecyclerView recyclerView;

    @Inject
    QuestionDetailsPresenter presenter;
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
        return R.layout.activity_question_details;
    }

    @Override
    public void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        titleTextView.setText(R.string.question_details);
    }

    @Override
    public void setupRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManagerWrapper(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void updateUi(DiffUtil.DiffResult diffResult) {
        diffResult.dispatchUpdatesTo(adapter);
    }
}
