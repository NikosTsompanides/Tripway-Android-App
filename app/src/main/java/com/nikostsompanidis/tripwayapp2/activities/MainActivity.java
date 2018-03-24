package com.nikostsompanidis.tripwayapp2.activities;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.nikostsompanidis.tripwayapp2.BottomNavigationBehavior;
import com.nikostsompanidis.tripwayapp2.ConnectivityReceiver;
import com.nikostsompanidis.tripwayapp2.MyApplication;
import com.nikostsompanidis.tripwayapp2.R;
import com.nikostsompanidis.tripwayapp2.fragments.TripsFragment;
import com.nikostsompanidis.tripwayapp2.model.Trip;

public class MainActivity extends AppCompatActivity implements TripsFragment.OnDailyTripSelectedListener{
    private final Context ctx=this;
    private BottomNavigationView navigationView ;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private LottieAnimationView noIneternetConnectionAnimationView;
    private FrameLayout dailyTripsFrameLayout;
    private TextView noInternetAccesTextView;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_map:
                    Intent i = new Intent(ctx, MapsActivity.class);
                    startActivity(i);
                    return true;
                case R.id.navigation_about_us:
                    Intent in = new Intent(ctx, AboutActivity.class);
                    startActivity(in);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onResume(){
        super.onResume();
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);

        ConnectivityReceiver connectivityReceiver = new ConnectivityReceiver();
        registerReceiver(connectivityReceiver, intentFilter);

        // register connection status listener
        MyApplication.getInstance().setConnectivityListener(new ConnectivityReceiver.ConnectivityReceiverListener() {
            @Override
            public void onNetworkConnectionChanged(boolean isConnected) {
                showInternetConnectionError(isConnected);
            }
        });
    }

    // Method to manually check connection status
    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
    }

    private void showInternetConnectionError(boolean isConnected) {

        if(isConnected){
            dailyTripsFrameLayout.setVisibility(View.VISIBLE);
            noIneternetConnectionAnimationView.setVisibility(View.INVISIBLE);
            noInternetAccesTextView.setVisibility(View.INVISIBLE);
        }
        else{
            dailyTripsFrameLayout.setVisibility(View.INVISIBLE);
            noIneternetConnectionAnimationView.setVisibility(View.VISIBLE);
            noInternetAccesTextView.setVisibility(View.VISIBLE);
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean previouslyStarted = prefs.getBoolean(getString(R.string.pref_previously_started), false);
        if(!previouslyStarted) {
            SharedPreferences.Editor edit = prefs.edit();
            edit.putBoolean(getString(R.string.pref_previously_started), Boolean.TRUE);
            edit.commit();
            showHelp();
        }
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        setContentView(R.layout.activity_main);
        checkConnection();

        noIneternetConnectionAnimationView = findViewById(R.id.noIneternetConnectionAnimationView);
        dailyTripsFrameLayout = findViewById(R.id.dailyTripsFragment);
        noInternetAccesTextView = findViewById(R.id.noInternetAccesTextView);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        TripsFragment fragment = new TripsFragment();
        fragmentTransaction.add(R.id.dailyTripsFragment, fragment);
        fragmentTransaction.commit();

        navigationView = (BottomNavigationView) findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());


    }

    @Override
    public void OnDailyTripItemSelected(Trip item) {

        Intent i = new Intent(ctx,TripDescriptionsActivity.class);
        i.putExtra("dailyTrip",item);
        startActivity(i);
    }

    public void showHelp(){

        Intent i = new Intent(this,MainIntroActivity.class);
        startActivity(i);
    }

}
