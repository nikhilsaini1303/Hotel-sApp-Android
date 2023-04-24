package com.example.assignment_1_android;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class LoadingAlert {

    Activity activity;
    AlertDialog dialog;

    LoadingAlert(Activity myActivity) {
        activity = myActivity;
    }

    @SuppressLint("InflateParams")
    void startAlertDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_layout, null));
        builder.setCancelable(true);
        dialog = builder.create();
        dialog.show();

    }

    void closeAlertDialog() {
        dialog.dismiss();
    }

}
