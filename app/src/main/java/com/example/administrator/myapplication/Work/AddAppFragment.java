package com.example.administrator.myapplication.Work;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.administrator.myapplication.R;

/**
 * Created by Administrator on 2017/9/28.
 */

public class AddAppFragment extends Fragment implements View.OnClickListener {

    ImageView back;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.addapp_layout, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        back=getActivity().findViewById(R.id.addappBack);
        back.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.addappBack:
                getActivity().onBackPressed();
        }

    }
}
