package com.ramapps.ramplaces.app.ui.view;

import android.location.Location;

import com.ramapps.ramplaces.app.data.response.Venue;

import java.util.List;

public interface IHomeView extends BaseView {

    void updateLocation(Location location);

    void showPlaces(List<Venue> places);

    void showEmpty(String message);

    void onRetry();

    void onVenue(Venue venue);
}
