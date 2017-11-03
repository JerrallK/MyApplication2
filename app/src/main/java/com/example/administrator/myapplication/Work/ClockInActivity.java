package com.example.administrator.myapplication.Work;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.utils.GetClockInDays;
import com.example.administrator.myapplication.utils.GetTime;
import com.example.administrator.myapplication.utils.SetFullScreen;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by Administrator on 2017/11/1.
 */

public class ClockInActivity extends Activity implements View.OnClickListener, GetTime.timeCallBack {
    private TextView today_time;
    private TextView times,clockInStatus;
    private FrameLayout clockInButton;
    private com.prolificinteractive.materialcalendarview.MaterialCalendarView calendarView;
    private SharedPreferences.Editor editor;
    private Collection<CalendarDay> calendarDay = new ArrayList<>();
    private Collection<CalendarDay> calendarDayToday = new ArrayList<>();
    private EventDecorator decorator, todayDecorate;
    private Set<String> dateSet;
    private String todayDate;
    private boolean isClockIn;
    private LinearLayout today;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String time = (String) msg.obj;
            times.setText(time);

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clockin_layout);
        SetFullScreen.getInstance().setFullscreen(getWindow());
        initView();
    }

    private void initView() {

        today_time = findViewById(R.id.today_clock_in_time);
        times = findViewById(R.id.time);
        calendarView = findViewById(R.id.calendar_view);
        clockInButton = findViewById(R.id.clock_in_button);
        clockInButton.setOnClickListener(this);
        today = findViewById(R.id.today);
        clockInStatus=findViewById(R.id.clock_status);


        final SimpleDateFormat clockFormat = new SimpleDateFormat("yyyy,MM,dd");
        todayDate = clockFormat.format(System.currentTimeMillis());





        editor = getSharedPreferences(GetClockInDays.calendarFileName, MODE_PRIVATE).edit();

        dateSet = GetClockInDays.getDaySet(getApplicationContext());

        calendarDay = initCalendarDay(dateSet);
        if (calendarDay.size() != 0) {
            decorator = new EventDecorator(Color.parseColor("#F85D6C"), calendarDay);
            calendarView.addDecorator(decorator);
        }

        isClockIn = isClockIn(dateSet, todayDate);

        if (isClockIn) {
            today.setVisibility(View.VISIBLE);
            today_time.setText(GetClockInDays.getTodayDate(getApplicationContext()));
            clockInStatus.setText(getResources().getString(R.string.is_clock_in2));
        }

        GetTime.getInstance().setLeave(false);
        GetTime.getInstance().setTimeCallBack(this);
        GetTime.getInstance().setAllTime();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clock_in_button:
                if (!isClockIn) {
                    clockInStatus.setText(getResources().getString(R.string.is_clock_in2));
                    final SimpleDateFormat clockFormat2 = new SimpleDateFormat("k:mm:ss ");
                    String clockInTime= clockFormat2.format(System.currentTimeMillis());
                    editor.putString(GetClockInDays.TodayKEY, clockInTime);
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.clock_in_success), Toast.LENGTH_SHORT).show();
                    today_time.setText(clockInTime);
                    times.setVisibility(View.INVISIBLE);
                    today.setVisibility(View.VISIBLE);
                    GetTime.getInstance().setLeave(true);
                    calendarDayToday.add(CalendarDay.today());
                    todayDecorate = new EventDecorator(Color.parseColor("#F85D6C"), calendarDayToday);
                    calendarView.addDecorator(todayDecorate);
                    addDate();
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.is_clock_in), Toast.LENGTH_SHORT).show();
                }

                break;
            default:
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        GetTime.getInstance().setLeave(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        GetTime.getInstance().setLeave(true);
    }


    @Override
    public void getTime(String strings) {
        Message message = handler.obtainMessage();
        message.obj = strings;
        handler.sendMessage(message);
        Log.d("getTime", "getTime: " + strings);
    }


    /**
     * Decorate several days with a dot
     */
    public class EventDecorator implements DayViewDecorator {

        private int color;
        private HashSet<CalendarDay> dates;

        public EventDecorator(int color, Collection<CalendarDay> dates) {
            this.color = color;
            this.dates = new HashSet<>(dates);
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return dates.contains(day);
        }


        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new DotSpan(20, color));
        }
    }

    private void addDate() {
        final SimpleDateFormat clockFormat = new SimpleDateFormat("yyyy,MM,dd");
        String date = clockFormat.format(System.currentTimeMillis());
        GetClockInDays.dateSet.add(date);
        editor.putStringSet(GetClockInDays.KEY, GetClockInDays.dateSet);
        editor.commit();
    }

    private Collection<CalendarDay> initCalendarDay(Set<String> dateSet) {
        Collection<CalendarDay> days = new ArrayList<>();
        if (dateSet.size() != 0) {
            for (String s : dateSet) {
                Log.d("initCalendarDay", "initCalendarDay: ="+s);
                String dates = s;//iter.next().toString();
                String[] strings = dates.split(",");
                int[] ints = new int[3];
                for (int j = 0; j < 3; j++) {
                    ints[j] = Integer.parseInt(strings[j]);
                }
                days.add(CalendarDay.from(ints[0], ints[1] - 1, ints[2]));
            }
        }
        return days;

    }

    private boolean isClockIn(Set<String> dateSet, String today) {

        if (dateSet.size() != 0) {
            for (String s : dateSet) {
                if (s.equals(today)) {
                    times.setVisibility(View.INVISIBLE);
                    return true;
                } else {
                    return false;
                }
            }
            return false;
        } else {
            return false;
        }
    }

}
