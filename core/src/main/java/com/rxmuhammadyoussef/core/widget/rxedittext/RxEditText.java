package com.rxmuhammadyoussef.core.widget.rxedittext;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputType;
import android.util.AttributeSet;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewAfterTextChangeEvent;
import com.rxmuhammadyoussef.core.util.TextUtil;

import io.reactivex.Observable;

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
        setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
    }

    @Override
    protected void onDetachedFromWindow() {
        if (presenter != null) {
            presenter.onDetachedFromWindow();
        }
        super.onDetachedFromWindow();
    }

    public void setTextChangesListener(TextChangesListener<String> textChangesListener) {
        presenter.setTextChangesListener(RxTextView.afterTextChangeEvents(this), textChangesListener);
    }

    public void setValidityListener(TextChangesListener<TextUtil.Result> validityListener) {
        presenter.listenIfValid(RxTextView.afterTextChangeEvents(this), validityListener);
    }

    public Observable<String> getTextObservable() {
        return RxTextView.afterTextChangeEvents(this)
                .filter(et -> true)
                .map(TextViewAfterTextChangeEvent::editable)
                .map(CharSequence::toString)
                .map(String::trim);
    }
}
