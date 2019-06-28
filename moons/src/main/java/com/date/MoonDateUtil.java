package com.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MoonDateUtil {
    /**
     * //이번달 가져오기  ( 2011/10)
     * @return
     */
    public static String getNowMonthStr(){

        String thisMonth = new SimpleDateFormat("yyyy/MM" , Locale.getDefault()).format(Calendar.getInstance().getTime());

        return thisMonth;
    }

    /**
     * 이번년도 가져오기
     * @return
     */
    public static String getNowYearStr(){

        String thisYear = new SimpleDateFormat("yyyy" , Locale.getDefault()).format(Calendar.getInstance().getTime());

        return thisYear;
    }

    /**
     * 이번달 가져오기
     * @return
     */
    public static String getOnlyNowMonthStr(){

        String thisMonth = new SimpleDateFormat("MM" , Locale.getDefault()).format(Calendar.getInstance().getTime());

        return thisMonth;
    }
    /**
     * 오늘날짜 가져오기(2011.10.24)
     * @return
     */
    public static String getTodayDateStr(){
        return new SimpleDateFormat("yyyy/MM/dd" , Locale.getDefault()).format(Calendar.getInstance().getTime());
    }

    /**
     * String을 Date로 변환 yyyy/MM/dd 문자열
     * @return
     */
    public static Date getDate(String stringDate) throws Exception
    {
        return MoonDateUtil.getDate(stringDate, "yyyy/MM/dd");
    }

    /**
     * String을 Date로 변환 yyyy/MM/dd hh:mm 문자열로 반환
     * @return
     */
    public static Date getDateTime(String stringDate) throws Exception
    {
        Date aa = MoonDateUtil.getDate(stringDate, "yyyy/MM/dd hh:mm:ss");
        return aa;
    }

    /**
     * String을 Date로 변환
     * @param stringDate : 문자열
     * @param formatStr : 문자열의 날짜포멧
     * @return
     */
    public static Date getDate(String stringDate, String formatStr) throws Exception
    {
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(formatStr);
        return format.parse(stringDate);
    }

    /**
     * 다음달로 조정한 Date를 리턴
     * ex. DateUtility.getAdjustedMonth(1) => 다음 달
     * @param adjustMonth : 조정월
     * @return Date
     */
    public static Date getAdjustedMonth(int adjustMonth)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, adjustMonth);

        return calendar.getTime();
    }

    /**
     * 년도를 조정한 Date를 리턴
     * ex. DateUtility.getAdjustedMonth(1) => 다음 달
     * @param adjustMonth : 조정월
     * @return Date
     */
    public static Date getAdjustedYear(int adjustYear)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, adjustYear);

        return calendar.getTime();
    }

    /**
     * 일자를 조정한 Date를 리턴
     * ex. DateUtility.getAdjustedDate(1) => 다음 날
     * @param adjustDate : 조정 일자
     * @return
     */
    public static Date getAdjustedDate(int adjustDate)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, adjustDate);

        return calendar.getTime();
    }

    /**
     * 일자를 조정한 Date를 리턴(시분까지 입력)
     * ex. DateUtility.getAdjustedDate(1) => 다음 날
     * @param adjustDate : 조정 일자
     * @param hour : 지정 시
     * @param minute : 지정 분
     * @return
     */
    public static Date getAdjustedDateHourMin(int adjustDate, int hour, int minute)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, adjustDate);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        return calendar.getTime();
    }

    /**
     * 일자를 조정한 Date를 리턴(문자열yyyy-MM-dd)
     * ex. DateUtility.getAdjustedDate(1) => 다음 날
     * @param adjustDate : 조정 일자
     * @return
     */
    public static String getAdjustedDateStr(int adjustDate)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, adjustDate);

        return new SimpleDateFormat("yyyy/MM/dd" , Locale.getDefault()).format(calendar.getTime());
    }

    /**
     * 해당월의 마지막 날
     * ex. DateUtility.getAdjustedDate(1) => 다음 날
     * @param adjustDate : 조정 일자
     * @return
     */
    public static String getLastDayOfMonthStr(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String lastDateStr = calendar.getMaximum(Calendar.DAY_OF_MONTH)+"";
        if(lastDateStr.length() == 1) lastDateStr = "0" + lastDateStr;
        return lastDateStr;
    }

    /**
     * 해당월의 마지막 날
     * ex. DateUtility.getAdjustedDate(1) => 다음 날
     * @param adjustDate : 조정 일자
     * @return
     */
    public static int getLastDayOfMonth(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 해당월의 주의 개수
     * ex. DateUtility.getWeekCountOfMonth(date)
     * @return
     */
    public static int getWeekCountOfMonth(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        date.setDate(MoonDateUtil.getLastDayOfMonth(date));
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_MONTH);
    }

    /**
     * 해당월의 주의 개수
     * ex. DateUtility.getWeekCountOfMonth("2016-05-07") => 5
     * @param adjustDate : 조정 일자
     * @return
     */
    public static int getWeekCountOfMonthStr(String stringDate) throws Exception
    {
        Date date = MoonDateUtil.getDate(stringDate);

        return MoonDateUtil.getWeekCountOfMonth(date);
    }

    public static Date getDatetoString(String stringDate){

        Date rDate = null;

        try {
            SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy/MM/dd");
            rDate = format.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return rDate;
    }
    /**
     * DATE를 FORMAT 스트링으로 변환
     * @param date : 날짜
     * @param formatStr : 문자열의 날짜포멧
     * @return
     */
    public static String getDateStringByFormmat(Date date , String formatStr) throws Exception
    {
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(formatStr);
        return format.format(date);
    }

    /**
     * 두 Date간의 날짜차이 구하기
     * @param startDate : 작은날짜
     * @param endDate : 큰날짜
     * @return int(날짜차이)
     */
    public static int getDiffDayCountTwoDate(Date startDate , Date endDate) throws Exception
    {
        Calendar startCal = Calendar.getInstance();
        Calendar endCal   = Calendar.getInstance();

        startCal.setTime(startDate);
        endCal.setTime(endDate);

        long diffMillis = endCal.getTimeInMillis() - startCal.getTimeInMillis();

        int diffDayCount = (int)(diffMillis/(24*60*60*1000));

        return diffDayCount;
    }

    /**
     * 일자를 Date로 조정후 FORMAT 스트링으로 변환 리턴
     * ex. DateUtility.getAdjustedDateToString(new Date(),"yyyy/MM/dd",1) => 다음 날
     * @param date : 조정 시점 일자
     * @param formatStr : 리턴 스트링 포멧
     * @param adjustDate : 조정 일자
     */
    public static String getAdjustedDateToString(Date date, String formatStr, int adjustDate)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime (date);
        calendar.add(Calendar.DATE, adjustDate);
        Date newDate = calendar.getTime();

        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(formatStr);
        return format.format(newDate);
    }
}
