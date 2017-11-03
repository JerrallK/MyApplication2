package com.example.administrator.myapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2017/11/3.
 */

public class GetClockInDays {
    public final static String calendarFileName = "calendar_file";
    public static SharedPreferences sharedPreferences;
    public static Collection<CalendarDay> calendarDay = new ArrayList<>();
    public static Set<String> dateSet = new HashSet<>();
    public static String today;

    public final static String KEY = "days";
    public final static String TodayKEY = "today";

    public static Set<String> getDaySet(Context context) {
        sharedPreferences = context.getSharedPreferences(calendarFileName, Context.MODE_PRIVATE);
        return sharedPreferences.getStringSet(KEY, dateSet);
    }

    public static String getTodayDate(Context context) {
        sharedPreferences = context.getSharedPreferences(calendarFileName, Context.MODE_PRIVATE);
        return sharedPreferences.getString(TodayKEY, today);
    }

}

/**
 * //Retrieve the values
 * Set<String> set = myScores.getStringSet("key", null);
 * <p>
 * //Set the values
 * Set<String> set = new HashSet<String>();
 * set.addAll(listOfExistingScores);
 * scoreEditor.putStringSet("key", set);
 * scoreEditor.commit();
 */
