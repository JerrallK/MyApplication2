package com.example.administrator.myapplication.Mine;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.administrator.myapplication.R;

import java.util.Comparator;


/**
 * Created by Administrator on 2017/9/27.
 */

public class CalendarFragment extends Fragment implements View.OnClickListener{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.calendar_layout,container,false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ImageView imageBack=new ImageView(getActivity());

        imageBack=(ImageView)getActivity().findViewById(R.id.calendarBack);
        imageBack.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.calendarBack:
                getActivity().onBackPressed();
        }

    }
}
