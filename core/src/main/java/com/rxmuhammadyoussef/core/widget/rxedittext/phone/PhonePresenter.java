package com.rxmuhammadyoussef.core.widget.rxedittext.phone;

import android.content.Context;
import android.util.Log;

import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.widget.TextViewAfterTextChangeEvent;
import com.rxmuhammadyoussef.core.util.Preconditions;
import com.rxmuhammadyoussef.core.util.TextUtil;
import com.rxmuhammadyoussef.core.widget.rxedittext.RxEditTextPresenter;
import com.rxmuhammadyoussef.core.widget.rxedittext.ValidityListener;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

class PhonePresenter extends RxEditTextPresenter {

    private final TextUtil textUtil;

    PhonePresenter(Context context) {
        super(context);
        textUtil = new TextUtil(context);
    }

    void listenIfValid(InitialValueObservable<TextViewAfterTextChangeEvent> afterTextChangeObservable, ValidityListener validityListener) {
        Preconditions.checkNonNull(validityListener, "validityListener cannot be null");
        disposable.add(
                afterTextChangeObservable
                        .filter(et -> et.view() != null)
                        .map(TextViewAfterTextChangeEvent::editable)
                        .map(CharSequence::toString)
                        .map(String::trim)
                        .doOnNext(text -> Log.d("Muhammad", text))
                        .map(textUtil::getIfValidPhoneNumberResult)
                        .doOnNext(text -> Log.d("Muhammad", String.valueOf(text.isValid())))
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(validityListener::onAfterTextChange, Timber::e));
    }
}