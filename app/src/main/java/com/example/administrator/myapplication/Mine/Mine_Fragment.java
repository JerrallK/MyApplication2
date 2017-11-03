package com.example.administrator.myapplication.Mine;

import android.support.v4.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.os.Bundle;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.administrator.myapplication.R;

/**
 * Created by Administrator on 2017/9/5.
 */

public class Mine_Fragment extends Fragment implements View.OnClickListener {
    RelativeLayout calendar, weCommunity, myCollect, setting;

    int mainLayout =R.id.mainLayou;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mine_layout, container, false);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        calendar = new RelativeLayout(getActivity());
        weCommunity = new RelativeLayout(getActivity());
        myCollect = new RelativeLayout(getActivity());
        setting = new RelativeLayout(getActivity());

        calendar = getActivity().findViewById(R.id.schedule);
        weCommunity = getActivity().findViewById(R.id.weCommunity);
        myCollect = getActivity().findViewById(R.id.myCollect);
        setting = getActivity().findViewById(R.id.setting);

        calendar.setOnClickListener(this);
        weCommunity.setOnClickListener(this);
        myCollect.setOnClickListener(this);
        setting.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.schedule:
                CalendarFragment calendarFragment = new CalendarFragment();
                ReplaceFragment(mainLayout, calendarFragment);
                break;
            case R.id.weCommunity:
                WeCommunity weCommunity = new WeCommunity();
                ReplaceFragment(mainLayout, weCommunity);
                break;
            case R.id.myCollect:
                MyCollectFragment myCollectFragment = new MyCollectFragment();
                ReplaceFragment(mainLayout, myCollectFragment);
                break;
            case R.id.setting:
                SettingFragment settingFragment = new SettingFragment();
                ReplaceFragment(mainLayout, settingFragment);
                break;
            default:
                break;


        }

    }


    public void ReplaceFragment(int id, Fragment fragment_view) {

        android.support.v4.app.FragmentManager manager = getFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
        transaction.addToBackStack("pn");
        transaction.replace(id, fragment_view);
        transaction.commit();
    }
}
