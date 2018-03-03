package com.rxmuhammadyoussef.core.widget.rxedittext;

import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.widget.TextViewAfterTextChangeEvent;
import com.rxmuhammadyoussef.core.util.Preconditions;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class RxEditTextPresenter {

    protected final CompositeDisposable disposable;

    protected RxEditTextPresenter() {
        disposable = new CompositeDisposable();
    }

    void setTextChangesListener(InitialValueObservable<TextViewAfterTextChangeEvent> afterTextChangeObservable, TextChangesListener textChangesListener) {
        Preconditions.checkNonNull(textChangesListener, "textChangesListener cannot be null");
        disposable.add(
                afterTextChangeObservable
                        .filter(et -> et.view().isFocused())
                        .map(TextViewAfterTextChangeEvent::editable)
                        .map(CharSequence::toString)
                        .map(String::trim)
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(textChangesListener::onAfterTextChange, Timber::e));
    }

    void onDetachedFromWindow() {
        disposable.dispose();
    }
}
