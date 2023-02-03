package com.example.tracker.util;

import android.text.format.DateFormat;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static Date getDate(String dt) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.parse(dt);
    }
    public static Long getLongDate(String dt)throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date = sdf.parse(dt);
        assert date != null;
        Log.i("String to Long",dt+" => "+date.getTime()+"");
        return date.getTime();
    }
    public static String getStrDateFromLongDate(Long dt){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date(dt);
        Log.i("Long to String",dt+" => "+sdf.format(date)+"");
        return sdf.format(date);
    }
    public static String getNextDateString(Date dt) {
        String _day = (String) DateFormat.format("dd",dt);
        String _mon = (String) DateFormat.format("MM",dt);
        String _year = (String) DateFormat.format("yyyy",dt);
        int _intMon = Integer.parseInt(_mon);
        int _intDay = Integer.parseInt(_day)+1;
        return new StringBuilder().append(_intDay).append("/").append(_intMon).append("/").append(_year).toString();
    }

    public static String getDateString(Date dt){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(dt);
    }
    public static String getLastMonStartDate(Date currentDate){
        String _mon = (String) DateFormat.format("MM",currentDate);
        String _year = (String) DateFormat.format("yyyy",currentDate);
        Integer _intMon = Integer.parseInt(_mon);
        Integer _lastMon;
        if(_intMon>1){
            _lastMon = _intMon-1;
        } else {
            _lastMon = 12;
            _year = (Integer.parseInt(_year)-1)+"";
        }
        return new StringBuilder().append("1/").append(_lastMon).append("/").append(_year).toString();
    }
    public static String getLastMonEndDate(Date currentDate){
        String _mon = (String) DateFormat.format("MM",currentDate);
        String _year = (String) DateFormat.format("yyyy",currentDate);
        Integer _intMon = Integer.parseInt(_mon);
        Integer _lastMon;
        if(_intMon>1){
            _lastMon = _intMon-1;
        } else {
            _lastMon = 12;
            _year = (Integer.parseInt(_year)-1)+"";
        }
        return new StringBuilder().append("31/").append(_lastMon).append("/").append(_year).toString();
    }
    public static String getCurYearStartDate(Date currentDate){
        String _year = (String) DateFormat.format("yyyy",currentDate);
        return new StringBuilder().append("1/1/").append(_year).toString();
    }
    public static String getCurMonFirstDate(Date currentDate){
        String _year = (String) DateFormat.format("yyyy",currentDate);
        String _mon = (String) DateFormat.format("MM",currentDate);
        int _intMon = Integer.parseInt(_mon);
        return new StringBuilder().append("1/").append(_intMon).append("/").append(_year).toString();
    }
}
