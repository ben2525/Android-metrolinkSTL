package com.benalbritton.metrolinkapp2;


import android.content.Context;
import android.database.Cursor;

import java.sql.Time;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class ArrivalTimes {

    private Context context;

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


    private double timeAsHourDouble(String s) {
        String[] hourMin = s.split(":");
        double hours = Double.parseDouble(hourMin[0]);
        double minutesAsHour = Double.parseDouble(hourMin[1]) / 60.0;

        return hours + minutesAsHour;
    }


    public ArrayList<ArriveTimeDetail> timesList() {

        String dbTable = dbTableMap().get(dayOfWeek());

        DatabaseAccess dbAccess = DatabaseAccess.getDbInstance(context);
        ArrayList<ArriveTimeDetail> arriveTimeList = new ArrayList<>();

        String currentTime = new CurrentTime().timeAsString();
        //Time currentTime = new CurrentTime().timeAsString();

        String closeStation = new StationDistances(context).getStationsInfo().get(0).getId();

        dbAccess.open();
        Cursor c = dbAccess.arriveTimes(dbTable, closeStation, currentTime);
        if(c != null) {
            c.moveToFirst();
            while (!c.isAfterLast()) {
                ArriveTimeDetail arriveTimeDetail = new ArriveTimeDetail();
                arriveTimeDetail.setTimeAsDouble(timeAsHourDouble(c.getString(0)));
                arriveTimeDetail.setTimeAsString(c.getString(0));
                arriveTimeDetail.setRouteColorDirection(c.getString(1));
                arriveTimeList.add(arriveTimeDetail);
                c.moveToNext();
            }
            c.close();
        }
        dbAccess.close();

        return arriveTimeList;
    }
}
