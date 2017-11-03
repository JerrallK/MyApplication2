package com.example.administrator.myapplication;

import android.os.Build;
import android.support.v4.app.Fragment;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.administrator.myapplication.Contack.Contack_Fragment;
import com.example.administrator.myapplication.Mine.Mine_Fragment;
import com.example.administrator.myapplication.Msg.Msg_Fragment;
import com.example.administrator.myapplication.Work.Work_Fragment;
import com.example.administrator.myapplication.utils.SetFullScreen;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    RelativeLayout msg_btn, work_btn, contack_btn, mine_btn;
    ImageView msgimg, workimg, contackimg, mineimg;
    int current1, current2, touch_state = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SetFullScreen.getInstance().setFullscreen(getWindow());


        //之前的错误出在没有写对findViewByID

        final String color_blue = getResources().getString(R.string.blue);
        final String color_green = getResources().getString(R.string.green);
        viewPager = (ViewPager) findViewById(R.id.mviewpage);
        //viewPager.bringToFront();
//

//        viewPager.findViewById(R.id.mviewpage);

        msg_btn = (RelativeLayout) findViewById(R.id.msg_btn);

        work_btn = (RelativeLayout) findViewById(R.id.work_btn);
        contack_btn = (RelativeLayout) findViewById(R.id.contack_btn);
        mine_btn = (RelativeLayout) findViewById(R.id.mine_btn);


        msgimg = (ImageView) findViewById(R.id.msgimg);

        workimg = (ImageView) findViewById(R.id.workimg);
        contackimg = (ImageView) findViewById(R.id.contackimg);
        mineimg = (ImageView) findViewById(R.id.mineimg);


        Msg_Fragment msg_fragment = new Msg_Fragment();

        Work_Fragment work_fragment = new Work_Fragment();
        Contack_Fragment contack_fragment = new Contack_Fragment();
        Mine_Fragment mine_fragment = new Mine_Fragment();

        List<Fragment> TabList = new ArrayList<>();

        TabList.add(msg_fragment);

        TabList.add(contack_fragment);
        TabList.add(work_fragment);

        TabList.add(mine_fragment);

        final List<ImageView> Img = new ArrayList<>();

        Img.add(msgimg);
        Img.add(contackimg);
        Img.add(workimg);
        Img.add(mineimg);
        Img.get(0).setColorFilter(Color.parseColor(color_green));

        viewPager.setAdapter(new Fragment_Adapter(getSupportFragmentManager(), TabList));
        msg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Main", "***********" + viewPager.getCurrentItem());
                if (viewPager.getCurrentItem() != 0) {
                    Img.get(viewPager.getCurrentItem()).setColorFilter(Color.parseColor(color_blue));
                    Img.get(0).setColorFilter(Color.parseColor(color_green));
                    viewPager.setCurrentItem(0);
                } else ;

            }
        });

        contack_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Main", "***********" + viewPager.getCurrentItem());
                if (viewPager.getCurrentItem() != 1) {
                    Img.get(viewPager.getCurrentItem()).setColorFilter(Color.parseColor(color_blue));
                    Img.get(1).setColorFilter(Color.parseColor(color_green));
                    viewPager.setCurrentItem(1);
                } else ;
            }
        });
        work_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Main", "***********" + viewPager.getCurrentItem());
                if (viewPager.getCurrentItem() != 2) {
                    Img.get(viewPager.getCurrentItem()).setColorFilter(Color.parseColor(color_blue));
                    Img.get(2).setColorFilter(Color.parseColor(color_green));
                    viewPager.setCurrentItem(2);
                } else ;
            }
        });

        mine_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Main", "***********" + viewPager.getCurrentItem());
                if (viewPager.getCurrentItem() != 3) {
                    Img.get(viewPager.getCurrentItem()).setColorFilter(Color.parseColor(color_blue));
                    Img.get(3).setColorFilter(Color.parseColor(color_green));
                    viewPager.setCurrentItem(3);
                } else ;
            }
        });


        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == 1) {
                    current1 = viewPager.getCurrentItem();
                }
                touch_state = state;
                if (touch_state == 2) {
                    current2 = viewPager.getCurrentItem();
                    if (current2 != current1) {
                        Img.get(current1).setColorFilter(Color.parseColor(color_blue));
                        Img.get(current2).setColorFilter(Color.parseColor(color_green));
                        viewPager.setCurrentItem(current2);
                    }
                }

            }
        });
    }


//    public void ReplaceFragment(Fragment fragment) {
//        android.app.FragmentManager manager = getFragmentManager();
//        android.app.FragmentTransaction transaction = manager.beginTransaction();
//        transaction.replace(R.id.mviewpage, fragment);
//        transaction.commit();
//    }

}
