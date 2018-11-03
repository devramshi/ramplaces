package com.ramapps.ramplaces.app.common;

import android.content.Context;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Ramsheed on 11/03/2018.
 * BasePresenter class
 */

public abstract class BasePresenter<T> {

    private T view;

    private Context context;


    private CompositeDisposable compositeSubscription = new CompositeDisposable();

    /**
     * Setting View
     *
     * @param view view reference
     */
    public void setView(T view) {
        this.view = view;
    }

    /**
     * Activity Context setting function
     *
     * @param context context of the current activity
     */
    public void setContext(Context context) {
        this.context = context;
    }

    public T getView() {
        if (view == null)
            throw new IllegalStateException("view is not available");
        return view;
    }

    public Context getContext() {
        if (context == null)
            throw new IllegalStateException("context not available");
        return context;
    }

    public void addDisposable(Disposable disposable) {
        compositeSubscription.add(disposable);
    }

    public void clearDisposable() {
        compositeSubscription.clear();
    }


    /**
     * Destroy function to dispose all the used objects and cancel all the live operations
     */

    public void onDestroy() {
        this.view = null;
        this.context = null;
        compositeSubscription.clear();
    }
}
