package com.rxmuhammadyoussef.core.widget.rxedittext.password;

import android.content.Context;
import android.text.InputType;
import android.util.AttributeSet;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.rxmuhammadyoussef.core.util.TextUtil;
import com.rxmuhammadyoussef.core.widget.rxedittext.RxEditText;
import com.rxmuhammadyoussef.core.widget.rxedittext.TextChangesListener;

public class PasswordEditText extends RxEditText {

    private PasswordPresenter presenter;

    public PasswordEditText(Context context) {
        super(context);
    }

    public PasswordEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PasswordEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init(Context context) {
        presenter = new PasswordPresenter(context);
        setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }

    @Override
    public void setValidityListener(TextChangesListener<TextUtil.Result> validityListener) {
        presenter.listenIfValid(RxTextView.afterTextChangeEvents(this), validityListener);
    }

    public void checIfPasswordsMatches(PasswordEditText confirmationEditText, TextChangesListener<TextUtil.Result> validityListener) {
        presenter.checkIfPasswordMatches(
                RxTextView.afterTextChangeEvents(this),
                RxTextView.afterTextChangeEvents(confirmationEditText),
                validityListener);
    }
}
