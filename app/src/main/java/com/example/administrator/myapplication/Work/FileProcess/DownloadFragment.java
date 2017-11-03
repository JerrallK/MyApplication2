package com.example.administrator.myapplication.Work.FileProcess;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.myapplication.MainActivity;
import com.example.administrator.myapplication.R;

/**
 * Created by Administrator on 2017/9/30.
 */

public class DownloadFragment extends Fragment implements View.OnClickListener {
    String TAG="DownloadFragment ";

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.download_layou, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        editText = (EditText) getActivity().findViewById(R.id.download_url);


        Button startBtn = (Button) getActivity().findViewById(R.id.start_download);
        Button pauseBtn = (Button) getActivity().findViewById(R.id.pause_download);
        Button cancel = (Button) getActivity().findViewById(R.id.cancel_download);
//        Button delete = (Button) getActivity().findViewById(R.id.delete_btn);

        ImageView back = getActivity().findViewById(R.id.downloadBack);
        back.setOnClickListener(this);


        startBtn.setOnClickListener(this);
        pauseBtn.setOnClickListener(this);
        cancel.setOnClickListener(this);
//        delete.setOnClickListener(this);
        Intent intent = new Intent(getContext(), DownloadService.class);
        getActivity().startService(intent);
        getActivity().bindService(intent, connection, Context.BIND_AUTO_CREATE);
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

        }
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
                Log.d(TAG, "start_download: "+url);
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
                getActivity().onBackPressed();
                break;
            default:
                break;
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unbindService(connection);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getActivity(), "您拒绝了访问权限", Toast.LENGTH_SHORT).show();
                    getActivity().onBackPressed();

                }
                break;
            default:
                break;
        }
    }
}
