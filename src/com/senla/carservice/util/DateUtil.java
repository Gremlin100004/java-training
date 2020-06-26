package com.senla.carservice.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static Date addDays(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    public static Date addHourMinutes(Date date, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hour);
        calendar.add(Calendar.MINUTE, minute);
        return calendar.getTime();
    }

    public static Date getDatesFromString(String stringDate) {
        // можно вынести в константу
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

        // проще вот так:
        /*
        try {
            return format.parse(stringDate);
        } catch (ParseException e) {
            return null;
        }
        */

        Date date;
        try {
            date = format.parse(stringDate);
        } catch (ParseException e) {
            date = null;
        }
        return date;
    }

    public static String getStringFromDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        return format.format(date);
    }
}