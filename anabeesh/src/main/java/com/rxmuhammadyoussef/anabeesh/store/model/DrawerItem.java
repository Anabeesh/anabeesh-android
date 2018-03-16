package com.rxmuhammadyoussef.anabeesh.store.model;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.rxmuhammadyoussef.anabeesh.store.model.DrawerItem.BOOKMARKS;
import static com.rxmuhammadyoussef.anabeesh.store.model.DrawerItem.HOME;
import static com.rxmuhammadyoussef.anabeesh.store.model.DrawerItem.INTERESTS;
import static com.rxmuhammadyoussef.anabeesh.store.model.DrawerItem.JOIN_US;
import static com.rxmuhammadyoussef.anabeesh.store.model.DrawerItem.SETTINGS;

@Retention(RetentionPolicy.RUNTIME)
@IntDef({HOME, BOOKMARKS, INTERESTS, JOIN_US, SETTINGS})
public @interface DrawerItem {
    int HOME = 0;
    int BOOKMARKS = 1;
    int INTERESTS = 2;
    int JOIN_US = 3;
    int SETTINGS = 4;
}
