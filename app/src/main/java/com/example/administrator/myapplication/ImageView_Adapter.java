package com.example.administrator.myapplication;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;

/**
 * Created by Administrator on 2017/9/7.
 */

//public class ImageView_Adapter extends PagerAdapter{
//    Context context;
//    LayoutInflater layoutInflater;
//
//
//    static public int[] imgRes = new int[]{R.drawable.sports,R.drawable.animals,
//            R.drawable.people
//    };
//
//
//
//    @Override
//    public int getItemPosition(Object object) {
//        return POSITION_NONE;
//    }
//
//    @Override
//    public int getCount() {
//        return imgRes.length;
//    }
//
//
//    @Override
//    public boolean isViewFromObject(View view, Object object) {
//        return view==((LinearLayout)object);
//    }
//
//
//    public ImageView_Adapter(Context context){
//        this.context=context;
//        layoutInflater=(LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//    }
//
//
//    @Override
//    public Object instantiateItem(ViewGroup container, int position) {
//        View view=layoutInflater.inflate(R.layout.viewpager_img,container,false);
//        ImageView imageView=view.findViewById(R.id.viewpaget_img);
//        imageView.setImageResource(imgRes[position]);
//        container.addView(view);
//        return view;
//
//
//    }
//
//    @Override
//    public void destroyItem(ViewGroup container, int position, Object object) {
//        container.removeView((LinearLayout)object);
//    }
//}
