package com.app.smt.shiper.util.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.app.smt.shiper.R;


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

    public static AlertDialog showDialogFailOrder(Activity context, final DialogInputCallback dialogCallback) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_fail_order, null);
        final EditText editText = view.findViewById(R.id.dialog_fail_order_edittext);
        final TextView tvError = view.findViewById(R.id.dialog_fail_order_tv_error);
        final TextView[] tvReasonOption = new TextView[4];
        tvReasonOption[0] = view.findViewById(R.id.dialog_fail_order_tv_option_1);
        tvReasonOption[1] = view.findViewById(R.id.dialog_fail_order_tv_option_2);
        tvReasonOption[2] = view.findViewById(R.id.dialog_fail_order_tv_option_3);
        tvReasonOption[3] = view.findViewById(R.id.dialog_fail_order_tv_option_4);

        builder.setView(view);
        Button buttonOk = view.findViewById(R.id.btn_ok);
        Button buttonCancel = view.findViewById(R.id.btn_cancel);

        for (int i = 0; i < 4; i++) {
            final int j = i;
            tvReasonOption[j].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tvError.setVisibility(View.GONE);
                    if (tvReasonOption[j].isActivated())
                        tvReasonOption[j].setActivated(false);
                    else
                        tvReasonOption[j].setActivated(true);
                }
            });
        }

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvError.setVisibility(View.GONE);
                boolean isInputReason = false;

                String comment = editText.getText().toString().trim();
                if (!TextUtils.isEmpty(comment) && comment.trim().length() > 0) {
                    isInputReason = true;
                }
                for (int i = 0; i < 4; i++) {
                    if (tvReasonOption[i].isActivated()) {
                        comment = "[" + tvReasonOption[i].getText().toString() + "]" + comment;
                        isInputReason = true;
                    }
                }

                if (!isInputReason) {
                    tvError.setVisibility(View.VISIBLE);
                    return;
                }
                if (dialogCallback != null)
                    dialogCallback.onConfirm(comment);

            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogCallback != null)
                    dialogCallback.onCancel();
            }
        });
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
