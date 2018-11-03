package com.ramapps.ramplaces.app.repo;

import com.ramapps.ramplaces.app.data.response.Venue;

import java.util.List;

import io.reactivex.Observable;

public interface PlacesRepo {

    public Observable<List<Venue>> findNearest(final Double lat, final Double longg);

    public Observable<String> loadImage(final String id);

}
