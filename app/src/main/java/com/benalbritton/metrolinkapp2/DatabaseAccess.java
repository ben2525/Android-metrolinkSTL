package com.benalbritton.metrolinkapp2;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

public class DatabaseAccess {

    private SQLiteOpenHelper sqLiteOpenHelper;
    private SQLiteDatabase db;
    private static DatabaseAccess dbInstance;

    UserLocationInfo userLocationInfo;

    private DatabaseAccess(Context context) {
        this.sqLiteOpenHelper = new DatabaseOpenHelper(context);
        userLocationInfo = new UserLocationInfo(context);
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
        return db.rawQuery(sqlStations, null);
    }

    public Cursor arriveTimes(String scheduleTable, String closestStation, double currentTime) {
        String sqlTimes = "SELECT " + scheduleTable + ".arrival_time " +
                "FROM " + scheduleTable + " JOIN stations on "
                + scheduleTable + ".stop_id = stations.stop_id WHERE "
                + scheduleTable + ".stop_id = " + closestStation
                + " AND " + scheduleTable + ".arrival_time  > " + currentTime +
                ";";

        return db.rawQuery(sqlTimes, null);
    }

}
