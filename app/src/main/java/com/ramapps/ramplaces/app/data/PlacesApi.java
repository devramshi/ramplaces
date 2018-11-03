package com.ramapps.ramplaces.app.data;

import com.ramapps.ramplaces.app.data.response.PhotoResponse;
import com.ramapps.ramplaces.app.data.response.SearchResponse;
import com.ramapps.ramplaces.common.Constants;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Ramsheed on 11/03/2018.
 * Places REST API Class
 */

public interface PlacesApi {

    @POST("/v2/venues/search")
    @FormUrlEncoded
    Observable<SearchResponse> searchPlaces(@FieldMap Map<String, String> params);

    @GET("/v2/venues/{venuid}/photos")
    Observable<PhotoResponse> loadImage(@Path("venuid") String venueId,
                                        @Query(Constants.KEY_CLIENT_ID) String clientId,
                                        @Query(Constants.KEY_CLIENT_SECRET) String clientSecret,
                                        @Query(Constants.KEY_VERSION) String version);

}


