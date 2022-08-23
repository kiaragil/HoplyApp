package com.example.hoplyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    Button bt_log_register, bt_log_login;
    EditText et_log_username, et_log_password;
    UserDBHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Hoply!                   LOGIN");

        bt_log_register = findViewById(R.id.bt_logregister);
        bt_log_login = findViewById(R.id.bt_loglogin);
        et_log_username = findViewById(R.id.et_logusername);
        et_log_password = findViewById(R.id.et_logpassword);

        myDB = new UserDBHelper(LoginActivity.this);

        //button listeners

        //login button
        bt_log_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = et_log_username.getText().toString();
                String password = et_log_password.getText().toString();

                if (username.equals("") || password.equals("")){
                    Toast.makeText(LoginActivity.this, "please fill in all fields.", Toast.LENGTH_SHORT).show();
                } else {
                    //checks if username and password are in the database
                    Boolean loginResult = myDB.checkUserPassword(username,password);

                    if (!loginResult) {
                        Toast.makeText(LoginActivity.this, "invalid credentials.", Toast.LENGTH_SHORT).show();
                    } else {
                        //redirects to home page after login
                        Intent redirectHome = new Intent(getApplicationContext(), HomeActivity.class);
                        redirectHome.putExtra("logged_user", username);
                        startActivity(redirectHome);
                    }
                }
            }
        });

        //register button from login page
        bt_log_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //redirects to registration page
            Intent redirectReg = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(redirectReg);
            }
        });
    }


}