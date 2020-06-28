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
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        try {
            return dateFormat.parse(stringDate);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String getStringFromDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format(date.getTime());
    }

    public static Date bringStartOfDayDate(Date date){
        if (addHourMinutes(date, START_DAY_HOUR, START_DAY_MINUTE).compareTo(new Date()) < 1){
            return addHourMinutes(new Date(), START_DAY_HOUR, START_DAY_MINUTE);
        } else {
            return addHourMinutes(date, START_DAY_HOUR, START_DAY_MINUTE);
        }
    }

    public static Date bringEndOfDayDate(Date date){
        return addHourMinutes(date, END_DAY_HOUR, END_DAY_MINUTE);
    }

    public static void checkDateTime(Date executionStartTime, Date leadTime) throws NullDateException, DateException {
        if(executionStartTime == null) throw new NullDateException("The date is null", executionStartTime);
        if(leadTime == null) throw new NullDateException("The date is null", leadTime);
        if (executionStartTime.compareTo(leadTime) > 0) throw new
                DateException("The execution start time is greater than lead time", executionStartTime, leadTime);
        if (executionStartTime.compareTo(new Date()) < 1) throw new
                DateException("The execution start time is less than current Date", executionStartTime, new Date());
    }

    public static void checkPeriodTime(Date startPeriodTime, Date endPeriodTime) throws NullDateException, DateException {
        if(startPeriodTime == null) throw new NullDateException("The date is null", startPeriodTime);
        if(endPeriodTime == null) throw new NullDateException("The date is null", endPeriodTime);
        if (startPeriodTime.compareTo(endPeriodTime) > 0) throw new
                DateException("The period time is wrong", startPeriodTime, endPeriodTime);
    }
}