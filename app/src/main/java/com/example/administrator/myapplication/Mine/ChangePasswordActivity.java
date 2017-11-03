package com.example.administrator.myapplication.Mine;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.utils.GetAccountData;
import com.example.administrator.myapplication.utils.SetFullScreen;

/**
 * Created by Administrator on 2017/10/31.
 */

public class ChangePasswordActivity extends Activity implements View.OnClickListener {
    private ImageView back;
    private LinearLayout complete;
    private EditText originalPW, newPW, new2PW;
    private String originalPWText, newPWText, new2PWText;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);
        SetFullScreen.getInstance().setFullscreen(getWindow());
        initView();
    }

    private void changePassword() {
        originalPWText = originalPW.getText().toString();
        newPWText = newPW.getText().toString();
        new2PWText = new2PW.getText().toString();
        if (originalPWText.equals(GetAccountData.getPassword(getApplicationContext()))) {
            if (!newPWText.equals(new2PWText)) {
                Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.password_error), Toast.LENGTH_SHORT).show();
            } else if (newPWText.isEmpty() && new2PWText.isEmpty()) {
                Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.empty_password), Toast.LENGTH_SHORT).show();
            } else if (new2PWText.equals(newPWText)) {
                editor.putString(GetAccountData.KEY_Password, new2PWText);
                originalPW.setText("");
                newPW.setText("");
                new2PW.setText("");
                if (editor.commit()) {
                    Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.change_password_success), Toast.LENGTH_SHORT).show();
                }

            }
        } else {
            Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.original_password_error), Toast.LENGTH_SHORT).show();
        }
    }

    private void initView() {
        back = findViewById(R.id.change_password_back);
        back.setOnClickListener(this);
        complete = findViewById(R.id.password_complete);
        complete.setOnClickListener(this);

        originalPW = findViewById(R.id.original_password);
        newPW = findViewById(R.id.confirm_passowrd);
        new2PW = findViewById(R.id.confirm_password_again);
        editor = getSharedPreferences(GetAccountData.FileName, MODE_PRIVATE).edit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.change_password_back:
                onBackPressed();
                break;
            case R.id.password_complete:
                changePassword();
                break;
            default:
                break;
        }

    }
}
