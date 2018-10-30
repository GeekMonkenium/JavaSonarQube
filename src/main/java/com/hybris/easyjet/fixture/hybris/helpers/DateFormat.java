package com.hybris.easyjet.fixture.hybris.helpers;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by dwebb on 11/16/2016.
 */
@Deprecated
public class DateFormat {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    private Date date;

    public static String getDate() {
        return getDate(0);
    }

    public static String getDate(int diff) {
        return DateFormat.getDate(Calendar.YEAR);
    }

    public static String getDate(int calendarUnit, int diff) {
        Format f = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Calendar cal = Calendar.getInstance();
        cal.add(calendarUnit, diff);
        return f.format(cal.getTime());
    }

    public static String getDateFromSpecificFormat(String dateValue, java.text.DateFormat fromFormat) throws ParseException {
        Date date = fromFormat.parse(dateValue);
        java.text.DateFormat toFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        return toFormat.format(date);
    }

    public static String getDate(String dateValue) throws ParseException {
        java.text.DateFormat fromFormat = new SimpleDateFormat("E dd-MMMM-yyyy HH-mm-ss", Locale.ENGLISH);
        Date date = fromFormat.parse(dateValue);
        java.text.DateFormat toFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        return toFormat.format(date);
    }

    public static String getDateInSpecificFormat(String dateValue) throws ParseException {
        java.text.DateFormat fromFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        java.text.DateFormat toFormat = new SimpleDateFormat("yyyy-mm-dd'T'HH:mm", Locale.ENGLISH);
        Date date = fromFormat.parse(dateValue);
        return toFormat.format(date);
    }

    public static Calendar getDateCalender(String date) {

        Calendar calender = Calendar.getInstance();
        try {
            calender.setTime(getDateFormatterWithDay().parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calender;
    }

    private static SimpleDateFormat getDateFormatterWithDay() {
        return new SimpleDateFormat("EEE d-MMM-yyyy HH-mm-ss", Locale.ENGLISH);
    }

    public static Date getDateF(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE dd-MMM-yyyy HH-mm-ss", Locale.ENGLISH);
        Date actualDate = sdf.parse(date);
        return actualDate;
    }

    /**
     * The method return the total time (as hour) of a flight
     *
     * @param totalDuration of the flight. Ex: 2hrs30m
     * @return
     */
    public static int duration(String totalDuration) {

        int hours = Integer.parseInt(totalDuration.split("h")[0]);
        String temp = totalDuration.split("h")[1];
        int minutes = Integer.parseInt(temp.split("m")[0]);
        return hours * 60 + minutes;
    }

    public static String getDateFormat(String date, int noOfCarHireDays) {
        return getDropOffDateFromArrivalDate(date, noOfCarHireDays);
    }

    public static String getDateFormat(String date) {
        if (date != null)
            return new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                    .format(DateFormat.getDateCalender(date).getTime());
        return null;
    }

    private static String getDropOffDateFromArrivalDate(String arrivalDateTime, int noOfCarHireDays) {
        Calendar arrivalCalDate = getInBoundDate(arrivalDateTime);
        arrivalCalDate.add(Calendar.DATE, noOfCarHireDays);
        return new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                .format(arrivalCalDate.getTime());
    }

    private static Calendar getInBoundDate(String date) {
        Calendar calender = Calendar.getInstance();
        try {
            calender.setTime(getDateFormatterWithDay().parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calender;
    }

    public static String getAddedTime(String flightDate, int minutes) {
        Calendar date = getInBoundDate(flightDate);
        date.add(Calendar.MINUTE, +minutes);
        return getDateFormatter().format(date.getTime());
    }

    private static SimpleDateFormat getDateFormatter() {
        return new SimpleDateFormat("HH-mm-ss", Locale.ENGLISH);
    }

    public static Duration getDateTimeDifferenceWithPresentDateTime(String fromDate) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime bookingDateTime = LocalDateTime.parse(fromDate, formatter);
        Duration duration = Duration.between(bookingDateTime, now);
        return duration;
    }

    public String as(String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.ENGLISH);
        return simpleDateFormat.format(date);
    }

    public String asUK() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        return simpleDateFormat.format(date);
    }

    public String asYearMonthDay() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        return simpleDateFormat.format(date);
    }

    public DateFormat today() {
        date = new Date();
        return this;
    }

    public DateFormat addDayToDate(int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, days);
        date = cal.getTime();
        return this;
    }

    public String addDay(int days) throws ParseException {
        Format f = new SimpleDateFormat("dd-MM-yyy", Locale.ENGLISH);
        return addDay(days, f.format(date));
    }

    public String addDay(int days, String date) throws ParseException {
        Format f = new SimpleDateFormat("dd-MM-yyy", Locale.ENGLISH);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new SimpleDateFormat("dd-MM-yyy", Locale.ENGLISH).parse(date));
        cal.add(Calendar.DAY_OF_YEAR, days);
        return f.format(cal.getTime());
    }

    public DateFormat withDate(String date) {
        SimpleDateFormat df = new SimpleDateFormat("E dd-MMM-yyyy HH-mm-ss", Locale.ENGLISH);
        try {
            this.date = df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return this;
    }
}
