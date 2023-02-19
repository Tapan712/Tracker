package com.example.tracker.util;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.example.tracker.dbhelper.TrackerDB;
import com.example.tracker.entity.AppLoc;

import java.util.List;
import java.util.Locale;

public class LocaleUtil {
    public static String language;
    public static Locale locale;
    public static void setCurAppLocale(Context context,String lang,boolean updateFlag){
        Resources resources = context.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        language = lang;
        locale = new Locale(lang.toLowerCase());
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, displayMetrics);
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration,displayMetrics);
        if(updateFlag){
            TrackerDB db=TrackerDB.getDb(context);
            if (db.appLocDao().getAllLoc().isEmpty()){
                AppLoc lo = new AppLoc();
                lo.locLang = language;
                db.appLocDao().insertAll(lo);
            } else {
                AppLoc lo = db.appLocDao().getAllLoc().get(0);
                lo.locLang = language;
                db.appLocDao().update(lo);
            }
        }
    }

    public static String startupLocale(Context context) {
        //get last updated locale from db and call the setCurAppLocale method
        TrackerDB db=TrackerDB.getDb(context);
        List<AppLoc>locList = db.appLocDao().getAllLoc();
        String lang;
        if(!locList.isEmpty()){
            lang = locList.get(0).locLang;
            setCurAppLocale(context,locList.get(0).locLang,false);
        }else {
            lang = "en-us";
            setCurAppLocale(context,"en-us",true);
        }
        return lang;
    }
}
