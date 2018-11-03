package com.ramapps.ramplaces.app.ui.view;


import com.ramapps.ramplaces.app.common.BasePresenter;

/**
 * Created by Ramsheed on 11/03/2018.
 * BaseView
 */

public interface BaseView {

    /*
    Full Sized Progress showing function
     */
    void showProgress();

    /*
    Full Sized Progress Hiding function
     */
    void hideProgress();

    /*
    @message - Message to show
     */
    void showMessage(String message);

    void setPresenter(BasePresenter presenters);

    void showAlert(String title, String message, int duration);

    void showAlert(String message, int duration);
}
