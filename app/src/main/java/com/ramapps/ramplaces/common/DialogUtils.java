package com.ramapps.ramplaces.common;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;

import com.ramapps.ramplaces.R;


/**
 * Created by Ginish on 11/03/2018.
 * All the dialog related functions goes here
 */
public class DialogUtils {

    public static void showMessage(Activity context, @NonNull String message) {
        try {
            //noinspection ConstantConditions
            if (context != null && message != null && !TextUtils.isEmpty(message))
                if (!context.isFinishing()) {
                    AlertDialog.Builder builder =
                            new AlertDialog.Builder(context, R.style.ConfirmDialog);
                    builder.setTitle(context.getResources().getString(R.string.app_name));
                    builder.setMessage(message);
                    builder.setPositiveButton(context.getString(R.string.ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showToast(View mView, @NonNull String message) {
        //noinspection ConstantConditions
        if (message != null && !TextUtils.isEmpty(message)) {
            Snackbar.make(mView, message, Snackbar.LENGTH_LONG)
                    .show();
        }
    }

    public static void showLoadingDialog(ProgressDialog loadingDialog, Activity mActivity) {
        if (loadingDialog != null) {
            if (!loadingDialog.isShowing() && !loadingDialog.isAdded())
                loadingDialog.show(mActivity.getFragmentManager(), "");
        } else {
            loadingDialog = new ProgressDialog();
            loadingDialog.show(mActivity.getFragmentManager(), "");
        }
    }

    public static void hideLoadingDialog(ProgressDialog loadingDialog) {
        if (loadingDialog != null && (loadingDialog.isVisible() || loadingDialog.isShowing()))
            loadingDialog.dismissAllowingStateLoss();
    }


    public static void showConfirm(Context context, String message, final Callback<Boolean> status) {
        AlertDialog.Builder alrt = new AlertDialog.Builder(context, R.style.ConfirmDialog);
        alrt.setTitle(R.string.app_name);
        alrt.setMessage(message);
        alrt.setCancelable(false);
        alrt.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                status.execute(true);
            }
        });
        alrt.setNegativeButton("no", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                status.execute(false);
            }
        });
        AlertDialog confirms = alrt.create();
        confirms.show();

    }

    public static void showOKWithAction(final Activity context, String message, final Callback<Boolean> status) {
        AlertDialog.Builder alrt = new AlertDialog.Builder(context, R.style.ConfirmDialog);
        alrt.setTitle(R.string.app_name);
        alrt.setMessage(message);
        alrt.setCancelable(false);
        alrt.setPositiveButton("ok ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                status.execute(true);
            }
        });
        AlertDialog confirms = alrt.create();
        confirms.show();
    }
}
