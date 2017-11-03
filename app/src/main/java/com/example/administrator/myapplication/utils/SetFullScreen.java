package com.example.administrator.myapplication.utils;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;

/**
 * Created by Administrator on 2017/10/31.
 */

public class SetFullScreen {

    public static SetFullScreen setFullScreen;

    public static SetFullScreen getInstance() {
        synchronized (SetFullScreen.class) {
            if (setFullScreen == null) {
                setFullScreen = new SetFullScreen();
            }
            return setFullScreen;
        }

    }


    public void setFullscreen(Window window)

    {

        if (Build.VERSION.SDK_INT >= 21)

        {
            View decorView = window.getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }


}
