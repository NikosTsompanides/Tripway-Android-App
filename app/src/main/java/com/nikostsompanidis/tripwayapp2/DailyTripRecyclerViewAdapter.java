package com.nikostsompanidis.tripwayapp2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nikostsompanidis.tripwayapp2.dummy.DummyContent.DummyItem;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link DailyTripsFragment.OnDailyTripSelectedListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class DailyTripRecyclerViewAdapter extends RecyclerView.Adapter<DailyTripRecyclerViewAdapter.ViewHolder> {

    private final List<DailyTrip> mValues;
    private final DailyTripsFragment.OnDailyTripSelectedListener mListener;
    private Context ctx;

    public DailyTripRecyclerViewAdapter(List<DailyTrip> items, DailyTripsFragment.OnDailyTripSelectedListener listener, Context ctx) {
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
       DailyTrip dailyTrip = mValues.get(position);
        holder.mItem = mValues.get(position);
        holder.tripTitle.setText(dailyTrip.getTitle());
        holder.tripDate.setText(dailyTrip.getDate());
        holder.tripPrice.setText(dailyTrip.getPrice());
        holder.description.setText(dailyTrip.getDescription());
        Picasso.with(ctx)
                .load(R.drawable.test_photo)
                .resize(1024,780)
                .into(holder.imageView);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.OnDayliTripItemSelected(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tripTitle,tripDate,tripPrice,description;
        public final ImageView imageView;
        public DailyTrip mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tripTitle = (TextView) view.findViewById(R.id.dailyTripTextView);
            tripDate = (TextView) view.findViewById(R.id.tripDateTextView);
            tripPrice = (TextView) view.findViewById(R.id.tripPriceTextView);
            description =(TextView) view.findViewById(R.id.descriptionTextView);
            imageView = (ImageView) view.findViewById(R.id.dailyTripImageView);
        }
    }
}
