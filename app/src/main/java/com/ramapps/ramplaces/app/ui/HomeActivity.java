package com.ramapps.ramplaces.app.ui;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintSet;
import android.support.transition.TransitionManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.dynamitechetan.flowinggradient.FlowingGradientClass;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.ramapps.ramplaces.R;
import com.ramapps.ramplaces.app.common.BaseActivity;
import com.ramapps.ramplaces.app.common.adapter.PlaceListAdapter;
import com.ramapps.ramplaces.app.data.SelectedPlace;
import com.ramapps.ramplaces.app.data.response.Venue;
import com.ramapps.ramplaces.app.presenter.PlaceSearchPresenter;
import com.ramapps.ramplaces.app.ui.view.IHomeView;
import com.ramapps.ramplaces.common.Callback;
import com.ramapps.ramplaces.common.Constants;
import com.ramapps.ramplaces.common.DialogUtils;
import com.ramapps.ramplaces.common.Navigator;
import com.ramapps.ramplaces.common.PermissionService;
import com.ramapps.ramplaces.databinding.ActivityHomeBinding;

import java.util.List;

/**
 * Created by Ramsheed on 11/03/2018.
 * Home Activity
 */
public class HomeActivity extends BaseActivity implements IHomeView, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private LocationManager locationManager;
    private ActivityHomeBinding binding;
    private GoogleApiClient googleApiClient;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 189;
    private LocationRequest locationRequest;
    private Location location;
    private static final long UPDATE_INTERVAL = 5000, FASTEST_INTERVAL = 5000;
    private PlaceSearchPresenter presenter;
    private PlaceListAdapter adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        binding.setShowProgress(true);
        binding.setShowLocation(false);
        presenter = new PlaceSearchPresenter();
        presenter.setContext(this);
        presenter.setView(this);
        setPresenter(presenter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.places.setLayoutManager(layoutManager);
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_anim_slide_bottom);
        binding.places.setLayoutAnimation(animation);
        adapter = new PlaceListAdapter(this);
        binding.places.setAdapter(adapter);
        boolean enabled = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (!enabled) {
            DialogUtils.showConfirm(this, getString(R.string.msg_enable_location), new Callback<Boolean>() {
                @Override
                public void execute(Boolean response) {
                    if (response) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    } else {
                        HomeActivity.this.finish();
                    }
                }
            });
        }
        // we build google api client
        googleApiClient = new GoogleApiClient.Builder(this).
                addApi(LocationServices.API).
                addConnectionCallbacks(this).
                addOnConnectionFailedListener(this).build();

        FlowingGradientClass grad = new FlowingGradientClass();
        grad.setBackgroundResource(R.drawable.progress_transition)
                .onRelativeLayout(binding.flowingBack)
                .setTransitionDuration(4000)
                .start();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!checkPlayServices()) {
            binding.txtMessage.setText("You need to install Google Play Services to use the App properly");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // stop location updates
        if (googleApiClient != null && googleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
            googleApiClient.disconnect();
        }
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST);
            } else {
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d("Places", "Connected");
        if (!PermissionService.getInstance().hasLocationPermission(this)) {
            PermissionService.getInstance().reqLocPermission(this);
            return;
        }
        location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        updateLocation(location);
        startLocationUpdates();
    }

    private void startLocationUpdates() {
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);
        if (PermissionService.getInstance().hasLocationPermission(this)) {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
        updateLocation(location);
    }

    @Override
    public void updateLocation(Location location) {
        if (location != null) {
            binding.setShowProgress(false);
            binding.setShowLocation(true);
            binding.txtLat.setText(String.valueOf(location.getLatitude()));
            binding.txtLong.setText(String.valueOf(location.getLongitude()));
            presenter.findNearestPlaces(location.getLatitude(), location.getLongitude());
            searchPlaces();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Constants.LOC_PERMISSION_REQ:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (googleApiClient != null) {
                        googleApiClient.connect();
                    }
                    Log.d("Places", "reques granted");
                } else {
                    DialogUtils.showOKWithAction(HomeActivity.this, "These permissions are mandatory to get your location. You need to allow them.", new Callback<Boolean>() {
                        @Override
                        public void execute(Boolean response) {
                            PermissionService.getInstance().reqLocPermission(HomeActivity.this);
                        }
                    });
                    Log.d("Places", "reques rejected");
                }
                break;
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void searchPlaces() {
        applyStyles();
    }

    private void applyStyles() {
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(binding.rootConstraint);
        binding.lblLat.setTextColor(ContextCompat.getColor(this, R.color.white_1));
        binding.lblLong.setTextColor(ContextCompat.getColor(this, R.color.white_1));
        binding.txtLat.setTextColor(ContextCompat.getColor(this, R.color.white_1));
        binding.txtLong.setTextColor(ContextCompat.getColor(this, R.color.white_1));
        binding.appName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 38);
        constraintSet.connect(R.id.bottomCard, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0);
        constraintSet.connect(R.id.bottomCard, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 0);
        constraintSet.connect(R.id.bottomCard, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0);
        constraintSet.connect(R.id.bottomCard, ConstraintSet.BOTTOM, R.id.topGuideline, ConstraintSet.BOTTOM);
        constraintSet.applyTo(binding.rootConstraint);
        TransitionManager.beginDelayedTransition(binding.rootConstraint);
        binding.bottomCard.setCardBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
    }

    @Override
    public void showPlaces(List<Venue> places) {
        adapter.setItems(places);
    }

    @Override
    public void showEmpty(String message) {
        binding.setShowGradient(false);
        binding.setShowEmpty(true);
        binding.txtMessage.setText(message);
    }

    @Override
    public void onRetry() {
        if (location != null)
            presenter.findNearestPlaces(location.getLatitude(), location.getLongitude());
    }

    @Override
    public void onVenue(Venue venue) {
        if (venue != null) {
            SelectedPlace.getInstance().saveSelectedPlace(venue);
            Navigator.getInstance().navigate(this, DetailsActivity.class, null, false, false);
        }
    }
}
