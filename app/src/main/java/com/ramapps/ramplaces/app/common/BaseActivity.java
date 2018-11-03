package com.ramapps.ramplaces.app.common;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;

import com.tapadoo.alerter.Alerter;

import com.ramapps.ramplaces.R;
import com.ramapps.ramplaces.app.ui.view.BaseView;
import com.ramapps.ramplaces.common.DialogUtils;
import com.ramapps.ramplaces.common.ProgressDialog;

/**
 * Created by Ramsheed on 11/03/2018.
 * BaseActivity used in this application
 */

public class BaseActivity extends AppCompatActivity implements BaseView {

    private BasePresenter presenter;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    public void showProgress() {
        if (dialog == null) {
            dialog = new ProgressDialog();
            dialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen);
        }
        DialogUtils.showLoadingDialog(dialog, this);
    }

    @Override
    public void hideProgress() {
        DialogUtils.hideLoadingDialog(dialog);
    }

    @Override
    public void showMessage(String message) {
        DialogUtils.showMessage(this, message);
    }


    @Override
    public void showAlert(String title, String message, int duration) {
        Alerter.create(this)
                .setTitle(title)
                .setText(message)
                .setDuration(duration)
                .enableSwipeToDismiss()
                .setBackgroundColorRes(R.color.colorAccent)
                .enableIconPulse(false)
                .show();
    }

    @Override
    public void showAlert(String message, int duration) {
        Alerter.create(this)
                .setText(message)
                .setDuration(duration)
                .enableSwipeToDismiss()
                .enableIconPulse(false)
                .setBackgroundColorRes(R.color.colorAccent)
                .show();
    }

    @Override
    public void setPresenter(BasePresenter presenters) {
        this.presenter = presenters;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null)
            presenter.onDestroy();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
