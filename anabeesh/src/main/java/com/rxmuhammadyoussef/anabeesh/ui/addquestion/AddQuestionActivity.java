package com.rxmuhammadyoussef.anabeesh.ui.addquestion;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rxmuhammadyoussef.anabeesh.AnabeeshApplication;
import com.rxmuhammadyoussef.anabeesh.R;
import com.rxmuhammadyoussef.anabeesh.di.activity.ActivityModule;
import com.rxmuhammadyoussef.core.component.activity.BaseActivity;
import com.rxmuhammadyoussef.core.widget.rxedittext.RxEditText;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddQuestionActivity extends BaseActivity implements AddQuestionScreen {

    @BindView(R.id.et_title)
    RxEditText titleEditText;
    @BindView(R.id.et_post)
    RxEditText bodyEditText;
    @BindView(R.id.tv_post)
    TextView postTextView;
    @BindView(R.id.pb_post)
    ProgressBar postProgressBar;

    @Inject
    AddQuestionPresenter presenter;

    @Override
    protected void onCreateActivityComponents() {
        AnabeeshApplication.getComponent(this)
                .plus(new ActivityModule(this))
                .inject(this);
        ButterKnife.bind(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_add_question;
    }

    @Override
    public void setupEditText() {
        titleEditText.setValidityListener(presenter::onAfterTitleChanged);
        bodyEditText.setValidityListener(presenter::onAfterBodyChanged);
    }

    @Override
    public void setSendButtonEnabled(boolean enabled) {
        postTextView.setEnabled(enabled);
    }

    @Override
    public void onQuestionPostedSuccessfully() {
        getUiUtil().hideKeyboard(bodyEditText);
        finish();
    }

    @Override
    public void showLoadingAnimation() {
        postProgressBar.setVisibility(View.VISIBLE);
        postTextView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideLoadingAnimation() {
        postProgressBar.setVisibility(View.GONE);
        postTextView.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.tv_post)
    void onPostClicked() {
        getUiUtil().hideKeyboard(bodyEditText);
        presenter.postQuestion(titleEditText.getText().toString(), bodyEditText.getText().toString());
    }

    @OnClick(R.id.iv_close)
    void onCloseClick() {
        getUiUtil().hideKeyboard(bodyEditText);
        finish();
    }
}
