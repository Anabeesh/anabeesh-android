package com.rxmuhammadyoussef.core.widget.rxedittext;

import android.content.Context;

import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.widget.TextViewAfterTextChangeEvent;
import com.rxmuhammadyoussef.core.util.Preconditions;
import com.rxmuhammadyoussef.core.util.TextUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class RxEditTextPresenter {

    protected final TextUtil textUtil;
    protected final CompositeDisposable disposable;

    protected RxEditTextPresenter(Context context) {
        disposable = new CompositeDisposable();
        textUtil = new TextUtil(context);
    }

    void setTextChangesListener(InitialValueObservable<TextViewAfterTextChangeEvent> afterTextChangeObservable, TextChangesListener<String> textChangesListener) {
        Preconditions.checkNonNull(textChangesListener, "textChangesListener cannot be null");
        disposable.add(
                afterTextChangeObservable
                        .filter(et -> et.view().isFocused())
                        .map(TextViewAfterTextChangeEvent::editable)
                        .map(CharSequence::toString)
                        .map(String::trim)
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(textChangesListener::onChanged, Timber::e));
    }

    void listenIfValid(InitialValueObservable<TextViewAfterTextChangeEvent> afterTextChangeObservable, TextChangesListener<TextUtil.Result> validityListener) {
        Preconditions.checkNonNull(validityListener, "validityListener cannot be null");
        disposable.add(
                afterTextChangeObservable
                        .filter(et -> et.view() != null)
                        .map(TextViewAfterTextChangeEvent::editable)
                        .map(CharSequence::toString)
                        .map(String::trim)
                        .map(textUtil::getIfNotEmptyStringResult)
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(validityListener::onChanged, Timber::e));
    }

    void onDetachedFromWindow() {
        disposable.dispose();
    }
}
