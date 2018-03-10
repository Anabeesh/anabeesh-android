package com.rxmuhammadyoussef.core.util.permission;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;

import static com.rxmuhammadyoussef.core.util.permission.PermissionType.FINE_LOCATION;
import static com.rxmuhammadyoussef.core.util.permission.PermissionType.WRITE_EXTERNAL_STORAGE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

@Retention(SOURCE)
@IntDef({FINE_LOCATION, WRITE_EXTERNAL_STORAGE})
public @interface PermissionType {

    int FINE_LOCATION = 0;
    int WRITE_EXTERNAL_STORAGE = 1;
}
