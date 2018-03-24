package com.nikostsompanidis.tripwayapp2;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.nikostsompanidis.tripwayapp2.model.Trip;

import java.util.List;

/**
 * Created by Nikos Τsompanidis on 9/3/2018.
 */

public class DailyTripsDiffCallback extends DiffUtil.Callback {
    private final List<Trip> mOldTripsList;
    private final List<Trip> mNewTripsList;

    public DailyTripsDiffCallback(List<Trip> mOldTripsList, List<Trip> mNewTripsList) {
        this.mOldTripsList = mOldTripsList;
        this.mNewTripsList = mNewTripsList;
    }

    @Override
    public int getOldListSize() {
        return mOldTripsList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewTripsList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldTripsList.get(oldItemPosition).getId() == mNewTripsList.get(
                newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final Trip oldTrip = mOldTripsList.get(oldItemPosition);
        final Trip newTrip = mNewTripsList.get(newItemPosition);

        return oldTrip.getTitles().get(0).equals(newTrip.getTitles().get(0));
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        // Implement method if you're going to use ItemAnimator
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }

}
