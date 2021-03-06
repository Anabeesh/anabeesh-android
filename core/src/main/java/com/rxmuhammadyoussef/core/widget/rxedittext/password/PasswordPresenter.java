package com.rxmuhammadyoussef.core.widget.rxedittext.password;

import android.content.Context;

import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.widget.TextViewAfterTextChangeEvent;
import com.rxmuhammadyoussef.core.util.Preconditions;
import com.rxmuhammadyoussef.core.util.TextUtil;
import com.rxmuhammadyoussef.core.widget.rxedittext.RxEditTextPresenter;
import com.rxmuhammadyoussef.core.widget.rxedittext.TextChangesListener;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

class PasswordPresenter extends RxEditTextPresenter {

    PasswordPresenter(Context context) {
        super(context);
    }

    void listenIfValid(InitialValueObservable<TextViewAfterTextChangeEvent> afterTextChangeObservable, TextChangesListener<TextUtil.Result> validityListener) {
        Preconditions.checkNonNull(validityListener, "validityListener cannot be null");
        disposable.add(
                afterTextChangeObservable
                        .filter(et -> et.view() != null)
                        .map(TextViewAfterTextChangeEvent::editable)
                        .map(CharSequence::toString)
                        .map(String::trim)
                        .map(textUtil::getIfValidPasswordResult)
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(validityListener::onChanged, Timber::e));
    }

    private Observable<String> getStringObservable(InitialValueObservable<TextViewAfterTextChangeEvent> afterTextChangeEvent) {
        return afterTextChangeEvent
                .filter(et -> et.view() != null)
                .map(TextViewAfterTextChangeEvent::editable)
                .map(CharSequence::toString)
                .map(String::trim);
    }

    void checkIfPasswordMatches(InitialValueObservable<TextViewAfterTextChangeEvent> passwordAfterTextChangeEvent,
                                InitialValueObservable<TextViewAfterTextChangeEvent> confirmationAfterTextChangeEvent,
                                TextChangesListener<TextUtil.Result> validityListener) {
        Preconditions.checkNonNull(validityListener, "validityListener cannot be null");
        disposable.add(
                Observable.combineLatest(
                        getStringObservable(passwordAfterTextChangeEvent),
                        getStringObservable(confirmationAfterTextChangeEvent),
                        textUtil::getIfPasswordsMatchResult)
                        .distinctUntilChanged()
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(validityListener::onChanged, Timber::e));
    }
}