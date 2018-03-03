package com.rxmuhammadyoussef.core.widget.rxedittext;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

import com.jakewharton.rxbinding2.widget.RxTextView;

public abstract class RxEditText extends AppCompatEditText {

    private final RxEditTextPresenter presenter = new RxEditTextPresenter();

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

    protected abstract void init(Context context);

    @Override
    protected void onDetachedFromWindow() {
        presenter.onDetachedFromWindow();
        super.onDetachedFromWindow();
    }

    public void setTextChangesListener(TextChangesListener textChangesListener) {
        presenter.setTextChangesListener(RxTextView.afterTextChangeEvents(this), textChangesListener);
    }

    public abstract void setValidityListener(ValidityListener validityListener);
}
