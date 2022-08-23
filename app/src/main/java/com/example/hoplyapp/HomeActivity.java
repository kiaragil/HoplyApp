package com.example.hoplyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    Button bt_post, bt_user;
    EditText et_post;
    Date currentTime;
    PostDBHelper myDB;
    ArrayAdapter postsArray;
    ListView lv_posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().setTitle("Hoply!                    HOME");

        //taking username from login activity for header
        bt_user = findViewById(R.id.bt_user);
        Intent intent = getIntent();
        String logged_user = intent.getStringExtra("logged_user");
        bt_user.setText("Hello, " + logged_user + "!");

        bt_post = findViewById(R.id.bt_post);
        lv_posts = findViewById(R.id.lv_posts);
        et_post = findViewById(R.id.et_post);
        myDB = new PostDBHelper(HomeActivity.this);

        //updates post lists
        viewPosts(myDB);

        //button listeners

        //post button
        bt_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentTime = Calendar.getInstance().getTime();
                String formatDate = DateFormat.getDateInstance(DateFormat.FULL).format(currentTime);
                Posts postModel;
                String context = et_post.getText().toString();

                postModel = new Posts(0,
                        logged_user,
                        context,
                        formatDate);

                if (context.equals("")){
                    Toast.makeText(HomeActivity.this, "ERROR! empty posts are not allowed.", Toast.LENGTH_SHORT).show();
                } else {
                    boolean addSuccess = myDB.createPost(postModel);
                    if (addSuccess == true) {
                        viewPosts(myDB);
                        Toast.makeText(HomeActivity.this, "POST PUBLISHED!" , Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(HomeActivity.this, "ERROR! POST NOT PUBLISHED." , Toast.LENGTH_SHORT).show();
                    }
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

        //click listener for post list
        lv_posts.setOnItemClickListener ( new AdapterView.OnItemClickListener ( ) {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Posts clickedPost = (Posts) parent.getItemAtPosition(position);
                int post_id = clickedPost.getId();
                String post_user_id = clickedPost.getUser_id();
                String post_context = clickedPost.getContext();
                String post_ts = clickedPost.getStamp();

                //extracts information from post clicked and transfers it to post viewing page
                Intent redirectPost = new Intent(getApplicationContext(), PostViewActivity.class);
                redirectPost.putExtra("post_id", post_id);
                redirectPost.putExtra("post_user_id", post_user_id);
                redirectPost.putExtra("post_context", post_context);
                redirectPost.putExtra("post_ts", post_ts);
                redirectPost.putExtra("logged_user", logged_user);
                startActivity(redirectPost);

            }
        });
    }

    //view posts
    private void viewPosts(PostDBHelper myDB) {
        postsArray = new ArrayAdapter<Posts>(HomeActivity.this, android.R.layout.simple_list_item_1, myDB.getAllPosts());
        lv_posts.setAdapter(postsArray);
    }
}