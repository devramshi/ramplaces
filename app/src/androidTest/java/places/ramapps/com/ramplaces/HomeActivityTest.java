package places.ramapps.com.ramplaces;


import android.app.Application;
import android.content.Context;
import android.location.LocationManager;
import android.support.test.runner.AndroidJUnit4;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.ramapps.ramplaces.app.common.PlacesApplication;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertFalse;

@RunWith(AndroidJUnit4.class)
public class HomeActivityTest {

    private LocationManager locationManager;
    private GoogleApiAvailability apiAvailability;

    @Before
    public void setUp() {
        locationManager =
                (LocationManager)
                        ((Application) PlacesApplication.getContext())
                                .getSystemService(Context.LOCATION_SERVICE);

        apiAvailability = GoogleApiAvailability.getInstance();
    }

    @Test
    public void isLocationEnabled() {
        Boolean enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        assert(enabled);
    }

    @Test
    public void isPlayServiceAvailable() {
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(PlacesApplication.getContext());
        assert(resultCode != ConnectionResult.SUCCESS);
    }

}
