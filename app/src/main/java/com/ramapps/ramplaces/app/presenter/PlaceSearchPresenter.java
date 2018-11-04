package com.ramapps.ramplaces.app.presenter;

import com.ramapps.ramplaces.app.common.BasePresenter;
import com.ramapps.ramplaces.app.data.response.Venue;
import com.ramapps.ramplaces.app.repo.PlacesRepo;
import com.ramapps.ramplaces.app.ui.view.IHomeView;
import com.ramapps.ramplaces.common.Constants;
import com.ramapps.ramplaces.common.RMException;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class PlaceSearchPresenter extends BasePresenter<IHomeView> {

    private PlacesRepo repo;

    public void setRepo(PlacesRepo rep) {
        this.repo = rep;
    }

    public void findNearestPlaces(Double lat, Double longi) {
        getView().showProgress();
        Disposable disposable = repo.findNearest(lat, longi)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableObserver<List<Venue>>() {
                    @Override
                    public void onNext(List<Venue> venues) {
                        getView().hideProgress();
                        getView().showPlaces(venues);
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        getView().hideProgress();
                        if (e instanceof RMException)
                            getView().showEmpty(e.getLocalizedMessage());
                        else
                            getView().showEmpty(Constants.ERR_SOMETHING);
                    }
                });
        addDisposable(disposable);

    }
}
