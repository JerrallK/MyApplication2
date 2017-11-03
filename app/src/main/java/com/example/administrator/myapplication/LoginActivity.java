package com.example.administrator.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.administrator.myapplication.utils.GetAccountData;
import com.example.administrator.myapplication.utils.SetFullScreen;

/**
 * Created by Administrator on 2017/10/27.
 */

public class LoginActivity extends Activity implements View.OnClickListener {

    private EditText userName, passWord;
    private LinearLayout loginButton;
    private String userNameText, passwordText;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        SetFullScreen.getInstance().setFullscreen(getWindow());
        initView();
    }

    private void initView() {

        userName = findViewById(R.id.login_username);
        passWord = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_btn);
        loginButton.setOnClickListener(this);
        userNameText = GetAccountData.getUserName(getApplicationContext());
        passwordText = GetAccountData.getPassword(getApplicationContext());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                if (userName.getText().toString().equals(userNameText) && passWord.getText().toString().equals(passwordText)) {
                    Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.login_success),Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(this,MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                }else {
                    Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.error),Toast.LENGTH_SHORT).show();
                }

                break;
            default:
                break;
        }
    }
}
