package com.benalbritton.metrolinkapp2;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

public class StationListing {

    //private DatabaseAccess databaseAccess = DatabaseAccess.getDbInstance(context);

    public ArrayList<Station> getStationsInfo(Context context) {
        ArrayList<Station> allStations = new ArrayList<>();
        DatabaseAccess databaseAccess = DatabaseAccess.getDbInstance(context);

        databaseAccess.open();
        Cursor c = databaseAccess.getStations();
        if(c != null) {
            c.moveToFirst();
            while (!c.isAfterLast()) {
                Station station = new Station(c.getString(0), c.getString(1),
                        c.getDouble(2), c.getDouble(3));
                allStations.add(station);
                c.moveToNext();
            }
            c.close();
        }
        databaseAccess.close();

        return allStations;

    }
}
