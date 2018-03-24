package com.nikostsompanidis.tripwayapp2.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nikostsompanidis.tripwayapp2.MyApplication;
import com.nikostsompanidis.tripwayapp2.R;
import com.nikostsompanidis.tripwayapp2.model.Trip;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private final Context ctx = this;
    private ArrayList<Trip> trips = new ArrayList<>();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                   Intent intent = new Intent(ctx,MainActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_map:
                    return true;
                case R.id.navigation_about_us:
                    Intent i = new Intent(ctx,AboutActivity.class);
                    startActivity(i);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());
        Gson gson = new Gson();
        String json = appSharedPrefs.getString("MyObject", "");
        Type type = new TypeToken<List<Trip>>(){}.getType();
        trips = gson.fromJson(json, type);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

       final BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation2);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_map);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(40.6402778,22.9438889), 7.0f));
        for(Trip dt : trips){

            LatLng marker = new LatLng(dt.getSecondLongitude(),dt.getSecondLatitude());
            mMap.addMarker(new MarkerOptions().position(marker)).setTag(dt);
            mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());
            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    Trip trip = (Trip) marker.getTag();
                    Intent i = new Intent(ctx,TripDescriptionsActivity.class);
                    i.putExtra("trip", trip);
                    startActivity(i);
                }
            });
            mMap.setOnInfoWindowLongClickListener(new GoogleMap.OnInfoWindowLongClickListener() {
                @Override
                public void onInfoWindowLongClick(Marker marker) {
                    marker.hideInfoWindow();
                }
            });

        }


    }

    public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter{

        @Override
        public View getInfoWindow(Marker marker) {
            return null;
        }

        @Override
        public View getInfoContents(Marker marker) {
            Trip currentTrip = (Trip) marker.getTag();

            View view = getLayoutInflater().inflate(R.layout.info_window_item, null);

            ImageView dailyTripImageView = view.findViewById(R.id.dailyTripImageView);
            TextView dailyTripTextView =  view.findViewById(R.id.dailyTripTextView);
            TextView descriptionTextView =  view.findViewById(R.id.descriptionsTextView);


            if(currentTrip !=null){

                Picasso.with(getApplicationContext())
                        .load(currentTrip.getImg())
                        .resize(1024,780)
                        .placeholder(R.drawable.default_image)
                        .error(R.drawable.default_image)
                        .into(dailyTripImageView);
                if(MyApplication.systemDefaultLanguage.equals("el")){
                    dailyTripTextView.setText(currentTrip.getTitles().get(1));
                    descriptionTextView.setText(Jsoup.parse(currentTrip.getDescriptions().get(1)).text());
                }
                else{
                    dailyTripTextView.setText(currentTrip.getTitles().get(0));
                    descriptionTextView.setText(Jsoup.parse(currentTrip.getDescriptions().get(0)).text());
                }
            }
            return view;
        }
    }
}
