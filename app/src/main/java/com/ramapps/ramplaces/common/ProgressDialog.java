package com.ramapps.ramplaces.common;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.ramapps.ramplaces.R;
import com.ramapps.ramplaces.databinding.DialogProgressNormalBinding;

/**
 * Created by Ramsheed on 11/03/2018.
 * Progress Dialog implementation
 * Small Progress Dialog
 */


public final class ProgressDialog extends DialogFragment {

    public static final String BUNDLE_TITLE = "title";
    private boolean isShowing = false;
    private int mDialogWidth = -1;
    private int mDialogHeight = -1;
    private DialogProgressNormalBinding binding;


    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Sets a custom dialog window size
        final Resources res = getResources();
        setDialogWidth(res.getDimensionPixelSize(R.dimen.loading_dialog_width));
        setDialogHeight(res.getDimensionPixelSize(R.dimen.loading_dialog_height));
        setCancelable(false);
    }

    public void setMessage(String title) {
        final Bundle bundle = new Bundle();
        if (!isShowing()) {
            bundle.putString(ProgressDialog.BUNDLE_TITLE, title);
            setArguments(bundle);
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        hideTitleBar();
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_progress_normal, container, false);
        return binding.getRoot();
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        isShowing = true;
        super.show(manager, tag);
    }

    @Override
    public void dismissAllowingStateLoss() {
        isShowing = false;
        super.dismissAllowingStateLoss();
    }

    public boolean isShowing() {
        return isShowing;
    }


    /**
     * Set the dialog's window width.
     * If width < 1, then the default width will be used.
     * This should be set before the onResume() lifecycle triggers.
     *
     * @param width The window width
     */
    protected void setDialogWidth(final int width) {
        if (width < 1) {
            mDialogWidth = -1;
        } else {
            mDialogWidth = width;
        }
    }

    /**
     * Set the dialog's window height.
     * If height < 1, then the default height will be used.
     * This should be set before the onResume() lifecycle triggers.
     *
     * @param height The window height
     */
    protected void setDialogHeight(final int height) {
        if (height < 1) {
            mDialogHeight = -1;
        } else {
            mDialogHeight = height;
        }
    }

    /**
     * Hide the dialog's title bar.
     */
    protected void hideTitleBar() {
        final Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        final Dialog dialog = getDialog();
        if (dialog != null) {
            // Adjust the dialog's window settings
            final Window window = dialog.getWindow();
            final WindowManager.LayoutParams lp = window.getAttributes();
            if (mDialogWidth != -1) {
                lp.width = mDialogWidth;
            }
            if (mDialogHeight != -1) {
                lp.height = mDialogHeight;
            }
            window.setAttributes(lp);
        }
    }

}