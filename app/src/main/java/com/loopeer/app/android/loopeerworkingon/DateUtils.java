package com.loopeer.app.android.loopeerworkingon;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtils {

    public static final int SECONDS_IN_DAY = 60 * 60 * 24;
    public static final long MILLIS_IN_DAY = 1000L * SECONDS_IN_DAY;

    private static String[] WEEK_LARGE = new String[]{"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
    private static GregorianCalendar calendar = new GregorianCalendar();

    public static String getStandardDate(Long time) {
        long timeSub = getNow() - time;
        long minute = (long) Math.ceil(timeSub / 60 / 1000.0f);
        if (minute >= 2) {
            if (getMillis(getDate(time)) == getMillis(getTodayData())) {
                return getTimeSimple2(time);
            }
            if (getMillis(getDate(time)) == getMillis(getYesData())) {
                return "昨天";
            }
            if (getMillis(getDate(time)) > getMillis(getWeekStartData())) {
                return WEEK_LARGE[getWeek(time)];
            } else {
                return formateDateYmd3(time);
            }
        } else {
            return "刚刚";
        }
    }

    public static final String getCallLogDetailDate(long time) {
        SimpleDateFormat sdf;
        if (getYear(time) < getYear(getNow()))
            sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        else
            sdf = new SimpleDateFormat("MM月dd日 HH:mm");
        return sdf.format(new Date(time));
    }

    public static String getDetailStandardDate(long milliseconds) {
        long timeSub = getNow() - milliseconds;
        long minute = (long) Math.ceil(timeSub / 60 / 1000.0f);
        if (minute >= 2) {
            if (getMillis(getDate(milliseconds)) == getMillis(getTodayData())) {
                return getTimeSimple2(milliseconds);
            }
            if (getMillis(getDate(milliseconds)) == getMillis(getYesData())) {
                return "昨天";
            }
            if (getYear(milliseconds) < getYear(getNow())) {
                return formateDateYmd3(milliseconds);
            }
            if (getMillis(getDate(milliseconds)) > getMillis(getWeekStartData())) {
                return WEEK_LARGE[getWeek(milliseconds)];
            }
            return formateDateYmd3(milliseconds);
        } else {
            return getTimeSimple2(milliseconds);
        }
    }

    public static int getWeek(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return calendar.get(Calendar.DAY_OF_WEEK) - 1;
    }

    @SuppressLint("SimpleDateFormat")
    public static String formateDateYmd3(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd");
        return sdf.format(new Date(time));
    }

    public static String getTimeSimple2(Long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.SIMPLIFIED_CHINESE);
        return sdf.format(new Date(time));
    }

    public static String getMonthDay(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日", Locale.SIMPLIFIED_CHINESE);
        return sdf.format(new Date(time));
    }

    public static String getYearDay(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日", Locale.SIMPLIFIED_CHINESE);
        return sdf.format(new Date(time));
    }

    // 提供“yyyy-mm-dd”形式的字符串到毫秒的转换
    public static long getMillis(String dateString) {
        String[] date = dateString.split("-");
        return getMillis(date[0], date[1], date[2]);

    }

    // 根据输入的年、月、日，转换成毫秒表示的时间
    public static long getMillis(int year, int month, int day) {
        GregorianCalendar calendar = new GregorianCalendar(year, month, day);
        return calendar.getTimeInMillis();
    }

    // 根据输入的年、月、日，转换成毫秒表示的时间
    public static long getMillis(String yearString, String monthString,
                                 String dayString) {
        int year = Integer.parseInt(yearString);
        int month = Integer.parseInt(monthString) - 1;
        int day = Integer.parseInt(dayString);
        return getMillis(year, month, day);
    }

    // 获得当前时间的毫秒表示
    public static long getNow() {
        GregorianCalendar now = new GregorianCalendar();
        return now.getTimeInMillis();
    }

    // 根据输入的毫秒数，获得日期字符串
    public static String getDate(long millis) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.SIMPLIFIED_CHINESE);
        return sdf.format(new Date(millis));
    }

    // 根据输入的毫秒数，获得年份
    public static int getYear(long millis) {
        calendar.setTimeInMillis(millis);
        return calendar.get(Calendar.YEAR);
    }

    // 根据输入的毫秒数，获得月份
    public static int getMonth(long millis) {
        calendar.setTimeInMillis(millis);
        return calendar.get(Calendar.MONTH);
    }

    // 根据输入的毫秒数，获得日期
    public static int getDay(long millis) {
        calendar.setTimeInMillis(millis);
        return calendar.get(Calendar.DATE);
    }

    // 根据输入的毫秒数，获得小时
    public static int getHour(long millis) {
        calendar.setTimeInMillis(millis);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    // 根据输入的毫秒数，获得分钟
    public static int getMinute(long millis) {
        calendar.setTimeInMillis(millis);
        return calendar.get(Calendar.MINUTE);
    }

    // 根据输入的毫秒数，获得秒
    public static int getSecond(long millis) {
        calendar.setTimeInMillis(millis);
        return calendar.get(Calendar.SECOND);
    }

    // 获得今天日期
    public static String getTodayData() {
        return getDate(getNow());
    }

    // 获得明天日期
    public static String getTomoData() {
        // 86400000为一天的毫秒数
        return getDate(getNow() + 86400000);
    }

    // 获得后天日期
    public static String getTheDayData() {
        return getDate(getNow() + 86400000 + 86400000);
    }

    // 获得昨天日期
    public static String getYesData() {
        return getDate(getNow() - 86400000L);
    }

    public static String getWeekStartData() {
        int currentWeek = getWeek(getNow());
//        return getDate(getNow() - 86400000L * 6);
        return getDate(getNow() - 86400000L * currentWeek);
    }

    // 获得前天日期
    public static String getBeforeYesData() {
        return getDate(getNow() - 86400000L - 86400000L);
    }

    /**
     * 获取今天时间具体内容
     *
     * @return
     */
    public static String StringData() {
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String mYear = String.valueOf(c.get(Calendar.YEAR)); // 获取当前年份
        String mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
        String mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
        String mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        if ("1".equals(mWay)) {
            mWay = "日";
        } else if ("2".equals(mWay)) {
            mWay = "一";
        } else if ("3".equals(mWay)) {
            mWay = "二";
        } else if ("4".equals(mWay)) {
            mWay = "三";
        } else if ("5".equals(mWay)) {
            mWay = "四";
        } else if ("6".equals(mWay)) {
            mWay = "五";
        } else if ("7".equals(mWay)) {
            mWay = "六";
        }
        return mYear + "年" + mMonth + "月" + mDay + "日" + " 星期" + mWay;
    }

    /**
     * 获取类似今天、昨天的简单名称
     *
     * @param date 格式为 20xx-xx-xx
     * @return
     */
    public static String getCustomStr(String date) {
        if (getMillis(date) == getMillis(getBeforeYesData())) {
            return "前天";
        } else if (getMillis(date) == getMillis(getYesData())) {
            return "昨天";
        } else if (getMillis(date) == getMillis(getTodayData())) {
            return "今天";
        } else if (getMillis(date) == getMillis(getTomoData())) {
            return "明天";
        } else if (getMillis(date) == getMillis(getTheDayData())) {
            return "后天";
        } else {
            return "星期" + getWeek((getMillis(date)));
        }
    }

    public static final String formatDate24(long milliseconds) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(new Date(milliseconds));
    }

    public static final String formatDate12(long milliseconds) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        return sdf.format(new Date(milliseconds));
    }

    //单位秒
    public static final String formatMDHMDate(long seconds) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
        return sdf.format(new Date(seconds * 1000));
    }

    public static String formatTime(Long triggerTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return format.format(new Date(triggerTime));
    }

    public static String formatDuration(long elapsedSeconds) {
        long minutes;
        long seconds;

        if (elapsedSeconds >= 3600) {
            long hours = elapsedSeconds / 3600;
            minutes = (elapsedSeconds % 3600) / 60;
            seconds = elapsedSeconds - hours * 3600 - minutes * 60;
            return hours + "小时" + minutes + "分" + seconds + "秒";
        }

        if (elapsedSeconds >= 60) {
            minutes = elapsedSeconds / 60;
            elapsedSeconds -= minutes * 60;
            return minutes + "分" + elapsedSeconds + "秒";
        }

        return elapsedSeconds + "秒";
    }

    public static boolean isSameDayOfMillis(final long ms1, final long ms2) {
        final long interval = ms1 - ms2;
        return interval < MILLIS_IN_DAY
                && interval > -1L * MILLIS_IN_DAY
                && toDay(ms1) == toDay(ms2);
    }

    private static long toDay(long millis) {
        return (millis + TimeZone.getDefault().getOffset(millis)) / MILLIS_IN_DAY;
    }

}
