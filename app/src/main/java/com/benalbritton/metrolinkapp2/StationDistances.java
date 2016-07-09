package com.benalbritton.metrolinkapp2;


import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class StationDistances {

    private ArrayList<Station> stationList;
    public DatabaseAccess databaseAccess;
    private UserLocationInfo userLocationInfo;
    private double[] userCoordinates;

    /////
    public Context mcontext;


    protected StationDistances(Context context) {

        // Below 2 can get context from mcontext



        mcontext = context;
    }


    // NOTE : This whole function should be obsolete
    public String closestStation() {
        String stationID = "";
        double stationDist = 1000000;

        // Get list of stations from StationListing
        //StationListing stationListing = new StationListing();
        //stationList = stationListing.getStationsInfo(mcontext);

        stationList = getStationsInfo();

        // Get users location
        userCoordinates = userLocationInfo.getUserLocation();

        // Get and store distance from user to each station  <- DONE!
        /* Currently returns id of closest station.  May modify
           to only get distances to use later.
           May want to rename function to reflect that change when done.
         */
        for(Station station : stationList) {
            double distance = distToStation(userCoordinates[0], userCoordinates[1],
                    station.getLat(), station.getLon());


            if (distance < stationDist) {
                stationDist = distance;
                stationID = station.getId();
            }
        }

        return stationID;
    }


    public ArrayList<Station> getStationsInfo() {
        ArrayList<Station> allStations = new ArrayList<>();
        double statDist;

        databaseAccess = DatabaseAccess.getDbInstance(mcontext);
        userLocationInfo = new UserLocationInfo(mcontext);

        databaseAccess.open();
        Cursor c = databaseAccess.getStations();
        if(c != null) {
            c.moveToFirst();
            while (!c.isAfterLast()) {
                Station station = new Station(c.getString(0), c.getString(1),
                        c.getDouble(2), c.getDouble(3));

                userCoordinates = userLocationInfo.getUserLocation();
                statDist = distToStation(c.getDouble(2), c.getDouble(3),
                                userCoordinates[0], userCoordinates[1]);
                station.setDistToUser(statDist);

                allStations.add(station);
                c.moveToNext();
            }
            c.close();
        }
        databaseAccess.close();

        Collections.sort(allStations, Station.DistanceComparator);

        return allStations;
    }

    public double distToStation(double lat1, double lon1,
                                double lat2, double lon2) {
        // Earth's radius 3958.75 miles, 6371.0 km
        double earthsRadius = 3958.75;

        double latRad1 = Math.toRadians(lat1);
        double latRad2 = Math.toRadians(lat2);
        double lonRad1 = Math.toRadians(lon1);
        double lonRad2 = Math.toRadians(lon2);

        double latSinOfDiff = Math.sin((latRad2 - latRad1) / 2);
        double lonSinOfDiff = Math.sin((lonRad2 - lonRad1) / 2);

        double a = Math.pow(latSinOfDiff, 2) + Math.pow(lonSinOfDiff, 2)
                * Math.cos(latRad1) * Math.cos(latRad2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = earthsRadius *c;

        return distance;
    }

}
