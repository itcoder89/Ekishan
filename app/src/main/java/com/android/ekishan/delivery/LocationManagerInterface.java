package com.android.ekishan.delivery;

import android.location.Location;

/**
 * Created by ${} on 6/16/2020.
 */
public interface LocationManagerInterface {
    String TAG = LocationManagerInterface.class.getSimpleName();

    void locationFetched(Location mLocation, Location oldLocation, String time, String locationProvider);

}
