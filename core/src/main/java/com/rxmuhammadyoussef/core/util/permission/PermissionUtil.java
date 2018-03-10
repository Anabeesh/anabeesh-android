package com.rxmuhammadyoussef.core.util.permission;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import com.rxmuhammadyoussef.core.component.activity.BaseActivity;

import java.util.ArrayList;

import javax.inject.Inject;

import static com.rxmuhammadyoussef.core.util.permission.PermissionType.FINE_LOCATION;
import static com.rxmuhammadyoussef.core.util.permission.PermissionType.WRITE_EXTERNAL_STORAGE;

public class PermissionUtil {

    private final BaseActivity activity;

    @Inject
    public PermissionUtil(BaseActivity activity) {
        this.activity = activity;
    }

    public boolean hasLocationPermission() {
        return !(ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED);
    }

    public boolean hasWritePermission() {
        return ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    public boolean isGPSEnabled() {
        LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        return locationManager != null && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public boolean hasPermissions(@NonNull String[] permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public boolean hasPermissions(@NonNull ArrayList<String> permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public boolean hasPermissionForType(@PermissionType int permissionType) {
        switch (permissionType) {
            case FINE_LOCATION:
                return hasLocationPermission();
            case WRITE_EXTERNAL_STORAGE:
                return hasWritePermission();
            default:
                return false;
        }
    }

    public void requestLocationPermission() {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
    }

    public void requestWritePermission() {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
    }

    public void requestPermissions(@NonNull String[] permissions) {
        ActivityCompat.requestPermissions(activity, permissions, 1);
    }
}
