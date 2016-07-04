package com.benalbritton.metrolinkapp2;


import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;

import layout.TimerFragment;

public class UserLocationInfo extends Service implements LocationListener {

    private Context mcontext;
    public double[] coordinates;


    public  UserLocationInfo(Context mcontext) {
        this.mcontext = mcontext;
        //coordinates = getUserLocation();
    }

    /////////////
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    protected LocationManager locationManager;

    public double[] getUserLocation() {
        try {
            locationManager = (LocationManager) mcontext.getSystemService(LOCATION_SERVICE);

            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                //Do what you need if enabled...
            }else{
                //Do what you need if not enabled...
            }

            Location gpsLocation;
            gpsLocation = getLocation(locationManager.GPS_PROVIDER);
            if (gpsLocation == null) {
                showSettingsAlert("GPS");
            }
            if (gpsLocation != null) {
                double latitude = gpsLocation.getLatitude();
                double longitude = gpsLocation.getLongitude();
                //double[] userLocation = {latitude, longitude};
                double[] userLocation = {0, 0};
                userLocation[0] = latitude;
                userLocation[1] = longitude;
                return userLocation;
            }
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
        return null;
    }


    private Location getLocation(String provider) {
        Location location;
        try {
            if (locationManager.isProviderEnabled(provider)) {
                locationManager.requestLocationUpdates(provider, 0, 0, this);
                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(provider);
                    try {
                        locationManager.removeUpdates(this);
                    } catch (SecurityException ex) {
                        ex.printStackTrace();
                    }
                    return location;
                }
            }

        } catch (SecurityException ex) {
            ex.printStackTrace();
        }
        try {
            locationManager.removeUpdates(this);
        } catch (SecurityException ex) {
            ex.printStackTrace();
        }
        return null;
    }



    public void showSettingsAlert(String provider) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        alertDialog.setTitle(provider + " SETTINGS");

        alertDialog.setMessage(provider + " is not enabled! Want to go to settings menu?");

        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        getApplicationContext().startActivity(intent);
                    }
                });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();
    }


    @Override
    public void onLocationChanged(Location location) {
    }
    @Override
    public void onProviderDisabled(String provider) {
    }
    @Override
    public void onProviderEnabled(String provider) {
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
}

