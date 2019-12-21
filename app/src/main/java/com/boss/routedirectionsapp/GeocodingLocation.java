package com.boss.routedirectionsapp;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GeocodingLocation {

    private static final String TAG = "GeocodingLocation";

    public static String getAddressFromLocation(final String sd,
                                                final String locationAddress,
                                                final Context context, final Handler handler) {
        final String[] result = {null};
        Thread thread = new Thread() {
            @Override
            public void run() {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                try {
                    List addressList = geocoder.getFromLocationName(locationAddress, 1);
                    if (addressList != null && addressList.size() > 0) {
                        Address address = (Address) addressList.get(0);
                        StringBuilder sb = new StringBuilder();
                        sb.append(address.getLatitude()).append("\n");
                        sb.append(address.getLongitude()).append("\n");
                        result[0] = sb.toString();
                    }
                } catch (IOException e) {
                    Log.e(TAG, "Unable to connect to Geocoder", e);
                } finally {
                    Message message = Message.obtain();
                    message.setTarget(handler);
                    if (result[0] != null) {
                        message.what = 1;
                        Bundle bundle = new Bundle();
                        bundle.putString("sd", sd);
                        bundle.putString("address", result[0]);
                        message.setData(bundle);
                    } else {
                        message.what = 1;
                        Bundle bundle = new Bundle();
                        result[0] = "Unable to get Latitude and Longitude for this address location.";
                        bundle.putString("address", result[0]);
                        message.setData(bundle);
                    }
                    message.sendToTarget();
                }
            }
        };
        thread.start();
        return result[0];
    }
}