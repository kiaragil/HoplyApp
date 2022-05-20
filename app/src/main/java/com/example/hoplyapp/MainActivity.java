package com.example.hoplyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    Button bt_add, bt_viewall;
    EditText et_username, et_name, et_password;
    Switch sw_agree;
    ListView lview_users;
    Date currentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_add = findViewById(R.id.bt_add);
        bt_viewall = findViewById(R.id.bt_viewall);
        et_username = findViewById(R.id.et_username);
        et_name = findViewById(R.id.et_name);
        et_password = findViewById(R.id.et_password);
        sw_agree = findViewById(R.id.sw_agree);
        lview_users = findViewById(R.id.lview_userlist);

        //listeners
        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentTime = Calendar.getInstance().getTime();
//                String formatDate = DateFormat.getDateInstance(DateFormat.LONG).format(currentTime);
                Users userModel = new Users(0,
                                            et_name.getText().toString(),
                                            et_username.getText().toString(),
                                            et_password.getText().toString(),
                                            currentTime);
                Toast.makeText(MainActivity.this, userModel.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        bt_viewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(MainActivity.this, "VIEW ALL BUTTON", Toast.LENGTH_SHORT).show();
            }
        });
    }


}