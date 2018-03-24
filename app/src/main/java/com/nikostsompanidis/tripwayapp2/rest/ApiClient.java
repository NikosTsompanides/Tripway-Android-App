package com.nikostsompanidis.tripwayapp2.rest;

import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Nikos Τsompanidis on 9/3/2018.
 */

public class ApiClient {
    public static final String BASE_URL = "https://api-tripway.travelotopos.com/";
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
