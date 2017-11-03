package com.example.administrator.myapplication.Work.FileProcess;

/**
 * Created by Administrator on 2017/9/18.
 */

//一般写程序是你调用系统的API，如果把关系反过来，
// 你写一个函数，让系统调用你的函数，那就是回调了，那个被系统调用的函数就是回调函数。
public interface DownloadListener {
    void onprogress(int progerss);
    void onSuccess();
    void onFailed();
    void onPause();
    void onCanceled();
    void onDelete();
}
