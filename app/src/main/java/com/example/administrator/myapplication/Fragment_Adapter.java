package com.example.administrator.myapplication;


import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/5.
 */

public class Fragment_Adapter extends FragmentPagerAdapter {
    List<Fragment> list;

//    public Fragment_Adapter(FragmentManager fm, List<android.app.Fragment> tabList) {
//        super(fm);
//        this.list=list;
//    }

    public Fragment_Adapter(FragmentManager fm) {
        super(fm);
    }
    //Constructor
    public Fragment_Adapter(FragmentManager fm , List<Fragment> list){
        super(fm);
        this.list=list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
