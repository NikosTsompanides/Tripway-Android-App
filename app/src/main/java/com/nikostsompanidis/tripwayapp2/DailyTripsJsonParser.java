package com.nikostsompanidis.tripwayapp2;

import android.util.Log;

import com.nikostsompanidis.tripwayapp2.adapters.DailyTripRecyclerViewAdapter;
import com.nikostsompanidis.tripwayapp2.model.Trip;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Nikos Τsompanidis on 6/3/2018.
 */

public  class DailyTripsJsonParser{
    private DailyTripRecyclerViewAdapter dailyTripRecyclerViewAdapter;
    private static final String API_KEY_VALUE = "E4B7ADDE-547A-5639-2312-E3E9FB67C5EE";
    private static final String RESULT_OBJECT_DATA = "data";
    private static final String TRIP_IMG_KEY = "service_image";
    private static final String TRIP_PRICE_KEY = "service_min_price";
    private static final String TRIP_DEPOSIT_KEY = "service_deposit";
    private static final String TRIP_DISCOUNTED_PRICE_KEY = "service_min_discounted_price";
    private static final String TRIP_TITLE_KEY = "service_title";
    private static final String TRIP_DESCRIPTION_KEY = "service_description";
    private static final String TRIP_PUBLISHED_FROM_KEY = "service_publish_from";
    private static final String TRIP_PUBLISHED_TO_KEY = "service_publish_to";
    private static final String TRIP_FIRST_LONGITUDE_KEY = "service_longitude";
    private static final String TRIP_SECOND_LONGITUDE_KEY = "service_second_longitude";
    private static final String TRIP_FIRST_LATITUDE_KEY = "service_latitude";
    private static final String TRIP_SECOND_LATITUDE_KEY = "service_second_latitude";
    private static final String TRIP_ID_KEY = "service_id";
    private static final String LOG_TAG = DailyTripsJsonParser.class.getSimpleName();

    private  ArrayList<Trip> dataList = new ArrayList<>();

    public DailyTripsJsonParser() {
    }

    public  ArrayList<Trip> getTripsDataFromJson(String placesJsonStr) throws JSONException {
        try {

            JSONObject dailyTripsJson = new JSONObject(placesJsonStr);
            JSONObject data = dailyTripsJson.optJSONObject(RESULT_OBJECT_DATA);

            for (int i = 0; i<data.names().length(); i++) {
                String img,price,deposit,discountedPrice;
                ArrayList<String> titles = new ArrayList<>();
                ArrayList<String> descriptions = new ArrayList<>();
                String publishFrom,publishTo;
                double firstLongitude,firstLatitude,secondLongitude,secondLatitude;
                int id;

                JSONObject dailyTrip = data.optJSONObject(data.names().getString(i));
                img="https://tripway.travelotopos.com/files/tripway/resources/";
                id = dailyTrip.optInt(TRIP_ID_KEY);
                img += dailyTrip.optString(TRIP_IMG_KEY);
                price = dailyTrip.optString(TRIP_PRICE_KEY);
                deposit = dailyTrip.optString(TRIP_DEPOSIT_KEY);
                discountedPrice = dailyTrip.optString(TRIP_DISCOUNTED_PRICE_KEY);
                titles.add(dailyTrip.optJSONObject(TRIP_TITLE_KEY).optString("english"));
                titles.add(dailyTrip.optJSONObject(TRIP_TITLE_KEY).optString("greek"));
                descriptions.add(dailyTrip.optJSONObject(TRIP_DESCRIPTION_KEY).optString("english"));
                descriptions.add(dailyTrip.optJSONObject(TRIP_DESCRIPTION_KEY).optString("greek"));
                publishFrom = dailyTrip.optString(TRIP_PUBLISHED_FROM_KEY);
                publishTo = dailyTrip.optString(TRIP_PUBLISHED_TO_KEY);
                firstLongitude = dailyTrip.optDouble(TRIP_FIRST_LONGITUDE_KEY,0);
                firstLatitude = dailyTrip.optDouble(TRIP_FIRST_LATITUDE_KEY,0);
                secondLongitude = dailyTrip.optDouble(TRIP_SECOND_LONGITUDE_KEY,0);
                secondLatitude = dailyTrip.optDouble(TRIP_SECOND_LATITUDE_KEY,0);

                Trip trip1 = new Trip(img, price, deposit, discountedPrice, publishFrom, publishTo, firstLongitude, firstLatitude, secondLongitude, secondLatitude,titles,descriptions,id);
                Log.d("latlng",firstLatitude+" "+firstLongitude+" "+secondLatitude+" "+secondLongitude);
                dataList.add(trip1);
            }
            Log.d(LOG_TAG, "Fetch Places Task Complete. " + dataList.size() + " places Inserted");

            return dataList;
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }
        return dataList;
    }
}
