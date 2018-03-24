package com.nikostsompanidis.tripwayapp2.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nikostsompanidis.tripwayapp2.DailyTripsDiffCallback;
import com.nikostsompanidis.tripwayapp2.MyApplication;
import com.nikostsompanidis.tripwayapp2.activities.TripDescriptionsActivity;
import com.nikostsompanidis.tripwayapp2.model.Trip;
import com.nikostsompanidis.tripwayapp2.fragments.TripsFragment;
import com.nikostsompanidis.tripwayapp2.R;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.List;

public class DailyTripRecyclerViewAdapter extends RecyclerView.Adapter<DailyTripRecyclerViewAdapter.ViewHolder> {

    private final List<Trip> mValues;
    private final TripsFragment.OnDailyTripSelectedListener mListener;
    private Context ctx;

    public DailyTripRecyclerViewAdapter(List<Trip> items, TripsFragment.OnDailyTripSelectedListener listener, Context ctx) {
        mValues = items;
        mListener = listener;
        this.ctx = ctx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
       final Trip trip = mValues.get(position);
        holder.mItem = mValues.get(position);
        if(MyApplication.systemDefaultLanguage.equals("el")){
            holder.tripTitle.setText(trip.getTitles().get(1));
            String description = trip.getDescriptions().get(1);
            holder.description.setText(Jsoup.parse(description).text());
            holder.description.setTextSize(12);
        } else {
            holder.tripTitle.setText(trip.getTitles().get(0));
            String description = trip.getDescriptions().get(0);
            holder.description.setText(Jsoup.parse(description).text());
            holder.description.setTextSize(12);
        }
        Double price = Double.parseDouble(trip.getPrice()) ;
        holder.tripPrice.setText(" "+price+"0 € / "+ctx.getResources().getString(R.string.person));

        Picasso.with(ctx)
                .load(trip.getImg())
                .resize(1024,780)
                .placeholder(R.drawable.default_image)
                .error(R.drawable.default_image)
                .into(holder.imageView);

        holder.readMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ctx,TripDescriptionsActivity.class);
                i.putExtra("trip", trip);
                view.getContext().startActivity(i);
            }
        });
        holder.shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, view.getContext().getResources().getString(R.string.share_text)+" : https://tripway.travelotopos.com/s/"+trip.getId()+"/1");
                sendIntent.setType("text/plain");
                view.getContext().startActivity(Intent.createChooser(sendIntent, view.getContext().getResources().getText(R.string.app_name)));
            }
        });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void updateDailyTripsListItems(List<Trip> trips) {
        final DailyTripsDiffCallback diffCallback = new DailyTripsDiffCallback(this.mValues, trips);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        this.clear();
        this.mValues.addAll(trips);
        diffResult.dispatchUpdatesTo(this);
    }

    public void clear() {
        mValues.clear();
        this.notifyDataSetChanged();
    }

    public void addAll(ArrayList<Trip> trip) {
        mValues.addAll(trip);
        this.notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tripTitle,tripPrice,description;
        public final ImageView imageView;
        public final Button readMoreButton;
        public Trip mItem;
        public final ImageButton shareButton;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            tripTitle = (TextView) view.findViewById(R.id.dailyTripTextView);
            readMoreButton = view.findViewById(R.id.readMoreButton);
            tripPrice = (TextView) view.findViewById(R.id.trippriceTextView);
            description =(TextView) view.findViewById(R.id.descriptionTextView);
            imageView = (ImageView) view.findViewById(R.id.dailyTripImageView);
            shareButton = view.findViewById(R.id.imageButton);
        }
    }
}
