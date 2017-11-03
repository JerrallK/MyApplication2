package com.example.administrator.myapplication.Work.FileProcess;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.myapplication.MainActivity;
import com.example.administrator.myapplication.R;

import java.io.File;

public class DownloadService extends Service {
    String TAG = "Download";
    private DownLoadTask downLoadTask;
    private String downloadUrl;


    private DownloadListener listener = new DownloadListener() {
        @Override
        public void onprogress(int progress) {
            getNotificationManager().notify(1, getNotification("Downloading...", progress));

        }

        @Override
        public void onSuccess() {
            downLoadTask = null;
            stopForeground(true);
            getNotificationManager().notify(1, getNotification("Download Success", -1));
            Toast.makeText(DownloadService.this, "Download Success", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailed() {
            downLoadTask = null;
            stopForeground(true);
            getNotificationManager().notify(1, getNotification("Download Failed", -1));
            Toast.makeText(DownloadService.this, "Download Failed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPause() {
            downLoadTask = null;
            Toast.makeText(DownloadService.this, "Paused", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onCanceled() {
            downLoadTask = null;
            stopForeground(true);
            Toast.makeText(DownloadService.this, "Canceled++", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onDelete() {
            downLoadTask = null;
            getNotificationManager().notify(1, getNotification("Download deleted", -1));
            Toast.makeText(DownloadService.this, "file has deleted", Toast.LENGTH_SHORT).show();
        }
    };


    public DownloadService() {
    }

    private DownloadBinder mBinder = new DownloadBinder();

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mBinder;
    }


    private NotificationManager getNotificationManager() {
        return (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    private Notification getNotification(String title, int progress) {
        Log.d(TAG, "getNotification: " + progress);
        Intent intent = new Intent(this, DownloadActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        builder.setContentIntent(pi);
        builder.setContentTitle(title);
        Log.d(TAG, "getNotification: " + progress);
        if (progress > 0) {
            builder.setContentText(progress + "%");
            //setProgress()第一个参数传入通知的最大进度，
            // 第二个参数传入当前进度，第三个参数表示是否使用进度条
            builder.setProgress(100, progress, false);
        }

        return builder.build();


    }

    class DownloadBinder extends Binder {
        public void deleteDownload() {
            if (downLoadTask != null) {
                downLoadTask.deleteDownload();

            }
            if (downloadUrl != null) {
                String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
                String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
                File file = new File(directory + fileName);
                if (file.exists()) {
                    file.delete();
                }
                getNotificationManager().cancel(1);
                stopForeground(true);
                Toast.makeText(DownloadService.this, "file has deleted", Toast.LENGTH_SHORT).show();

            }
        }

        public void startDownload(String url) {
            if (downLoadTask == null) {
                downloadUrl = url;
                downLoadTask = new DownLoadTask(listener);
                downLoadTask.execute(downloadUrl);
                startForeground(1, getNotification("DownLoading...", 0));
                Log.d(TAG, "startDownload:6666666666 ");
                //Toast.makeText(DownloadService.this, "DownLoading233", Toast.LENGTH_SHORT).show();
            }
        }

        public void pauseDownload() {
            if (downLoadTask != null) {
                downLoadTask.pausedDownload();
            }
        }

        public void cancelDownload() {
            if (downLoadTask != null) {
                downLoadTask.cancelDownload();
            } else {
                if (downloadUrl != null) {
                    String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
                    String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
                    File file = new File(directory + fileName);
                    if (file.exists()) {
                        file.delete();
                    }
                    getNotificationManager().cancel(1);
                    stopForeground(true);
                    Toast.makeText(DownloadService.this, "canceled--", Toast.LENGTH_SHORT).show();

                }
            }
        }
    }

}
