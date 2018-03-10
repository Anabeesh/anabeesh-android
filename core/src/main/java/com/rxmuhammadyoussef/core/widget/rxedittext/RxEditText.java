package com.rxmuhammadyoussef.core.widget.rxedittext;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputType;
import android.util.AttributeSet;

import com.jakewharton.rxbinding2.widget.RxTextView;

public class RxEditText extends AppCompatEditText {

    private RxEditTextPresenter presenter;

    public RxEditText(Context context) {
        super(context);
        init(context);
    }

    public RxEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RxEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    protected void init(Context context) {
        presenter = new RxEditTextPresenter(context);
        setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
    }

    @Override
    protected void onDetachedFromWindow() {
        if (presenter != null) {
            presenter.onDetachedFromWindow();
        }
        super.onDetachedFromWindow();
    }

    public void setTextChangesListener(TextChangesListener textChangesListener) {
        presenter.setTextChangesListener(RxTextView.afterTextChangeEvents(this), textChangesListener);
    }

    public void setValidityListener(ValidityListener validityListener) {
        presenter.listenIfValid(RxTextView.afterTextChangeEvents(this), validityListener);
    }
}
