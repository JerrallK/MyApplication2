package com.example.administrator.myapplication.Work;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.example.administrator.myapplication.MainActivity;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.Work.FileProcess.DownloadActivity;
import com.example.administrator.myapplication.Work.FileProcess.DownloadFragment;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/5.
 */

public class Work_Fragment extends Fragment {

    String TAG="Work_Fragment";
    private GridView gridView;
    private List<Map<String, Object>> dataList;
    private SimpleAdapter simpleAdapter;
    int mainLayout =R.id.mainLayou;
    private int[] icon = {R.drawable.ic_action_music, R.drawable.ic_action_punch_clock
            , R.drawable.ic_action_file, R.drawable.ic_action_bus, R.drawable.ic_action_map, R.drawable.ic_action_addbox};
    private String[] iconName = {"音乐", "打卡", "文件", "班车", "地图", ""};


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.app_layout, container, false);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        gridView = (GridView) getActivity().findViewById(R.id.appGridview);
        dataList = new ArrayList<Map<String, Object>>();

        getDataList();
        String[] from = {"image", "text"};
        int[] to = {R.id.gridViewImg, R.id.gridViewText};
        simpleAdapter = new SimpleAdapter(getContext(), dataList, R.layout.girdviewitem, from, to);

        gridView.setAdapter(simpleAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, "//////////////:"+adapterView.getPositionForView(view)+",i="+i+",l="+l);

                if (i == (icon.length - 1)) {
                    AddAppFragment addAppFragment=new AddAppFragment();
                    ReplaceFragment(mainLayout,addAppFragment);
                }
                if (i==2){
                    Intent intent=new Intent(getActivity(), DownloadActivity.class);
                    startActivity(intent);
                }
                if (i==1){
                    Intent intent =new Intent(getContext(),ClockInActivity.class);
                    startActivity(intent);

                }

            }
        });

    }

    public List<Map<String, Object>> getDataList() {
        for (int i = 0; i < icon.length; i++) {
            Map<String, Object> stringObjectsMap = new HashMap<String, Object>();
            stringObjectsMap.put("image", icon[i]);
            stringObjectsMap.put("text", iconName[i]);
            dataList.add(stringObjectsMap);

        }

        return dataList;
    }


    public void ReplaceFragment(int id, Fragment fragment_view) {

        android.support.v4.app.FragmentManager manager = getFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
        transaction.addToBackStack("pn");
        transaction.replace(id, fragment_view);
        transaction.commit();
    }
}
