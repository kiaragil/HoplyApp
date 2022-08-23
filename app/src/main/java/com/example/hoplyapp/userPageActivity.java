package com.example.hoplyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class userPageActivity extends AppCompatActivity {

    TextView tv_user;
    Button bt_logout, bt_delete, bt_backhome;
    UserDBHelper myDB;
    PostDBHelper myDB2;
    ReactionsDBHelper myDB3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);
        getSupportActionBar().setTitle("Hoply!                SETTINGS");

        tv_user = findViewById(R.id.tv_user);
        bt_logout = findViewById(R.id.bt_logout);
        bt_delete = findViewById(R.id.bt_delete);
        bt_backhome = findViewById(R.id.bt_backhome);
        myDB = new UserDBHelper(userPageActivity.this);
        myDB2 = new PostDBHelper(userPageActivity.this);
        myDB3 = new ReactionsDBHelper(userPageActivity.this);
        Intent intent = getIntent();
        String logged_user = intent.getStringExtra("logged_user");
        tv_user.setText("Hello, " + logged_user + "!");

        //deletes reactions, posts, and user
        //redirects to login page
        bt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDB3.deleteReactionByUser(logged_user);
                myDB2.deletePostbyUser(logged_user);
                myDB.deleteUser(logged_user);

                if (!myDB.checkExistingUser(logged_user)) {
                    Toast.makeText(userPageActivity.this, "USER DELETED.", Toast.LENGTH_SHORT).show();
                    Intent redirectLogin = new Intent(getApplicationContext(), LoginActivity.class);
                    redirectLogin.putExtra("logged_user", " ");
                    startActivity(redirectLogin);
                }
                else {
                    Toast.makeText(userPageActivity.this, "ERROR! USER NOT DELETED.", Toast.LENGTH_SHORT).show();

                }
            }
        });

        //redirects back to login page and changes logged user to blank aka no one
        bt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent redirectLogin = new Intent(getApplicationContext(), LoginActivity.class);
                redirectLogin.putExtra("logged_user", " ");
                startActivity(redirectLogin);
                Toast.makeText(userPageActivity.this, "SUCCESSFULLY LOGGED OUT.", Toast.LENGTH_SHORT).show();
            }
        });

        bt_backhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent redirectHome = new Intent(getApplicationContext(), HomeActivity.class);
                redirectHome.putExtra("logged_user", logged_user);
                startActivity(redirectHome);
            }
        });
    }
}