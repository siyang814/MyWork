package com.example.mywork.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author mingfanglin
 * 时间日期处理类
 */
public final class DateUtil {

    /**
     * default date format pattern
     */
    public final static String DATE_FORMAT = "yyyy-MM-dd";
    public final static String DATE_FORMAT2 = "yyyy年MM月dd日";
    public final static String DATE_FORMAT3 = "yyyyMMdd";
    public final static String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";
    public final static String FULL_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public final static String FULL_DATE_TIME_FORMAT3 = "yyyy-MM-dd HH:mm";
    public final static String FULL_DATE_TIME = "yyyy-MM-dd HH:mm:ss:SSS";
    public final static String TIME_FORMAT = "HH:mm";
    public final static String MONTH_DAY_HOUR_MINUTE_FORMAT = "MM-dd HH:mm";
    public final static String FULL_DATE_TIME_FORMAT_2="yyyyMMddHHmmss";
    public final static String FULL_DATE_TIME_FORMAT_4 = "yyyyMMddHHmm";
    
    private static final int DAYS_OF_A_WEEK = 7;

    private DateUtil() {
    }

    /**
     * parse date with the default pattern
     * 
     * @param date string date
     * @return the parsed date
     */
    public static Date parseDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT3);
        try {
            return format.parse((date.length() > 8 ? date.substring(0, 8) : date));
        } catch (ParseException e) {
            return new Date();
        }
    }

    public static Date parseDate(String date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            return format.parse(date);
        } catch (ParseException e) {
            return new Date();
        }
    }

    /**
     * 获取增加小时后的 Date
     * 
     * @param date
     * @param i
     * @return squall add 20100225
     */
    public static Date addHour(Date date, int i) {
        Calendar calendar = getDefaultCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, i);
        return calendar.getTime();
    }

    /**
     * format date with the default pattern
     * 
     * @param date the date that want to format to string
     * @return the formated date
     */
    public static String formatDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        return format.format(date);
    }

    public static String formatTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(TIME_FORMAT);
        return format.format(date);
    }

    /**
     * format date with the given pattern
     * 
     * @param date the date that want to format to string
     * @param pattern the formated pattern
     * @return the formated date
     */
    public static String formatDate(Date date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    /**
     * get current date
     * 
     * @return the string of current date
     */
    public static String getCurrentDateFormat() {
        return formatDate(new Date());
    }

    /**
     * get number of days between the two given date
     * 
     * @param fromDate the begin date
     * @param endDate the end date
     * @return the number of days between the two date
     */
    public static int getDateNum(Date fromDate, Date endDate) {
        long days = (endDate.getTime() - fromDate.getTime()) / (1000 * 60 * 60 * 24);
        return (int) days;
    }

    /**
     * add day to the date
     * 
     * @param date the added date
     * @param number the number to add to the date
     * @return the added date
     */
    public static Date addDate(Date date, int number) {
        Calendar calendar = getDefaultCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, number);
        return calendar.getTime();
    }
    
    public static Date addMonth(Date date, int number) {
        Calendar calendar = getDefaultCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, number);
        return calendar.getTime();
    }
    
    public static Date subMonth(Date date, int number) {
        Calendar calendar = getDefaultCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, number);
        return calendar.getTime();
    }

    /**
     * get the default calendar
     * 
     * @return the calendar instance
     */
    public static Calendar getDefaultCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        return calendar;
    }

    /**
     * format the date into string value
     * 
     * @param calendar the formated calendar
     * @return the string date
     */
    public static String getStringDate(Calendar calendar) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return year + "-" + getNiceString(month) + "-" + getNiceString(day);
    }

    /**
     * according to the pattern yyyy-MM-dd
     * 
     * @param value the value
     * @return the formated value
     */
    public static String getNiceString(int value) {
        String str = "00" + value;
        return str.substring(str.length() - 2, str.length());
    }

    /**
     * get calendar from date
     * 
     * @param date the passing date
     * @return the calendar instance
     */
    public static Calendar getCalendarFromDate(Date date) {
        Calendar calendar = getDefaultCalendar();
        calendar.setTime(date);
        return calendar;
    }

    public static String getInterval(Date startDate, Date endDate) {
        long intervalTime = endDate.getTime() - startDate.getTime();
        return getInterval(intervalTime);
    }
    
    public static int getIntervalMinute(Date startDate, Date endDate){
        long intervalTime = endDate.getTime() - startDate.getTime();
        return (int) (intervalTime / (1000 * 60));
    }
    
    public static int getIntervalHour(Date startDate, Date endDate){
        long intervalTime = endDate.getTime() - startDate.getTime();
        return (int) ((intervalTime / (1000 * 60)) / 60);
    }
    
    public static String getInterval(long intervalTime) {
        int hour = (int) (intervalTime / (1000 * 60 * 60));
        int minute = (int) (intervalTime / (1000 * 60) - hour * 60);
        int second = (int) ((intervalTime / 1000) - hour * 60 * 60 - minute * 60);
        if (hour > 0) {
            return hour + "小时 " + minute + "分 " + second + "秒";
        } else if (minute > 0) {
            return minute + "分钟 " + second + "秒";
        } else {
            return second + "秒";
        }
    }
    
    public static int getIntervalDay(String sDateStr, String eDateStr) {
		int day = 0;
		try {
			Date sDate = DateUtil.parseDate(sDateStr, DateUtil.DATE_FORMAT);
			Date eDate = DateUtil.parseDate(eDateStr, DateUtil.DATE_FORMAT);
			long intervalTime = eDate.getTime() - sDate.getTime();
			day = (int) (intervalTime / 1000 / 60 / 60 / 24);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return day;
	}
    
    public static String getDateStr(Date date) {
    	return getYear(date) + "年" + getMonth(date) + "月" + getDayOfMonth(date) + "日";
    }
    
    public static int getYear(Date date) {
        Calendar calendar = getCalendarFromDate(date);
        return calendar.get(Calendar.YEAR);
    }

    public static int getMonth(Date date) {
        Calendar calendar = getCalendarFromDate(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    public static int getDayOfMonth(Date date) {
        Calendar calendar = getCalendarFromDate(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static int getHour(Date now) {
        Calendar calendar = getCalendarFromDate(now);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static int getWeekOfYear(Date date) {
        Calendar calendar = getCalendarFromDate(date);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        return calendar.get(Calendar.WEEK_OF_YEAR) - 1;
    }

    public static Date getCurrentDate() {
        Calendar calendar = getCalendarFromDate(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static Date getNextDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getCurrentDate());
        calendar.add(Calendar.DATE, 1);
        return calendar.getTime();
    }
    
    /**
     * 一周的日期
     * @param date
     * @return
     */
    public static List<Date> getWeekDayOfYear(Date date) {
        Calendar calendar = getCalendarFromDate(date);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setMinimalDaysInFirstWeek(DAYS_OF_A_WEEK);
        int week = calendar.get(Calendar.WEEK_OF_YEAR);
        int year = calendar.get(Calendar.YEAR);

        List<Date> result = new ArrayList<Date>();
        result.add(getDateOfYearWeek(year, week, Calendar.MONDAY));
        result.add(getDateOfYearWeek(year, week, Calendar.TUESDAY));
        result.add(getDateOfYearWeek(year, week, Calendar.WEDNESDAY));
        result.add(getDateOfYearWeek(year, week, Calendar.THURSDAY));
        result.add(getDateOfYearWeek(year, week, Calendar.FRIDAY));
        result.add(getDateOfYearWeek(year, week, Calendar.SATURDAY));
        result.add(getDateOfYearWeek(year, week, Calendar.SUNDAY));
        return result;
    }

    /**
     * 获取一年中某周,星期几的日期
     * @param yearNum
     * @param weekNum
     * @param dayOfWeek
     * @return
     */
    private static Date getDateOfYearWeek(int yearNum, int weekNum, int dayOfWeek) {
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        cal.setMinimalDaysInFirstWeek(DAYS_OF_A_WEEK);
        cal.set(Calendar.YEAR, yearNum);
        cal.set(Calendar.WEEK_OF_YEAR, weekNum);
        /*cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);*/
        return cal.getTime();
    }
    
    /**
     * 获取指定日期是一周的第几天,星期日是第一天
     * @param date
     * @return
     */
    public static int getDayOfWeek(Date date) {
        Calendar calendar = getCalendarFromDate(date);
        calendar.setMinimalDaysInFirstWeek(DAYS_OF_A_WEEK);
        return calendar.get(Calendar.DAY_OF_WEEK) - 1;
    }
    
    public static boolean beyondDate(Date beginDate, Date endDate, int i){
        long inter = endDate.getTime() - beginDate.getTime();
        return inter - (i * 1000 * 60 * 60 * 24) >= 0;
    }
    
    public static boolean beyondMinute(Date beginDate, Date endDate, int i){
        long inter = endDate.getTime() - beginDate.getTime();
        return Math.abs(inter) - (i * 1000 * 60) >= 0;
    }
    
    public static boolean beyondSecond(Date beginDate, Date endDate, int i){
        long inter = endDate.getTime() - beginDate.getTime();
        return Math.abs(inter) - (i * 1000) >= 0;
    }
    
    public static boolean isEndGtStart(Date StartDate, Date endDate) {
    	long inter = endDate.getTime() - StartDate.getTime();
        return inter < 0;
    }
    
    // 判断是否同一天
    public static boolean isSameDay(Date dateOne, Date dateTwo) {
    	boolean flag = true;
    	if (getYear(dateOne) != getYear(dateTwo)) {
    		flag = false;
    	} else if (getMonth(dateOne) != getMonth(dateTwo)) {
    		flag = false;
    	} else if (getDayOfMonth(dateOne) != getDayOfMonth(dateTwo)) {
    		flag = false;
    	}
    	return flag;
    }
    
    public static void main(String aaaaa[]){
        if(beyondMinute(
            parseDate("2010-08-23 13:40:00", FULL_DATE_TIME_FORMAT), 
            parseDate("2010-08-23 13:41:1", FULL_DATE_TIME_FORMAT), 
            70)){
            
            System.out.println("true");
        }else{
            System.out.println("false");
        }
    }
}