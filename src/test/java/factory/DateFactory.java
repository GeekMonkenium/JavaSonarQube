package factory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * DateFactory generate date in the format used by ACP from meaningful string.
 * See src/test/java/feature/document/steps/constants/StepsRegex.DATES
 *
 * @author gd <g.dimartino@reply.it>
 */
public class DateFactory {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
    private static final Calendar date = Calendar.getInstance();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH-mm-ss");


    /**
     * Get a random date between today and next 10 days
     *
     * @return a string representing the generate date in dateFormat
     */
    public static String getDate() {
        date.setTime(new Date());
        date.add(Calendar.DAY_OF_MONTH, new Random().nextInt(10));
        return dateFormat.format(date.getTime());
    }

    /**
     * Get the date specified as argument
     *
     * @param date it can be today or a meaningful sentence that include the number of days to add to today
     * @return a string representing the generate date in dateFormat
     * @throws IllegalArgumentException if the date argument doesn't include a number of day to add from today and is not today
     */
    public static String getDate(String date) {
        DateFactory.date.setTime(new Date());

        if (date.equals("today")) {
            return dateFormat.format(DateFactory.date.getTime());
        } else {
            try {
                new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).parse(date);
                return date;
            } catch (ParseException ignored) {
                Matcher p = Pattern.compile("\\d+").matcher(date);
                if (p.find()) {
                    DateFactory.date.add(Calendar.DAY_OF_MONTH, Integer.valueOf(p.group()));
                    return dateFormat.format(DateFactory.date.getTime());
                } else {
                    throw new IllegalArgumentException("Date specified is not valid");
                }
            }
        }
    }

    /**
     * Given a date in EEE dd-MMM-yyyy HH-mm-ss, the method return the date in the desired format
     *
     * @param date       in EEE dd-MMM-yyyy HH-mm-ss format
     * @param formatDate like yyyy/mm/dd
     * @return the new date
     */
    public static String getDateFromCompleteDateFormatter(String date, String formatDate) {
        SimpleDateFormat originalDate = new SimpleDateFormat("EEE dd-MMM-yyyy HH-mm-ss", Locale.ENGLISH);
        SimpleDateFormat targetDate = new SimpleDateFormat(formatDate, Locale.ENGLISH);
        String result = "";
        try {
            result = targetDate.format(originalDate.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Given a date in EEE dd-MMM-yyyy HH-mm-ss, the method return the time in the desired format
     *
     * @param date       in EEE dd-MMM-yyyy HH-mm-ss format
     * @param formatTime like hh:mm:ss
     * @return the new time
     */
    public static String getTimeFromCompleteDateFormatter(String date, String formatTime) {
        SimpleDateFormat originalDate = new SimpleDateFormat("EEE dd-MMM-yyyy HH-mm-ss", Locale.ENGLISH);
        SimpleDateFormat targetDate = new SimpleDateFormat(formatTime, Locale.ENGLISH);
        String result = "";
        try {
            result = targetDate.format(originalDate.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * given a time in HH:mm:ss, the method add minute
     *
     * @param time HH:mm:ss format
     * @param min  to add to time
     * @return the new time
     */
    public static String addMinuteTime(String time, int min) {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
        Date d = null;
        try {
            d = df.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.MINUTE, min);
        return df.format(cal.getTime());
    }

    /**
     * given a time in YYYY-MM-dd, the method add minute
     *
     * @param date YYYY-MM-dd format
     * @param day  to add to time
     * @return the new time
     */
    public static String addDayDate(String date, int day) {
        LocalDate actualDate = LocalDate.parse(date);
        return actualDate.plusDays(day).toString();
    }

    public static Duration getDateTimeDifferenceWithPresentDateTime(String fromDate) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime bookingDateTime = LocalDateTime.parse(fromDate, formatter);
        Duration duration = Duration.between(bookingDateTime, now);
        return duration;
    }
}