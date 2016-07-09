package com.benalbritton.metrolinkapp2;


import android.content.Context;
import android.database.Cursor;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.TimeZone;

public class ArrivalTimes {

    private Context context;
    ArrayList<Station> stationList;

    public ArrivalTimes(Context context) {
        this.context = context;
    }

    private String dayOfWeek() {
        GregorianCalendar day = new GregorianCalendar();
        String[] weekdays = new DateFormatSymbols().getWeekdays();

        return weekdays[day.get(Calendar.DAY_OF_WEEK)];
    }


    private static final String WEEKDAY_TABLE = "stopsmf";
    private static final String SATURDAY_TABLE = "stopssat";
    private static final String SUNDAY_TABLE = "stopssun";

    private HashMap<String, String> dbTableMap() {
        HashMap<String, String> tableMap = new HashMap<>();

        tableMap.put("Monday", WEEKDAY_TABLE);
        tableMap.put("Tuesday", WEEKDAY_TABLE);
        tableMap.put("Wednesday", WEEKDAY_TABLE);
        tableMap.put("Thursday", WEEKDAY_TABLE);
        tableMap.put("Friday", WEEKDAY_TABLE);
        tableMap.put("Saturday", SATURDAY_TABLE);
        tableMap.put("Sunday", SUNDAY_TABLE);

        return tableMap;
    }


    private String pickDBTable() {

        String today = dayOfWeek();
        return dbTableMap().get(today);
    }


    private double timeAsHourDouble(String s) {
        String[] hourMin = s.split(":");
        int hour = Integer.parseInt(hourMin[0]);
        int mins = Integer.parseInt(hourMin[1]);
        int hoursInSeconds = hour * 3600;
        return hoursInSeconds + mins;
    }


    public ArrayList<Double> timesList() {

        DatabaseAccess dbAccess = DatabaseAccess.getDbInstance(context);
        ArrayList<Double> arriveTimeList = new ArrayList<>();

        double currentTime = new CurrentTime().currentTimeDoubleAsHour();

        // delete below after passing station into this function
        //String closeStation = new StationDistances(context).closestStation();

        String closeStation = new StationDistances(context).getStationsInfo().get(0).getId();

        /*
            Bring in list of stations from StationDistances.java,  getStationsInfo()
            Sort list to get closest station
            Maybe do in separate function
         */


        dbAccess.open();

        // Use info from above to replace   closeStation   argument
        Cursor c = dbAccess.arriveTimes(pickDBTable(), closeStation, currentTime);

        if(c != null) {
            c.moveToFirst();
            while (!c.isAfterLast()) {



                Double time = Double.valueOf(c.getString(0));
                arriveTimeList.add(time);
                c.moveToNext();
            }
            c.close();
        }
        dbAccess.close();

        return arriveTimeList;
    }
}
