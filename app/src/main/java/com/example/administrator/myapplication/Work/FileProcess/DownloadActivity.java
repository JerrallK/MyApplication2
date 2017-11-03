package com.example.administrator.myapplication.Work.FileProcess;

/**
 * Created by Administrator on 2017/9/30.
 */

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import android.widget.ImageView;
import android.widget.Toast;


import com.example.administrator.myapplication.MainActivity;
import com.example.administrator.myapplication.R;

public class DownloadActivity extends AppCompatActivity implements View.OnClickListener {
    String url;
    private EditText editText;
    private DownloadService.DownloadBinder downloadBinder;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            downloadBinder = (DownloadService.DownloadBinder) iBinder;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.download_layou);

        editText = (EditText) findViewById(R.id.download_url);

        ImageView backImgaeView=(ImageView)findViewById(R.id.downloadBack);
        backImgaeView.setOnClickListener(this);


        Button startBtn = (Button) findViewById(R.id.start_download);
        Button pauseBtn = (Button) findViewById(R.id.pause_download);
        Button cancel = (Button) findViewById(R.id.cancel_download);
//        Button delete = (Button) findViewById(R.id.delete_btn);

        startBtn.setOnClickListener(this);
        pauseBtn.setOnClickListener(this);
        cancel.setOnClickListener(this);
//        delete.setOnClickListener(this);
        Intent intent = new Intent(this, DownloadService.class);
        startService(intent);
        bindService(intent, connection, BIND_AUTO_CREATE);
        if (ContextCompat.checkSelfPermission(DownloadActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(DownloadActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

        }


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "您拒绝了访问权限", Toast.LENGTH_SHORT).show();
                    finish();

                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }

    @Override
    public void onClick(View view) {

        if (downloadBinder == null) {
            return;
        }
        switch (view.getId()) {
            case R.id.start_download:

                url = editText.getText().toString();
                //String url="https://dl.bandisoft.com/honeyview/HONEYVIEW-SETUP.EXE";
                downloadBinder.startDownload(url);
                break;
            case R.id.pause_download:
                downloadBinder.pauseDownload();
                break;
            case R.id.cancel_download:
                downloadBinder.cancelDownload();
                break;
//            case R.id.delete_btn:
//                downloadBinder.deleteDownload();
//                break;
            case R.id.downloadBack:
                onBackPressed();
            default:
                break;
        }

    }
}
