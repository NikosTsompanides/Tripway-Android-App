package com.nikostsompanidis.tripwayapp2.rest;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Nikos Τsompanidis on 9/3/2018.
 */

public interface ApiInterface {

    @GET("services/services")
    Call<ResponseBody> getServices(@Header("apikey") String apiKey);

    @FormUrlEncoded
    @POST("events/events")
    Call<ResponseBody> getEventsFromService(@Header("apikey") String apiKey, @FieldMap Map<String, String> fieldsMap);
}
