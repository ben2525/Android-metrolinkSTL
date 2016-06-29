package com.benalbritton.metrolinkapp2;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseAccess {

    private SQLiteOpenHelper sqLiteOpenHelper;
    private SQLiteDatabase db;
    private static DatabaseAccess dbInstance;

    ////////////////////////////////////////////////
    UserLocationInfo userLocationInfo;
    ///////////////////////////////////////////////

    private DatabaseAccess(Context context) {
        this.sqLiteOpenHelper = new DatabaseOpenHelper(context);

        ///////////////////////////////////////////////////////////
        ///////////////////////
        //Context.mcontext = context;
        ////////////////////////
        //UserLocationInfo userLocationInfo;

        userLocationInfo = new UserLocationInfo(context);
        ////////////////////////////////////////////////////////
    }




    public static DatabaseAccess getDbInstance(Context context) {
        if (dbInstance == null) {
            dbInstance = new DatabaseAccess(context);
        }
        return dbInstance;
    }

    public void open() {
        this.db = sqLiteOpenHelper.getReadableDatabase();
    }

    public void close() {
        if (db != null) {
            this.db.close();
        }
    }

    public Cursor getStations() {
        String sqlStations = "SELECT * FROM stations";
        Cursor c = db.rawQuery(sqlStations, null);
        return c;
    }

    public Cursor arriveTimes(String scheduleTable, String closestStation, double currentTime) {
        //String sqlTimes = "SELECT cast(stopsmf.arrival_time as REAL) " +
          //      "FROM stopsmf JOIN stations on stopsmf.stop_id = stations.stop_id WHERE stopsmf.stop_id = 10601;";
        String sqlTimes = "SELECT cast(stopsmf.arrival_time as REAL) " +
                "FROM " + scheduleTable + " JOIN stations on "
                + scheduleTable + ".stop_id = stations.stop_id WHERE "
                + scheduleTable + ".stop_id = " + closestStation
                + " AND cast(stopsmf.arrival_time as REAL) > " + currentTime +
                ";";

        // " AND cast(arrival_time as real) > " + currentTime +
        /*
        "FROM " + scheduleTable + " WHERE stop_id = " + closestStation +
                " AND " +
                "arrival_time > " + currentTime +
         */

        Cursor c = db.rawQuery(sqlTimes, null);
        return db.rawQuery(sqlTimes, null);
    }

}
