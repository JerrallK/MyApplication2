package com.example.administrator.myapplication.utils;

import android.util.Log;
import android.widget.TextView;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/11/2.
 */

public class GetTime {
    private Calendar calendar = Calendar.getInstance();
    public static GetTime getTime;
    private boolean isLeave;
    private timeCallBack callBack;

    public void setLeave(boolean leave) {
        isLeave = leave;
    }


    public static GetTime getInstance() {
        synchronized (GetTime.class) {
            if (getTime == null) {
                return getTime = new GetTime();
            }
            return getTime;
        }
    }


    public void setAllTime() {

        final SimpleDateFormat clockFormat = new SimpleDateFormat("HH:mm:ss");
        calendar = Calendar.getInstance();

        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!isLeave) {
                    String time = clockFormat.format(System.currentTimeMillis());
                    callBack.getTime(time);
                }else cancel();

            }
        }, 0,1000);

    }

    public interface timeCallBack {
        void getTime(String strings);
    }

    public void setTimeCallBack(timeCallBack callBack) {
        this.callBack = callBack;
    }

}
