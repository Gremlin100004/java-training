package com.senla.carservice.util;

import com.senla.carservice.exception.DateException;
import com.senla.carservice.exception.NullDateException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    private static final int END_DAY_HOUR = 23;
    private static final int END_DAY_MINUTE = 59;
    private static final int START_DAY_HOUR = 0;
    private static final int START_DAY_MINUTE = 0;

    private DateUtil() {
    }

    public static Date addDays(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);

        return calendar.getTime();
    }

    public static Date addHourMinutes(Date date, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.HOUR_OF_DAY, hour);
        calendar.add(Calendar.MINUTE, minute);
        return calendar.getTime();
    }

    public static Date getDatesFromString(String stringDate, Boolean isTime) {
        String format;
        if (isTime){
            format = "dd.MM.yyyy hh:mm";
        } else
            format = "dd.MM.yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try {
            return dateFormat.parse(stringDate);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String getStringFromDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        return dateFormat.format(date.getTime());
    }

    public static Date bringStartOfDayDate(Date date) {
        if (addHourMinutes(date, START_DAY_HOUR, START_DAY_MINUTE).compareTo(new Date()) < 1) {
            return addHourMinutes(new Date(), START_DAY_HOUR, START_DAY_MINUTE);
        } else {
            return addHourMinutes(date, START_DAY_HOUR, START_DAY_MINUTE);
        }
    }

    public static Date bringEndOfDayDate(Date date) {
        return addHourMinutes(date, END_DAY_HOUR, END_DAY_MINUTE);
    }

    public static void checkDateTime(Date executionStartTime, Date leadTime){
        if (executionStartTime == null)
            throw new NullDateException("Error date format, should be \"dd.MM.yyyy hh:mm\"");
        if (leadTime == null) throw new NullDateException("Error date format, should be \"dd.MM.yyyy hh:mm\"");
        if (executionStartTime.compareTo(leadTime) > 0) throw new
                DateException("The execution start time is greater than lead time");
        if (executionStartTime.compareTo(new Date()) < 1) throw new
                DateException("The execution start time is less than current Date");
    }

    public static void checkPeriodTime(Date startPeriodTime, Date endPeriodTime){
        if (startPeriodTime == null) throw new NullDateException("Error date format, should be \"dd.MM.yyyy hh:mm\"");
        if (endPeriodTime == null) throw new NullDateException("Error date format, should be \"dd.MM.yyyy hh:mm\"");
        if (startPeriodTime.compareTo(endPeriodTime) > 0) throw new
                DateException("The period time is wrong");
    }
}