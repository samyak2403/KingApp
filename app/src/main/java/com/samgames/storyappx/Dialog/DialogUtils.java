package com.samgames.storyappx.Dialog;

import android.app.ProgressDialog;
import android.content.Context;
import androidx.appcompat.app.AlertDialog;

public class DialogUtils {
    private static ProgressDialog progressDialog;

    public static void showLoadingDialog(Context context, String message) {
        dismissLoadingDialog();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public static void dismissLoadingDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    public static void showErrorDialog(Context context, String title, String message) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }
}