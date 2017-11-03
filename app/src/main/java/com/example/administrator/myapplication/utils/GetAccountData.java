package com.example.administrator.myapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2017/10/19.
 */

public class GetAccountData {


    public final static String KEY_Password= "PASSWORD";
    public final static String KEY_UserName="USERNAME";
    private final static String DEFAULT_USERNAME="11070367";
    private final static String DEFAULT_PASSWORD="vivo";

    public final static String FileName="password_file";


    private static SharedPreferences preferences;

    public static String getPassword(Context context){
        preferences = context.getSharedPreferences(FileName, Context.MODE_PRIVATE);//FileName is where preference file stored  http://www.jb51.net/article/36151.htm
        return preferences.getString(KEY_Password,DEFAULT_PASSWORD);
    }

    public static String getUserName(Context context){
        preferences=context.getSharedPreferences(FileName,Context.MODE_PRIVATE);
        return preferences.getString(KEY_UserName,DEFAULT_USERNAME);
    }


}
