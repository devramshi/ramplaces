package com.ramapps.ramplaces.app.data;

import com.ramapps.ramplaces.app.data.response.Venue;

public class SelectedPlace {
    private static volatile SelectedPlace instance;
    private static volatile Venue venue;

    public static SelectedPlace getInstance() {
        SelectedPlace service = instance;
        if (service == null) {
            synchronized (SelectedPlace.class) {
                service = instance;
                if (service == null) {
                    service = new SelectedPlace();
                    instance = service;
                }
            }
        }
        return service;
    }

    public Venue getSelectedPlace() {
        return venue;
    }

    public void saveSelectedPlace(Venue venue) {
        this.venue = venue;
    }
}
