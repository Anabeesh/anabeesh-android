package com.rxmuhammadyoussef.core.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rxmuhammadyoussef.core.R;

import javax.inject.Inject;

public class UiUtil {

    private final Context context;
    private ProgressDialog progressDialog;

    @Inject
    public UiUtil(Context context) {
        Preconditions.checkNonNull(context, "should not pass null context reference");
        this.context = context;
    }

    public ProgressDialog getProgressDialog(String message) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
        }
        progressDialog.setCancelable(false);
        progressDialog.setMessage(Preconditions.requireStringNotEmpty(message));
        return progressDialog;
    }

    public ProgressDialog getProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
        }
        return progressDialog;
    }

    public Toast getSuccessToast(String message) {
        return createToast(getLayoutInflater().inflate(R.layout.layout_success_toast, new FrameLayout(context)), message);
    }

    private Toast createToast(View view, String message) {
        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        ((TextView) view.findViewById(R.id.tv_message)).setText(message);
        toast.setView(view);
        return toast;
    }

    private LayoutInflater getLayoutInflater() {
        return LayoutInflater.from(context);
    }

    public Toast getWarningToast(String message) {
        return createToast(getLayoutInflater().inflate(R.layout.layout_warning_toast, new FrameLayout(context)), message);
    }

    public Toast getErrorToast(String message) {
        return createToast(getLayoutInflater().inflate(R.layout.layout_error_toast, new FrameLayout(context)), message);
    }

    public Toast getAnnouncementToast(String message) {
        return createToast(getLayoutInflater().inflate(R.layout.layout_announcement_toast, new FrameLayout(context)), message);
    }

    public Snackbar getSuccessSnackBar(View view, String content) {
        Snackbar snackbar = Snackbar.make(view, "", Snackbar.LENGTH_LONG);
        Snackbar.SnackbarLayout snackBarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackBarLayout.setBackgroundColor(0x00000000);
        snackBarLayout.setPadding(0, 0, 0, 0);
        snackBarLayout.findViewById(android.support.design.R.id.snackbar_text).setVisibility(View.INVISIBLE);
        View snackContainer = LayoutInflater.from(context).inflate(R.layout.view_snackbar, null, false);
        TextView contentView = snackContainer.findViewById(R.id.snackbar_content);
        contentView.setText(content);
        snackBarLayout.addView(snackContainer, 0);
        return snackbar;
    }

    public Snackbar getErrorSnackBar(View view, String content) {
        Snackbar snackbar = Snackbar.make(view, "", Snackbar.LENGTH_LONG);
        Snackbar.SnackbarLayout snackBarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackBarLayout.setBackgroundColor(0x00000000);
        snackBarLayout.setPadding(0, 0, 0, 0);
        snackBarLayout.findViewById(android.support.design.R.id.snackbar_text).setVisibility(View.INVISIBLE);
        View snackContainer = LayoutInflater.from(context).inflate(R.layout.view_snackbar, null, false);
        TextView contentView = snackContainer.findViewById(R.id.snackbar_content);
        contentView.setText(content);
        snackBarLayout.addView(snackContainer, 0);
        return snackbar;
    }

    public Snackbar getWarningSnackBar(View view, String content) {
        Snackbar snackbar = Snackbar.make(view, "", Snackbar.LENGTH_LONG);
        Snackbar.SnackbarLayout snackBarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackBarLayout.setBackgroundColor(0x00000000);
        snackBarLayout.setPadding(0, 0, 0, 0);
        snackBarLayout.findViewById(android.support.design.R.id.snackbar_text).setVisibility(View.INVISIBLE);
        View snackContainer = LayoutInflater.from(context).inflate(R.layout.view_snackbar, null, false);
        TextView contentView = snackContainer.findViewById(R.id.snackbar_content);
        contentView.setText(content);
        snackBarLayout.addView(snackContainer, 0);
        return snackbar;
    }

    public Snackbar getAnnouncementSnackBar(View view, String content) {
        Snackbar snackbar = Snackbar.make(view, "", Snackbar.LENGTH_LONG);
        Snackbar.SnackbarLayout snackBarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackBarLayout.setBackgroundColor(0x00000000);
        snackBarLayout.setPadding(0, 0, 0, 0);
        snackBarLayout.findViewById(android.support.design.R.id.snackbar_text).setVisibility(View.INVISIBLE);
        View snackContainer = LayoutInflater.from(context).inflate(R.layout.view_snackbar, null, false);
        TextView contentView = snackContainer.findViewById(R.id.snackbar_content);
        contentView.setText(content);
        snackBarLayout.addView(snackContainer, 0);
        return snackbar;
    }

    public void showKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    public void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}