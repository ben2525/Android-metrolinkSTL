package com.benalbritton.metrolinkapp2;


import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class CurrentTime {

    private Calendar generalCalendar() {

        Calendar genericCalendar = new GregorianCalendar();
        TimeZone timeZone = TimeZone.getTimeZone("GMT-5");
        genericCalendar.setTimeZone(timeZone);
        Date trialTime = new Date();
        genericCalendar.setTime(trialTime);

        return genericCalendar;
    }

    public double currentTimeDoubleAsHour() {

        Calendar calendar = generalCalendar();

        return calendar.get(Calendar.MILLISECOND)/1000.0/3600.0 +
                calendar.get(Calendar.SECOND)/3600.0 +
                calendar.get(Calendar.MINUTE)/60.0 +
                calendar.get(Calendar.HOUR_OF_DAY);
    }

    public long currentTimeLongAsMillisecond() {

        Calendar calendar = generalCalendar();
        double timeLong = calendar.get(Calendar.MILLISECOND) +
                calendar.get(Calendar.SECOND) * 1000.0 +
                calendar.get(Calendar.MINUTE) * 60.0 * 1000.0 +
                calendar.get(Calendar.HOUR_OF_DAY) * 3600.0 * 1000.0;

        return Math.round(timeLong);
    }

}
