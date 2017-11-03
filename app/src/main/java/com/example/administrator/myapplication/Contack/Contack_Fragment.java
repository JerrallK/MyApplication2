package com.example.administrator.myapplication.Contack;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.administrator.myapplication.ImageView_Adapter;
import com.example.administrator.myapplication.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2017/9/5.
 */

public class Contack_Fragment extends Fragment {

    String TAG = "Contack_Fragment";

    SwipeRefreshLayout refreshLayout;
    ImageView addContack;
    ArrayAdapter<String> arrayAdapter;
    //错误原因：没有初始化contacklist
    List<String> contackList = new ArrayList<>();


    String[] contackString;

    private ListView sortListView;
    private LetterSideBar sideBar;
    /**
     * 显示字母的TextView
     */
    private TextView dialog;
    private SortAdapter adapter;
    private ClearEditText mClearEditText;

    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    private List<SortModel> SourceDateList;

    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contack_layout, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ListView contackview = (ListView) getActivity().findViewById(R.id.contack_list);
        arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, contackList);
        contackview.setAdapter(arrayAdapter);
        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.READ_CONTACTS}, 1);
        } else
            readContacks();


        initViews();
        addContack = (ImageView) getActivity().findViewById(R.id.addcontack);
        addContack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddContackActivity.class);
                startActivity(intent);

            }
        });
//        refreshLayout = getActivity().findViewById(R.id.contackRefresh);
//
//
//        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//
//                //TODO 刷新新增联系人
//                //contackList=null;
//                //readContacks();
//            }
//        });

    }

    public void readContacks() {

        Cursor cursor = null;

        try {
            cursor = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    Log.i("info", "name: " + name);
                    String num = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    Log.i("info", "num: " + num);
                    Log.d("miaomiao", "readContacks: " + name);
                    contackList.add(name + "\n" + num);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    readContacks();
                } else {
                    Toast.makeText(getActivity(), "you denied", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }


    public void initViews() {

        contackString = contackList.toArray(new String[contackList.size()]);
        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();

        pinyinComparator = new PinyinComparator();

        sideBar = (LetterSideBar) getActivity().findViewById(R.id.sidrbar);
        dialog = (TextView) getActivity().findViewById(R.id.dialog);
        sideBar.setTextView(dialog);

        //设置右侧触摸监听
        sideBar.setOnTouchLetterListener(new LetterSideBar.OnTouchLetterListener() {
            @Override
            public void onTouchLetter(String s) {
                int position = adapter.getPositionForSection(s.charAt(0));

                if (position != -1) {
                    sortListView.setSelection(position);
                }
            }
        });

        sortListView = (ListView) getActivity().findViewById(R.id.contack_list);
        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //这里要利用adapter.getItem(position)来获取当前position所对应的对象
                Toast.makeText(getActivity().getApplication(), ((SortModel) adapter.getItem(position)).getName(), Toast.LENGTH_SHORT).show();
            }
        });


        SourceDateList = filledData(contackString);

        // 根据a-z进行排序源数据
        Collections.sort(SourceDateList, pinyinComparator);
        adapter = new SortAdapter(getActivity(), SourceDateList);
        sortListView.setAdapter(adapter);


        mClearEditText = (ClearEditText) getActivity().findViewById(R.id.filter_edit);

        //根据输入框输入值的改变来过滤搜索
        mClearEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private List<SortModel> filledData(String[] date) {
        List<SortModel> mSortList = new ArrayList<SortModel>();

        for (int i = 0; i < date.length; i++) {
            SortModel sortModel = new SortModel();
            sortModel.setName(date[i]);
            //汉字转换成拼音
            String pinyin = characterParser.getSelling(date[i]);
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;

    }

    private void filterData(String filterStr) {
        List<SortModel> filterDateList = new ArrayList<SortModel>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = SourceDateList;
        } else {
            filterDateList.clear();
            for (SortModel sortModel : SourceDateList) {
                String name = sortModel.getName();
                if (name.toUpperCase().indexOf(
                        filterStr.toString().toUpperCase()) != -1
                        || characterParser.getSelling(name).toUpperCase()
                        .startsWith(filterStr.toString().toUpperCase())) {
                    filterDateList.add(sortModel);
                }
            }
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, pinyinComparator);
        adapter.updateListView(filterDateList);
    }


}
