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

    //private DatabaseAccess dbAccess;
    private Context context;

    public ArrivalTimes(Context context) {
        this.context = context;

    }

    //private String dayOfWeek() {
    public String dayOfWeek() {
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


    //private String pickDBTable() {
    public String pickDBTable() {

        String today = dayOfWeek();
        //String dbTable = dbTableMap().get(today);

        //return dbTable;
        return dbTableMap().get(today);
    }


    private double currentTime() {

        Calendar calendar = new GregorianCalendar();
        TimeZone timeZone = TimeZone.getTimeZone("GMT-5");
        calendar.setTimeZone(timeZone);
        Date trialTime = new Date();
        calendar.setTime(trialTime);

        return calendar.get(Calendar.MILLISECOND)/1000.0/3600.0 +
                calendar.get(Calendar.SECOND)/3600.0 +
                calendar.get(Calendar.MINUTE)/60.0 +
                calendar.get(Calendar.HOUR_OF_DAY);
    }


    public ArrayList<Double> timesList() {

        DatabaseAccess dbAccess = DatabaseAccess.getDbInstance(context);
        ArrayList<Double> arriveTimeList = new ArrayList<>();
        String closeStation = new StationDistances(context).closestStation();

        dbAccess.open();
        Cursor c = dbAccess.arriveTimes(pickDBTable(), closeStation, currentTime());
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
