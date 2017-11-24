package com.hcmute.trietthao.yourtime.service.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class DateUtils {

    public static Integer getIntCurrentDateTime() {
        String timeStamp = new SimpleDateFormat("MMddHHmmss").format(Calendar.getInstance().getTime());
        return Integer.valueOf(timeStamp);
    }

    public static Date converStringToDateTime(String input) throws ParseException {

        SimpleDateFormat dateResultFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        dateResultFormat.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
        Date date = dateResultFormat.parse(input.replaceAll("Z$", "+0000"));
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR,cal.get(Calendar.HOUR)-7);
        return cal.getTime();
    }

    public static String getDateTimeToInsertUpdate(String input) throws ParseException {
        Date date = converStringToDateTime(input);

        String dateFormat = "yyyy-MM-dd HH:mm:ss";
        return formatDate(date, dateFormat);
    }

    public static String getDateDisplay(String input) throws ParseException {
        Date date = converStringToDateTime(input);
        String dateFormat = "dd";
        return formatDate(date, dateFormat);
    }

    public static String getDisplayDate(String input){
        Date date = null;
        try {
            date = converStringToDateTime(input);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String s=", ";
        switch (date.getDay()){
            case 1: s+="Monday "; break;
            case 2: s+="Tuesday "; break;
            case 3: s+="Wednesday "; break;
            case 4: s+="Thurday "; break;
            case 5: s+="Friday "; break;
            case 6: s+="Saturday "; break;
            case 7: s+="Sunday "; break;
        }

        String dateFormat1 = "HH:mm";
        String dateFormat2 = "dd";
        String dateFormat3 = "MM";
        return formatDate(date, dateFormat1)+s+formatDate(date, dateFormat2)+" Th"+formatDate(date, dateFormat3);
    }

    public static boolean isDateInCurrentWeek(Date date) {
        Calendar currentCalendar = Calendar.getInstance();
        int week = currentCalendar.get(Calendar.WEEK_OF_YEAR);
        int year = currentCalendar.get(Calendar.YEAR);
        Calendar targetCalendar = Calendar.getInstance();
        targetCalendar.setTime(date);
        int targetWeek = targetCalendar.get(Calendar.WEEK_OF_YEAR);
        int targetYear = targetCalendar.get(Calendar.YEAR);
        return week == targetWeek && year == targetYear;
    }

    public static boolean isToday(Date date) {
        return isSameDay(date, Calendar.getInstance().getTime());
    }

    public static boolean isDateNull(String input) throws ParseException {
        Date date = converStringToDateTime(input);
        if(date.getMonth()==0)
            return true;
        return false;
    }

    public static boolean isOverDueDate(String input) throws ParseException {
        return converStringToDateTime(input).compareTo(Calendar.getInstance().getTime())<0;
    }

    public static Date convertTimeZone(Date date, TimeZone fromTZ, TimeZone toTZ) {
        long fromTZDst = 0;
        if (fromTZ.inDaylightTime(date)) {
            fromTZDst = fromTZ.getDSTSavings();
        }

        long fromTZOffset = fromTZ.getRawOffset() + fromTZDst;

        long toTZDst = 0;
        if (toTZ.inDaylightTime(date)) {
            toTZDst = toTZ.getDSTSavings();
        }
        long toTZOffset = toTZ.getRawOffset() + toTZDst;

        return new Date(date.getTime() + (toTZOffset - fromTZOffset));
    }

    // format date object to string with a format
    private static String formatDate(Date date, String desFormat) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat dateResultFormat = new SimpleDateFormat(desFormat, Locale.US);
        dateResultFormat.setTimeZone(TimeZone.getDefault());
        return dateResultFormat.format(date);
    }

    public static String formatYear(Date date) {
        String dateFormat = "yyyy";
        return formatDate(date, dateFormat);
    }

    public static String formatMonthYear(Date date) {
        String dateFormat = "MM/yyyy";
        return formatDate(date, dateFormat);
    }

    public static String formatDateAsHourMinute(Date date) {
        String dateFormat = "HH:mm";
        return formatDate(date, dateFormat);
    }

    public static String formatDateAsHourMinutePeriods(Date date) {
        String dateFormat = "HH:mm a";
        return formatDate(date, dateFormat);
    }

    public static String formatDateAsDay(Date date) {
        String dateFormat = "EEEE";
        return formatDate(date, dateFormat);
    }


    // get year of date as number
    public static int getYearOfDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    // check 2 date is same day
    public static boolean isSameDay(Date leftDate, Date rightDate) {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(leftDate);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(rightDate);
        return startCalendar.get(Calendar.YEAR) == endCalendar.get(Calendar.YEAR) &&
                startCalendar.get(Calendar.DAY_OF_YEAR) == endCalendar.get(Calendar.DAY_OF_YEAR);
    }

    // get first date of a month
    public static Date getFirstDayOfMonth(int month, int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.AM_PM, Calendar.AM);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.YEAR, year);
        int minDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DATE, minDay);
        return cal.getTime();
    }

    // get first date of a month
    public static Date getFirstDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.AM_PM, Calendar.AM);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        int minDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DATE, minDay);
        return cal.getTime();
    }

    // get first date of a previous month
    public static Date getFirstDayOfPreviousMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.AM_PM, Calendar.AM);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.MONTH, -1);
        int minDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DATE, minDay);
        return cal.getTime();
    }

    // get last date of a month
    public static Date getLastDayOfMonth(int month, int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.AM_PM, Calendar.AM);
        cal.set(Calendar.HOUR, 23);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.MILLISECOND, 999);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.YEAR, year);
        int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DATE, maxDay);
        return cal.getTime();
    }

    // get last date of a month
    public static Date getLastDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.AM_PM, Calendar.AM);
        cal.set(Calendar.HOUR, 23);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.MILLISECOND, 999);
        int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DATE, maxDay);
        return cal.getTime();
    }

    // get last date of a previous month
    public static Date getLastDayOfPreviousMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.AM_PM, Calendar.AM);
        cal.set(Calendar.HOUR, 23);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.MILLISECOND, 999);
        cal.add(Calendar.MONTH, -1);
        int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DATE, maxDay);
        return cal.getTime();
    }

    // get first date of date
    public static Date getFirstDateOfDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // reset time
        cal.set(Calendar.AM_PM, Calendar.AM);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    // get last date of date
    public static Date getLastDateOfDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // reset time
        cal.set(Calendar.AM_PM, Calendar.AM);
        cal.set(Calendar.HOUR, 23);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }

    // check date between 2 date object or not
    public static boolean checkDateBetweenTwoDate(Date date, Date minDate, Date maxDate) {
        return date.compareTo(minDate) >= 0 && date.compareTo(maxDate) <= 0;
    }

    // get month as string from month number

}