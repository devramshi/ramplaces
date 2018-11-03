package com.ramapps.ramplaces.app.presenter;

import com.ramapps.ramplaces.app.common.BasePresenter;
import com.ramapps.ramplaces.app.repo.PlacesRepo;
import com.ramapps.ramplaces.app.ui.view.IDetailsView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class DetailsPresenter extends BasePresenter<IDetailsView> {

    private PlacesRepo repo;

    public void setRepo(PlacesRepo repo) {
        this.repo = repo;
    }

    public void loadImages(String id) {

        Disposable disposable = repo.loadImage(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableObserver<String>() {
                    @Override
                    public void onNext(String path) {
                        getView().updateImage(path);
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                });
        addDisposable(disposable);

    }

}
