package com.rxmuhammadyoussef.core.widget.rxedittext.multiline;

import android.content.Context;
import android.text.InputType;
import android.util.AttributeSet;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.rxmuhammadyoussef.core.widget.rxedittext.RxEditText;
import com.rxmuhammadyoussef.core.widget.rxedittext.ValidityListener;

public class MultiLineEditText extends RxEditText {

    private MultiLineEditTextPresenter presenter;

    public MultiLineEditText(Context context) {
        super(context);
    }

    public MultiLineEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MultiLineEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init(Context context) {
        presenter = new MultiLineEditTextPresenter(context);
        setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
    }

    @Override
    public void setValidityListener(ValidityListener validityListener) {
        presenter.listenIfValid(RxTextView.afterTextChangeEvents(this), validityListener);
    }
}
