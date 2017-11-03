package com.example.administrator.myapplication.Work.FileProcess;

import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by Administrator on 2017/9/19.
 */

public class DownLoadTask extends AsyncTask<String, Integer, Integer> {

    public static final int TYPE_SUCCESS = 0;
    public static final int TYPE_FAILED = 1;
    public static final int TYPE_PAUSED = 2;
    public static final int TYPE_CANCELED = 3;
    public static final int TYPE_DELETE = 4;


    private DownloadListener listener;

    private boolean isCanceled = false;

    private boolean isPaused = false;

    private boolean isDelete = false;


    private int lastProgress;

    public DownLoadTask(DownloadListener listener) {
        this.listener = listener;
    }

    @Override
    protected Integer doInBackground(String... params) {
        InputStream is = null;
        RandomAccessFile saveFile = null;
        File file = null;
        try {
            long downloadedlength = 0;
            String downloadUrl = params[0];
            String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
            String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
            file = new File(directory + fileName);
            if (file.exists()) {
                downloadedlength = file.length();
            }

            long contentLength = getContentLength(downloadUrl);
            if (contentLength == 0) {
                return TYPE_FAILED;
            } else if (contentLength == downloadedlength) {
                return TYPE_SUCCESS;
            }
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .addHeader("RANGE", "bytes=" + downloadedlength + "-")
                    .url(downloadUrl)
                    .build();
            Response response = client.newCall(request).execute();
            if (response != null) {
                is = response.body().byteStream();
                saveFile = new RandomAccessFile(file, "rw");
                saveFile.seek(downloadedlength);
                byte[] b = new byte[1024];//自定义的字节缓冲区
                int total = 0;
                int len;
                while ((len = is.read(b)) != -1) {//返回的是读取的字节数，如读完或读满，则返回-1
                    if (isCanceled) {
                        return TYPE_CANCELED;
                    } else if (isPaused) {
                        return TYPE_PAUSED;
                    } else {
                        total += len;
                        saveFile.write(b, 0, len);
                        int progress = (int) ((total + downloadedlength) * 100 / contentLength);

                        //publishProgress(progress)这句很重要！！！
                        publishProgress(progress);

                    }

                }
                response.body().close();
                return TYPE_SUCCESS;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (saveFile != null) {
                    saveFile.close();
                }
                if (isCanceled && file != null) {
                    file.delete();
                }
                if (isDelete && file != null) {
                    file.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return TYPE_FAILED;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        int progress = values[0];
        if (progress > lastProgress) ;
        {
            listener.onprogress(progress);
            lastProgress = progress;
        }

    }

    @Override
    protected void onPostExecute(Integer status) {
        switch (status) {
            case TYPE_SUCCESS:
                listener.onSuccess();
                break;
            case TYPE_FAILED:
                listener.onFailed();
                break;
            case TYPE_PAUSED:
                listener.onPause();
                break;
            case TYPE_CANCELED:
                listener.onCanceled();
                break;
            case TYPE_DELETE:
                listener.onDelete();
            default:
                break;

        }
    }

    public void pausedDownload() {
        isPaused = true;
    }

    public void cancelDownload() {
        isCanceled = true;
    }

    public void deleteDownload() {
        isDelete = true;
    }

    private long getContentLength(String downloadUrl) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(downloadUrl)
                .build();
        Response response = client.newCall(request).execute();
        if (response != null && response.isSuccessful()) {
            long contentlength = response.body().contentLength();
            response.body().close();
            return contentlength;
        }
        return 0;

    }
}
