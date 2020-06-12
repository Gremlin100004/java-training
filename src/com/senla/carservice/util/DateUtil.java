package com.senla.carservice.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static Date addDays(Date date, int days){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    public static Date addHourMinutes(Date date, int hour, int minute){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hour);
        calendar.add(Calendar.MINUTE, minute);
        return calendar.getTime();
    }

    public static Date getDateWithoutTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
}
