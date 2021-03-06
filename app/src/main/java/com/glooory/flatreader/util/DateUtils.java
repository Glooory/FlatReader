package com.glooory.flatreader.util;

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Glooory on 2016/9/30 0030 10:45.
 */

public class DateUtils {

    /**
     * 获取指定日期是星期几
     *
     * @param dt
     * @return
     */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    public static String getWeekOfDate(long time) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /**
     * 将日期转换为毫秒时长
     *
     * @param pattern
     * @param date
     * @return
     */
    public static long dateToMillis(String pattern, String date) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            Date dateTemp = sdf.parse(date);
            return dateTemp.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 转换时间格式
     *
     * @param currentPattern
     * @param tagetPattern
     * @param date
     * @return
     */
    public static String dateToPattern(String currentPattern, String tagetPattern, String date) {
        SimpleDateFormat sdf = new SimpleDateFormat(currentPattern);
        SimpleDateFormat sdfToString = new SimpleDateFormat(tagetPattern);
        try {
            Date dateTemp = sdf.parse(date);
            return sdfToString.format(dateTemp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 转换IT之家的时间格式
     * 如果是当天就只显示小时和分钟
     * 如果是当天之前就显示日期加小时和分钟
     *
     * @param date
     * @return
     */
    public static String dateToITPattern(Context context, String date) {
        String result = "";
        SimpleDateFormat sdfOriginal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdfCurrent = new SimpleDateFormat("yyyy-MM-dd");
        String currentData = sdfCurrent.format(new Date(System.currentTimeMillis()));
        try {
            if (currentData.equals(date.substring(0, 10))) {
                SimpleDateFormat sdfWithoutDay = new SimpleDateFormat("HH:mm");
                Date datewithoutDay = sdfOriginal.parse(date);
                result = sdfWithoutDay.format(datewithoutDay);
            } else {
                SimpleDateFormat sdfWithDay = new SimpleDateFormat("MM月dd日 HH:mm");
                Date dateWithDay = sdfOriginal.parse(date);
                result = sdfWithDay.format(dateWithDay);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

}
