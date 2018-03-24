package com.nikostsompanidis.tripwayapp2.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.gson.Gson;
import com.nikostsompanidis.tripwayapp2.DailyTripsJsonParser;
import com.nikostsompanidis.tripwayapp2.model.Trip;
import com.nikostsompanidis.tripwayapp2.R;
import com.nikostsompanidis.tripwayapp2.adapters.DailyTripRecyclerViewAdapter;
import com.nikostsompanidis.tripwayapp2.rest.ApiClient;
import com.nikostsompanidis.tripwayapp2.rest.ApiInterface;
import org.json.JSONException;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class TripsFragment extends Fragment {

    private OnDailyTripSelectedListener mListener;
    private ArrayList<Trip> trips = new ArrayList<>();
    private ArrayList<Integer> servicesIds= new ArrayList<>();
    private final String API_KEY = "E4B7ADDE-547A-5639-2312-E3E9FB67C5EE";
    private  RecyclerView recyclerView;
    private DailyTripRecyclerViewAdapter adapter;
    private SwipeRefreshLayout swipeContainer;
    private ApiInterface apiService;
    private SharedPreferences.Editor prefsEditor;
    private SharedPreferences appSharedPrefs;
    private Gson gson;
    private View view;



    public TripsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_item_list, container, false);
        if (container != null) {
            container.removeAllViews();
        }

        recyclerView = view.findViewById(R.id.dailyTripsRecyclerView);

        apiService= ApiClient.getClient().create(ApiInterface.class);

        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(getActivity());
        progressDoalog.setMessage(getString(R.string.loading_message));
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();

        Call<ResponseBody> call = apiService.getServices(API_KEY);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    DailyTripsJsonParser jsonParser = new DailyTripsJsonParser();

                    ArrayList<Trip> currentTrips1 = jsonParser.getTripsDataFromJson(response.body().string());
                    for(Trip dt : currentTrips1)
                        servicesIds.add(dt.getId());
                    String json = gson.toJson(currentTrips1);
                    prefsEditor.putString("MyObject", json);
                    prefsEditor.apply();
                    adapter.addAll(currentTrips1);
                    adapter.notifyDataSetChanged();
                    progressDoalog.dismiss();
                    recyclerView.setVisibility(View.VISIBLE);


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDoalog.dismiss();
                new AlertDialog.Builder(getActivity())
                        .setTitle(getString(R.string.fail_response))
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setMessage(getResources().getString(R.string.fail_response_description))
                        .show();

            }
        });


        appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        prefsEditor = appSharedPrefs.edit();
        gson = new Gson();

        adapter = new DailyTripRecyclerViewAdapter(trips, mListener, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);


        swipeContainer= view.findViewById(R.id.swipeRefreshLayout);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.clear();
                fetchDailyTripsAsync();
            }
        });


        return view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (OnDailyTripSelectedListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void fetchDailyTripsAsync() {

        Call<ResponseBody> refreshCall = apiService.getServices(API_KEY);
        refreshCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    DailyTripsJsonParser jsonParser = new DailyTripsJsonParser();
                    ArrayList<Trip> currentTrips = jsonParser.getTripsDataFromJson(response.body().string());
                    for(Trip dt : currentTrips)
                        servicesIds.add(dt.getId());
                    String json = gson.toJson(currentTrips);
                    prefsEditor.putString("MyObject", json);
                    prefsEditor.apply();
                    adapter.updateDailyTripsListItems(currentTrips);
                    adapter.notifyDataSetChanged();
                    swipeContainer.setRefreshing(false);

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                //recyclerView.setVisibility(View.INVISIBLE);
                swipeContainer.setRefreshing(false);
                new AlertDialog.Builder(getActivity())
                        .setTitle(getString(R.string.fail_response))
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setMessage(getResources().getString(R.string.fail_response_description))
                        .show();


            }
        });

    }


    public void onListItemClick(DailyTripRecyclerViewAdapter.ViewHolder v, int position) {
        // Send the event to the host activity
       // mListener.OnDailyTripItemSelected(v.mItem);
    }

    public interface OnDailyTripSelectedListener {
        void OnDailyTripItemSelected(Trip item);
    }

}
