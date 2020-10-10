package com.lambda.wallet.util;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class DateUtils {

    /**
     * The Juhe format pattern.
     */
    public static final String juhe_formatPattern = "MM/dd";
    /**
     * The Format pattern.
     */
    public static final String formatPattern = "yyyy-MM-dd";
    /**
     * The Date and time pattern.
     */
    public static final String DateAndTimePattern = "yyyy-MM-dd HH:mm:ss";
    /**
     * The Date and time pattern.
     */
    public static final String DateAndTimePatterm = "yyyy-MM-dd HH:mm";
    /**
     * The Date and time pattern.
     */
    public static final String DateAndTimePatternT = "yyyy-MM-dd'T'HH:mm:ss";
    /**
     * The Week of days.
     */
    static String[] weekOfDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

    private DateUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 获取当前日期
     *
     * @return juhe date
     */
    public static String getJuheDate() {
        SimpleDateFormat format = new SimpleDateFormat(juhe_formatPattern);
        String date = format.format(new Date());
        String front = date.substring(0, 2);
        String behind = date.substring(3, 5);
        if (front.startsWith("0")) {
            front = front.substring(1, front.length());
        }
        if (behind.startsWith("0")) {
            behind = behind.substring(1, behind.length());
        }
        return String.valueOf(front + "/" + behind);


    }

    /**
     * 获取当前日期
     *
     * @return current date
     */
    public static String getCurrentDate() {
        SimpleDateFormat format = new SimpleDateFormat(DateAndTimePattern);
        return format.format(new Date());

    }

    //获取时间
    public static String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat(DateAndTimePattern);
        return format.format(date);
    }

    //获取时间
    public static String getTime1(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat(formatPattern);
        return format.format(date);
    }

    /**
     * 获取时间差
     *
     * @param startTime the start time
     * @param endTime   the end time
     * @return the long
     */
    public static long getTimeDistance(long startTime, long endTime) {
        return endTime - startTime;
    }

    /**
     * 将时间转化成毫秒
     * 时间格式: yyyy-MM-dd HH:mm
     *
     * @param time
     * @return
     */
    public static Long timeStrToSecond(String time) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Long second = format.parse(time).getTime();
            return second;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1l;
    }

    /**
     * 获取指定日期是星期几
     * 参数为null时表示获取当前日期是星期日
     *
     * @param date the date
     * @return week of date
     */
    public static String getWeekOfDate(Date date) {

        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekOfDays[w];
    }

    /**
     * 根据毫秒数获取当前是星期几
     *
     * @param timeMiss the time miss
     * @return week of date
     */
    public static String getWeekOfDate(long timeMiss) {
        Date date = new Date(timeMiss);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekOfDays[w];
    }

    /**
     * 获取当前日期和时间
     *
     * @return current date and time
     */
    public static String getCurrentDateAndTime() {
        SimpleDateFormat format = new SimpleDateFormat(DateAndTimePattern);
        return format.format(new Date());
    }


    /**
     * 获取制定毫秒数之前的日期
     *
     * @param timeDiff the time diff
     * @return designated date
     */
    public static String getDesignatedDate(long timeDiff) {
        SimpleDateFormat format = new SimpleDateFormat(formatPattern);
        long nowTime = System.currentTimeMillis();
        long designTime = nowTime - timeDiff;
        return format.format(designTime);
    }

    /**
     * 获取前几天的日期
     *
     * @param count the count
     * @return the prefix date
     */
    public static String getPrefixDate(String count) {
        Calendar cal = Calendar.getInstance();
        int day = 0 - Integer.parseInt(count);
        cal.add(Calendar.DATE, day);   // int amount   代表天数
        Date datNew = cal.getTime();
        SimpleDateFormat format = new SimpleDateFormat(formatPattern);
        return format.format(datNew);
    }

    /**
     * 将格林威治时间字符串转换为yyyy-MM-dd HH:mm:ss字符串，JDK1.7以上版本支持该方法
     *
     * @param s the s
     * @return string
     */
    public static String iso8601Timestodata(String s) {
        String str = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.US);
            Date d = sdf.parse(new Date().toString());
            str = d.toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 传入Data类型日期，返回字符串类型时间（ISO8601标准时间）
     *
     * @param s the s
     * @return string
     */
    public static String getISO8601Timestamp(String s) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateAndTimePattern);
        Date date = null;
        try {
            date = simpleDateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat(DateAndTimePatternT);
        df.setTimeZone(tz);
        String nowAsISO = df.format(date);
        return nowAsISO;
    }

    /**
     * Date to stamp string.
     *
     * @param s the s
     * @return the string
     */
/*
     * 将时间转换为时间戳
     */
    public static long dateToStamp(String s) {
        long ts = 0;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateAndTimePattern);
            Date date = null;
            date = simpleDateFormat.parse(s);
            ts = date.getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return ts;
    }

    /**
     * Stamp to date string.
     *
     * @param s the s
     * @return the string
     */
/*
     * 将时间戳转换为时间
     */
    public static String stampToDate(int s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateAndTimePattern);
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * Gets expiration as date.
     *
     * @param dateStr the date str
     * @return the expiration as date
     */
    public static Date getExpirationAsDate(String dateStr) {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

    /**
     * 秒数转化为日期
     */
    public static String getDateFromSeconds(String seconds) {
        if (seconds == null)
            return " ";
        else {
            Date date = new Date();
            try {
                date.setTime(Long.parseLong(seconds) * 1000);
            } catch (NumberFormatException nfe) {

            }
            SimpleDateFormat sdf = new SimpleDateFormat(DateAndTimePattern);
            return sdf.format(date);
        }
    }
    /**
     * 毫数转化为日期
     */
    public static String getDateFromM(String m,String type) {//type代表转化的类型
        if (m == null)
            return " ";
        else {
            Date date = new Date();
            try {
                date.setTime(Long.parseLong(m ));
            } catch (NumberFormatException nfe) {

            }
            SimpleDateFormat sdf = new SimpleDateFormat(type);
            return sdf.format(date);
        }
    }
    /**
     * 日期字符串转换为data
     */
    public static Date string2Data(String time) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DateAndTimePattern);
        return sdf.parse(time);
    }

    /**
     * 时间戳转换成日期格式字符串
     *
     * @param seconds   精确到秒的字符串
     * @return
     */
    public static String timeStamp2Date(String seconds, String format) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        if (format == null || format.isEmpty()) {
            format = DateAndTimePattern;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds + "000")));
    }
    /**
     * 时间戳转换成日期格式字符串
     *
     * @param  ms  精确到毫秒的字符串
     * @return
     */
    public static String mstimeStamp2Date(String ms, String format) {
        if (ms == null || ms.isEmpty() || ms.equals("null")) {
            return "";
        }
        if (format == null || format.isEmpty()) {
            format = DateAndTimePattern;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(ms)));
    }
    /**
     * 毫秒转换为月数
     *
     * @param  ms  精确到毫秒的字符串
     * @return
     */
    public static String mstimeStamp2Month(String ms) {
        if (ms == null || ms.isEmpty() || ms.equals("null")) {
            return "";
        }
        String month = StringUtils.deletzero(BigDecimalUtil.divide(new BigDecimal(ms),new BigDecimal("2592000000000000"),2).toString());

        return month;
    }
    /**
     * 月数转毫秒
     *
     * @param  ms  精确到毫秒的字符串
     * @return
     */
    public static String month2MstimeStamp(String ms) {
        if (ms == null || ms.isEmpty() || ms.equals("null")) {
            return "";
        }
        String month = StringUtils.deletzero(BigDecimalUtil.multiply(new BigDecimal(ms),new BigDecimal("2592000000000000"),2).toString());

        return month;
    }
    public static String GTMToLocal(String GTMDate) {

        String dateTemp = GTMDate.split("T")[0];
        String timeTemp = GTMDate.split("T")[1];
        String convertString = dateTemp + " " + timeTemp;


        SimpleDateFormat format;
        format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        Date result_date;
        long result_time = 0;


        if (null == GTMDate) {
            return GTMDate;
        } else {
            try {
                format.setTimeZone(TimeZone.getTimeZone("GMT00:00"));
                result_date = format.parse(convertString);
                result_time = result_date.getTime();
                format.setTimeZone(TimeZone.getDefault());
                return format.format(result_time);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return GTMDate;
    }


    public static String currentDate(String time ){
        time = time.replace("Z"," UTC");//UTC是世界标准时间
        SimpleDateFormat format =new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
       Date resdate = null;
        try {
            resdate = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String wantTime = sf.format(resdate);
      return wantTime ;
    }




}
