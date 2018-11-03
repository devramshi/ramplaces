package com.ramapps.ramplaces.app.repo;

import com.ramapps.ramplaces.BuildConfig;
import com.ramapps.ramplaces.app.data.response.PhotoResponse;
import com.ramapps.ramplaces.app.data.response.SearchResponse;
import com.ramapps.ramplaces.app.data.response.Venue;
import com.ramapps.ramplaces.common.Constants;
import com.ramapps.ramplaces.common.FunctionUtils;
import com.ramapps.ramplaces.common.RMException;
import com.ramapps.ramplaces.services.RestService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class PlacesRepoImpl implements PlacesRepo {

    public Observable<List<Venue>> findNearest(final Double lat, final Double longg) {

        return Observable.create(new ObservableOnSubscribe<List<Venue>>() {
            @Override
            public void subscribe(final ObservableEmitter<List<Venue>> emitter) throws Exception {

                Map<String, String> params = new HashMap<>();
                params.put(Constants.KEY_CLIENT_ID, BuildConfig.CL_ID);
                params.put(Constants.KEY_CLIENT_SECRET, BuildConfig.CL_SECRET);
                params.put(Constants.KEY_VERSION, FunctionUtils.getDateTime());
                params.put(Constants.KEY_LOCATION, String.valueOf(lat) + "," + String.valueOf(longg));
                RestService.getPlacesAPI().searchPlaces(params)
                        .observeOn(Schedulers.io())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new DisposableObserver<SearchResponse>() {
                            @Override
                            public void onNext(SearchResponse searchResponse) {
                                if (searchResponse != null) {
                                    if (searchResponse.getResponse() != null) {
                                        if (searchResponse.getResponse().getVenues() != null && !searchResponse.getResponse().getVenues().isEmpty()) {
                                            emitter.onNext(searchResponse.getResponse().getVenues());
                                        } else {
                                            emitter.onNext(new ArrayList<Venue>());
                                        }

                                    } else if (searchResponse.getMeta() != null) {
                                        emitter.onError(new RMException(searchResponse.getMeta().getErrorType()));
                                    } else {
                                        emitter.onError(new RMException(Constants.ERR_SOMETHING));
                                    }

                                } else {
                                    emitter.onError(new RMException(Constants.ERR_SOMETHING));
                                }

                            }

                            @Override
                            public void onError(Throwable e) {
                                emitter.onError(e);
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }
        });
    }

    public Observable<String> loadImage(final String id) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(final ObservableEmitter<String> emitter) throws Exception {
                RestService.getPlacesAPI().loadImage(id, BuildConfig.CL_ID, BuildConfig.CL_SECRET, FunctionUtils.getDateTime())
                        .observeOn(Schedulers.io())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new DisposableObserver<PhotoResponse>() {
                            @Override
                            public void onNext(PhotoResponse searchResponse) {
                                if (searchResponse != null) {
                                    if (searchResponse.getResponse() != null) {
                                        if (searchResponse.getResponse().getPhotos() != null && searchResponse.getResponse().getPhotos().getItems() != null && !searchResponse.getResponse().getPhotos().getItems().isEmpty()) {
                                            StringBuilder builder = new StringBuilder();
                                            builder.append(searchResponse.getResponse().getPhotos().getItems().get(0).getPrefix());
                                            builder.append(Constants.IMAGE_SIZE).append(searchResponse.getResponse().getPhotos().getItems().get(0).getSuffix());
                                            emitter.onNext(builder.toString());
                                        } else {
                                            emitter.onNext(Constants.EMPTY);
                                        }

                                    } else if (searchResponse.getMeta() != null) {
                                        emitter.onError(new RMException(searchResponse.getMeta().getErrorType()));
                                    } else {
                                        emitter.onError(new RMException(Constants.ERR_SOMETHING));
                                    }

                                } else {
                                    emitter.onError(new RMException(Constants.ERR_SOMETHING));
                                }

                            }

                            @Override
                            public void onError(Throwable e) {
                                emitter.onError(e);
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }
        });
    }

}
