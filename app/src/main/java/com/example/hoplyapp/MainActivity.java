package com.example.hoplyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //declaring attributes on page
    Button bt_register, bt_login;
    EditText et_username, et_name, et_password, et_repassword;
    Date currentTime;
    //declaring dbHelper to connect app to DB
    UserDBHelper myDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Hoply!           REGISTRATION");

        //initializing attributes
        bt_register = findViewById(R.id.bt_register);
        bt_login = findViewById(R.id.bt_login);
        et_username = findViewById(R.id.et_username);
        et_name = findViewById(R.id.et_name);
        et_password = findViewById(R.id.et_password);
        et_repassword = findViewById(R.id.et_repassword);

        myDB = new UserDBHelper(MainActivity.this);

        //listeners for buttons

        //register button
        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //time stamp stuff
                currentTime = Calendar.getInstance().getTime();
                String formatDate = DateFormat.getDateInstance(DateFormat.FULL).format(currentTime);
                Users userModel;

                //extracting information from fields
                String username = et_username.getText().toString();
                String name = et_name.getText().toString();
                String password = et_password.getText().toString();
                String repassword = et_repassword.getText().toString();

                //putting it into object
                userModel = new Users(
                        username.toLowerCase(),
                        name,
                        password,
                        repassword,
                        formatDate);

                //checking for blank fields
                if (username.equals("") || password.equals("") || repassword.equals("")){
                    Toast.makeText(MainActivity.this, "please fill in all fields.", Toast.LENGTH_SHORT).show();
                }
                //checking if passwords match
                else if (!(password.equals(repassword))){
                    Toast.makeText(MainActivity.this, "passwords do not match.", Toast.LENGTH_SHORT).show();

                }
                //checking if username already exists
                else if (myDB.checkExistingUser(username)) {
                    Toast.makeText(MainActivity.this, "username already exists. please log in.", Toast.LENGTH_SHORT).show();
                }

                //registering user
                else {
                    boolean addSuccess = myDB.createUser(userModel);
                    if(addSuccess == true) {
                        Toast.makeText(MainActivity.this, "SUCCESS! User created." , Toast.LENGTH_SHORT).show();
                        Intent redirectLogin = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(redirectLogin);
                    } else {
                        Toast.makeText(MainActivity.this, "ERROR! User NOT created." , Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });

        //login button from registration page
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //redirect to login page
                Intent redirectLogin = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(redirectLogin);
            }
        });
    }


}