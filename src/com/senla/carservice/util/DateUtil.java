package com.senla.carservice.util;

import com.senla.carservice.exception.BusinessException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    private static final int START_DAY_HOUR = 0;
    private static final int START_DAY_MINUTE = 0;
    private static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd kk:mm");
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

    private DateUtil() {
    }

    public static Date addDays(Date date, int days) {
        if (date == null) {
            throw new BusinessException("date is null");
        }
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

    public static Date getDatesFromString(String stringDate, boolean isTime) {
        try {
            return isTime ? DATE_TIME_FORMAT.parse(stringDate) : DATE_FORMAT.parse(stringDate);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String getStringFromDate(Date date, boolean isTime) {
        return isTime ? DATE_TIME_FORMAT.format(date.getTime()) : DATE_FORMAT.format(date.getTime());
    }

    public static Date bringStartOfDayDate(Date date) {
        if (addHourMinutes(date, START_DAY_HOUR, START_DAY_MINUTE).before(new Date())) {
            return addHourMinutes(new Date(), START_DAY_HOUR, START_DAY_MINUTE);
        } else {
            return addHourMinutes(date, START_DAY_HOUR, START_DAY_MINUTE);
        }
    }

    public static void checkDateTime(Date executionStartTime, Date leadTime, Boolean periodTime) {
        if (executionStartTime == null || leadTime == null) {
            throw new BusinessException("Error date format, should be \"yyyy-MM-dd hh:mm\"");
        }
        if (executionStartTime.after(leadTime)) {
            throw new BusinessException("The execution start time is greater than lead time");
        }
        if (executionStartTime.before(new Date()) && !periodTime) {
            throw new BusinessException("The execution start time is less than current Date");
        }
    }
}