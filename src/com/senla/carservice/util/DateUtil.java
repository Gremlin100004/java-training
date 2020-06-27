package com.senla.carservice.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    private static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat("dd.MM.yyyy");
    private static final int END_DAY_HOUR = 23;
    private static final int END_DAY_MINUTE = 59;
    private static final int START_DAY_HOUR = 0;
    private static final int START_DAY_MINUTE = 0;

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
        try {
            return FORMAT_DATE.parse(stringDate);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String getStringFromDate(Date date) {
        return FORMAT_DATE.format(date);
    }

    public static Date getCurrentDayDate(){
        return addHourMinutes(new Date(), START_DAY_HOUR, START_DAY_MINUTE);
    }

    public static Date getEndOfDayDate(Date date){
        return addHourMinutes(date, END_DAY_HOUR, END_DAY_MINUTE);
    }
}