package com.avinash.rider_gps_sos;

import android.location.Location;

public class CollectLocation extends Location {


    public CollectLocation(Location l) {
        super(l);
    }

    @Override
    public  float getSpeed() {
        return super.getSpeed()*3.6f;
    }

    @Override
    public double getAltitude() {
        return super.getAltitude();
    }

    @Override
    public float getAccuracy() {
        return super.getAccuracy();
    }

    @Override
    public float distanceTo(Location dest) {
        return super.distanceTo(dest);
    }
}
