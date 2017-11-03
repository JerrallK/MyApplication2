package com.example.administrator.myapplication.Contack;

import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapplication.R;

/**
 * Created by Administrator on 2017/9/28.
 */

public class AddContackActivity extends Activity {

    String TAG = "AddContackActivity";
    TextView cancel, complete;
    EditText nameText, numberText;
    String name, number;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contack_dialog);
        final Contack_Fragment contack_fragment = new Contack_Fragment();

        cancel = findViewById(R.id.addcancel);
        complete = findViewById(R.id.addcomplete);

        nameText = findViewById(R.id.name_edittext);
        numberText = findViewById(R.id.num_edittext);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = nameText.getText().toString();
                number = numberText.getText().toString();

                if ((name == null || name.isEmpty()) && (number == null || number.isEmpty())) {
                    Toast.makeText(AddContackActivity.this, "姓名或电话不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    InsertContack(name, number);
                    contack_fragment.readContacks();
                    contack_fragment.initViews();
                    onBackPressed();
                }
            }
        });

    }

    public void InsertContack(String name, String num) {
        ContentValues contentValues = new ContentValues();
        Uri rawContackUri = this.getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, contentValues);
        long rawContackId = ContentUris.parseId(rawContackUri);

        //add name
        contentValues.clear();
        contentValues.put(ContactsContract.Data.RAW_CONTACT_ID, rawContackId);
        contentValues.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
        contentValues.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, name);
        this.getContentResolver().insert(ContactsContract.Data.CONTENT_URI, contentValues);
        //this.getContentResolver().update(ContactsContract.Data.CONTENT_URI,contentValues, null, null);

        //add number
        contentValues.clear();
        contentValues.put(ContactsContract.Data.RAW_CONTACT_ID, rawContackId);
        contentValues.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        contentValues.put(ContactsContract.CommonDataKinds.Phone.NUMBER, num);
        contentValues.put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
        this.getContentResolver().insert(ContactsContract.Data.CONTENT_URI, contentValues);


    }

}
