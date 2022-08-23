package com.example.hoplyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class editPostActivity extends AppCompatActivity {

    Posts currentPost;
    Button bt_user, bt_backedit, bt_update;
    TextView et_update, tv_context, tv_new;
    PostDBHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);
        getSupportActionBar().setTitle("Hoply!               EDIT HOME");

        bt_user = findViewById(R.id.bt_user);
        bt_backedit = findViewById(R.id.bt_backedit);
        bt_update = findViewById(R.id.bt_update);
        et_update = findViewById(R.id.et_update);
        tv_context = findViewById(R.id.tv_context);
        tv_new = findViewById(R.id.tv_new);
        myDB = new PostDBHelper(editPostActivity.this);

        Intent intent = getIntent();
        String logged_user = intent.getStringExtra("logged_user");
        bt_user.setText("Hello, " + logged_user + "!");


        int post_id = intent.getIntExtra("post_id",-1);
        String post_context = intent.getStringExtra("post_context");
        String post_user_id = intent.getStringExtra("post_user_id");
        String post_ts = intent.getStringExtra("post_ts");

        currentPost = new Posts (post_id, post_user_id, post_context, post_ts);

        tv_context.setText("\"" + post_context + "\"");


//        currentPost = new Posts (post_id, post_user_id, post_context, post_ts);

        //baxk button to post page
        bt_backedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent redirectPost = new Intent(getApplicationContext(), PostViewActivity.class);
                redirectPost.putExtra("logged_user", logged_user);
                startActivity(redirectPost);
            }
        });

        //update button for post
        bt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //stores new post context
                String new_post_context = et_update.getText().toString();
                //makes sure the user logged in is the only one who can edit
                if (currentPost.getUser_id().equals(logged_user)){
                    myDB.editPost(currentPost, new_post_context);

                    //redirects to home page after update
                    Intent redirectPost = new Intent(getApplicationContext(), HomeActivity.class);
                    redirectPost.putExtra("logged_user", logged_user);
                    startActivity(redirectPost);
                }
            }
        });

        bt_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent redirectUserPage = new Intent(getApplicationContext(), userPageActivity.class);
                redirectUserPage.putExtra("logged_user", logged_user);
                startActivity(redirectUserPage);

            }
        });


    }
}