package com.nikostsompanidis.tripwayapp2.activities;

import android.annotation.SuppressLint;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.ImageView;

import com.nikostsompanidis.tripwayapp2.MyApplication;
import com.nikostsompanidis.tripwayapp2.R;
import com.nikostsompanidis.tripwayapp2.fragments.BuyFragment;
import com.nikostsompanidis.tripwayapp2.fragments.DescriptionsFragment;
import com.nikostsompanidis.tripwayapp2.fragments.MapFragment;
import com.nikostsompanidis.tripwayapp2.model.Trip;
import com.squareup.picasso.Picasso;

public class TripDescriptionsActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    private static Trip trip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        setContentView(R.layout.activity_descriptions);

        trip =(Trip) getIntent().getSerializableExtra("trip");

        Bundle descriptionsBundle = new Bundle();
        if(MyApplication.systemDefaultLanguage.equals("el"))
            descriptionsBundle.putString("descriptions", trip.getDescriptions().get(1));
        else
            descriptionsBundle.putString("descriptions", trip.getDescriptions().get(0));
        descriptionsBundle.putSerializable("trip",trip);

        Bundle mapBundle = new Bundle();
        mapBundle.putDouble("firstLongitude", trip.getFirstLongitude());
        mapBundle.putDouble("firstLatitude", trip.getFirtsLatitude());
        mapBundle.putDouble("secondLongitude", trip.getSecondLongitude());
        mapBundle.putDouble("secondLatitude", trip.getSecondLatitude());

        Bundle buyBundle = new Bundle();
        buyBundle.putInt("serviceId", trip.getId());


        ImageView ivParallax = findViewById(R.id.ivParallax);
        Picasso.with(this)
                .load(trip.getImg())
                .resize(1080,720)
                .placeholder(R.drawable.default_image)
                .error(R.drawable.default_image)
                .into(ivParallax);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(MyApplication.systemDefaultLanguage.equals("el"))
            setTitle(trip.getTitles().get(1));
        else
            setTitle(trip.getTitles().get(0));



        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),descriptionsBundle,mapBundle,buyBundle);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private Bundle descriptionsBundle,mapBundle,buyBundle;

        public SectionsPagerAdapter(FragmentManager fm,Bundle descriptionsBundle,Bundle mapBundle,Bundle buyBundle) {
            super(fm);
            this.descriptionsBundle =descriptionsBundle;
            this.mapBundle = mapBundle;
            this.buyBundle = buyBundle;

        }

        @Override
        public Fragment getItem(int position) {

            switch (position){
                case 0:
                    DescriptionsFragment tab1 = new DescriptionsFragment();
                    tab1.setArguments(descriptionsBundle);
                    return  tab1;
                case 1:
                    MapFragment mapFragment = new MapFragment();
                    mapFragment.setArguments(mapBundle);
                    return mapFragment;
                case 2:
                    BuyFragment tab3 = new BuyFragment();
                    tab3.setArguments(buyBundle);
                    return  tab3;
            }
            DescriptionsFragment tab1 = new DescriptionsFragment();
            tab1.setArguments(descriptionsBundle);
            return  tab1;

        }

        @Override
        public int getCount() {
            return 3;
        }
    }

}
