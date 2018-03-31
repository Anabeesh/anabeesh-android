package com.rxmuhammadyoussef.anabeesh.ui.questiondetails;

import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.bumptech.glide.signature.ObjectKey;
import com.makeramen.roundedimageview.RoundedImageView;
import com.rxmuhammadyoussef.anabeesh.AnabeeshApplication;
import com.rxmuhammadyoussef.anabeesh.R;
import com.rxmuhammadyoussef.anabeesh.di.activity.ActivityModule;
import com.rxmuhammadyoussef.anabeesh.store.model.question.QuestionViewModel;
import com.rxmuhammadyoussef.anabeesh.util.GlideApp;
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
    TextView title;
    @BindView(R.id.tv_header)
    TextView headerTextView;
    @BindView(R.id.tv_body)
    TextView bodyTextView;
    @BindView(R.id.iv_cover)
    RoundedImageView coverImageView;

    @Inject
    QuestionDetailsPresenter presenter;

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
        title.setText(R.string.question_details);
    }

    @Override
    public void updateUi(QuestionViewModel questionViewModel) {
        headerTextView.setText(questionViewModel.getHeadline());
        bodyTextView.setText(questionViewModel.getDescription());
        GlideApp.with(this)
                .load(questionViewModel.getCoverUrl())
                .placeholder(R.color.colorTextSecondaryLight)
                .signature(new ObjectKey(questionViewModel.getId()))
                .centerCrop()
                .into(coverImageView);
    }
}
