package com.example.hau.weatherapp.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by HAU on 9/5/2015.
 */
public class Time {
    /**
     * Function get Day of Week
     *
     * @param unixSeconds
     * @return
     */
    public static String getDayofWeek(long unixSeconds) {
        String dayofweek = "";
        Date date = new Date(unixSeconds * 1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formatdate = sdf.format(date);
        String result[] = formatdate.split("[-]");
        int year = Integer.parseInt(result[0]);
        int month = Integer.parseInt(result[1]);
        int day = Integer.parseInt(result[2]);

        String[] strDays = new String[]{"Chủ Nhật", "Thứ Hai", "Thứ Ba", "Thứ Tư", "Thứ Năm",
                "Thứ Sáu", "Thứ Bảy"};
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, (month-1), day);
        dayofweek =strDays[calendar.get(Calendar.DAY_OF_WEEK) -1];
        return dayofweek;
    }

    /**
     * Function convert time
     *
     * @param unixSeconds
     * @return
     */
    public static String convertTime(long unixSeconds) {
        Date date = new Date(unixSeconds * 1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa");
        String time = sdf.format(date);
        return time;
    }

    /**
     * Function convert date
     *
     * @param unixSeconds
     * @return
     */
    public static String convertDate(long unixSeconds) {
        Date date = new Date(unixSeconds * 1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String time = sdf.format(date);
        return time;
    }
    /**
     * Function convert date format dd-MM
     *
     * @param unixSeconds
     * @return
     */
    public static String convertDateItem(long unixSeconds) {
        Date date = new Date(unixSeconds * 1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM");
        String time = sdf.format(date);
        return time;
    }
}
