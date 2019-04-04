package com.app.smt.shiper.util.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.TextUtils;


public final class DialogUtils {

    public static AlertDialog showDialogDefault(Context context, String title, String message, String btnOk) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (!TextUtils.isEmpty(title))
            builder.setTitle(title);
        if (!TextUtils.isEmpty(message))
            builder.setMessage(Html.fromHtml(message));
        builder.setCancelable(true);

        builder.setPositiveButton(btnOk, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });

        return builder.create();
    }

    public static AlertDialog showDialogDefault(Context context, String message, String btnOk) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (!TextUtils.isEmpty(message))
            builder.setMessage(Html.fromHtml(message));
        builder.setCancelable(true);

        builder.setPositiveButton(btnOk, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });

        return builder.create();
    }

    public static AlertDialog showDialogDefault(Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (!TextUtils.isEmpty(message))
            builder.setMessage(Html.fromHtml(message));
        builder.setCancelable(true);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });

        return builder.create();
    }

    public static AlertDialog showDialogDefault(Context context, String message, String btnOk, String btnCancel, final DialogCallback dialogCallback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (!TextUtils.isEmpty(message))
            builder.setMessage(Html.fromHtml(message));
        builder.setCancelable(false);

        builder.setPositiveButton(btnOk, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (dialogCallback != null)
                    dialogCallback.okDialog();
            }
        });
        if (!TextUtils.isEmpty(btnCancel)) {
            builder.setNegativeButton(btnCancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    if (dialogCallback != null)
                        dialogCallback.cancelDialog();
                }
            });
        }

        return builder.create();
    }

    public interface DialogCallback {
        void cancelDialog();

        void okDialog();
    }

    public interface DialogInputCallback {
        void onConfirm(String text);
        void onCancel();
    }

}
