package com.example.hoplyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class PostViewActivity extends AppCompatActivity {

    TextView tv_context, tv_username, tv_stamp, tv_edit, tv_delete, et_react;
    Button bt_backpost, bt_user, bt_sendreaction;
    PostDBHelper myDB;
    ReactionsDBHelper myDB2;
    Posts clickedPost;
    ListView lv_reactions;
    ArrayAdapter reactionPosts;
    Date currentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view);
        getSupportActionBar().setTitle("Hoply!                    POST");

        //extracts post information from home page
        // the post that is clicked by user and its details are recorded below
        Intent intent = getIntent();
        int post_id = intent.getIntExtra("post_id",-1);
        String post_user_id = intent.getStringExtra("post_user_id");
        String post_context = intent.getStringExtra("post_context");
        String post_ts = intent.getStringExtra("post_ts");
        String logged_user = intent.getStringExtra("logged_user");
        clickedPost = new Posts (post_id, post_user_id, post_context, post_ts);

        et_react = findViewById(R.id.et_react);
        tv_context = findViewById(R.id.tv_context);
        tv_username = findViewById(R.id.tv_username);
        tv_stamp = findViewById(R.id.tv_stamp);
        bt_backpost = findViewById(R.id.bt_backpost);
        bt_user = findViewById(R.id.bt_user);
        bt_sendreaction = findViewById(R.id.bt_sendreaction);
        tv_edit = findViewById(R.id.tv_edit);
        tv_delete = findViewById(R.id.tv_delete);
        lv_reactions = findViewById(R.id.lv_reactions);

        //posts and reactions have separate db helpers
        myDB = new PostDBHelper(PostViewActivity.this);
        myDB2 = new ReactionsDBHelper(PostViewActivity.this);

        //updates reactions
        viewReactions(myDB2,post_id);

        //sets the contents of the page
        tv_context.setText("\"" + post_context + "\"");
        tv_username.setText("Posted by: " + post_user_id);
        tv_stamp.setText("Posted on " + post_ts);
        bt_user.setText("Hello, " + logged_user + "!");

        //visibility of edit and delete buttons on page based on the user logged in
        if (clickedPost.getUser_id().equals(logged_user)){
            tv_delete.setVisibility(View.VISIBLE);
            tv_delete.setClickable(true);
            tv_edit.setVisibility(View.VISIBLE);
            tv_edit.setClickable(true);
        } else {
            tv_delete.setVisibility(View.INVISIBLE);
            tv_delete.setClickable(false);
            tv_edit.setVisibility(View.INVISIBLE);
            tv_edit.setClickable(false);
        }

        //back button to home page
        bt_backpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent redirectHome = new Intent(getApplicationContext(), HomeActivity.class);
                redirectHome.putExtra("logged_user", logged_user);
                startActivity(redirectHome);
            }
        });

        //extracts current post details and redirects to editting page with all the information
        tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent redirectEdit = new Intent(getApplicationContext(), editPostActivity.class);
                redirectEdit.putExtra("post_id", post_id);
                redirectEdit.putExtra("post_user_id", post_user_id);
                redirectEdit.putExtra("post_stamp", post_ts);
                redirectEdit.putExtra("post_context", post_context);
                redirectEdit.putExtra("logged_user", logged_user);
                startActivity(redirectEdit);
                Toast.makeText(PostViewActivity.this, "EDIT BUTTON", Toast.LENGTH_SHORT).show();
            }
        });

        //deletes post
        tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //makes sure only user logged in can delete post
                if (clickedPost.getUser_id().equals(logged_user)){
                    myDB.deletePost(clickedPost);
                    Toast.makeText(PostViewActivity.this, "POST DELETED.", Toast.LENGTH_SHORT).show();

                    //redirects back to home page
                    Intent redirectHome = new Intent(getApplicationContext(), HomeActivity.class);
                    redirectHome.putExtra("logged_user", logged_user);
                    startActivity(redirectHome);
                } else {
                    Toast.makeText(PostViewActivity.this, "COULD NOT DELETE POST.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //send reaction button
        bt_sendreaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //extracts information on reaction submitted
                String reaction_type = et_react.getText().toString();
                int reaction_converted = Integer.parseInt(reaction_type);
                currentTime = Calendar.getInstance().getTime();
                String formatDate = DateFormat.getDateInstance(DateFormat.FULL).format(currentTime);
                Reactions reactionPost;

                reactionPost = new Reactions(logged_user,
                                            post_id,
                                            reaction_converted,
                                            formatDate);

                if (reaction_type.equals("")){
                    Toast.makeText(PostViewActivity.this, "You haven't entered a reaction", Toast.LENGTH_SHORT).show();
                }
                //checks if user has already submitted a reaction
                else if (myDB2.checkExistingReaction(logged_user, post_id)) {

                    //deletes post if 0 is provided
                    if (reaction_converted == 0) {
                        myDB2.deleteReaction(logged_user, post_id);
                        Toast.makeText(PostViewActivity.this, "REACTION DELETED!", Toast.LENGTH_SHORT).show();
                    }
                    //updates post using provided information
                    else {
                        myDB2.editReaction(reaction_converted, logged_user, post_id);
                        Toast.makeText(PostViewActivity.this, "REACTION UPDATED!", Toast.LENGTH_SHORT).show();
                    }
                    viewReactions(myDB2, post_id);
                }
                //action if reaction from user does not exist yet on particular post
                else {
                    boolean addSuccess = myDB2.createReaction(reactionPost);
                    if (addSuccess == true) {
                        Toast.makeText(PostViewActivity.this, "REACTION PUBLISHED!" , Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(PostViewActivity.this, "ERROR! REACTION NOT PUBLISHED. LOGGED USER IS " +  logged_user + reactionPost.toString(), Toast.LENGTH_SHORT).show();
                    }
                    viewReactions(myDB2, post_id);
                }

                bt_user.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent redirectUserPage = new Intent(getApplicationContext(), userPageActivity.class);
                        redirectUserPage.putExtra("logged_user", logged_user);
                        startActivity(redirectUserPage);

                    }
                });
            }
        });

    }
    //displays reactions
    private void viewReactions(ReactionsDBHelper myDB, int post_id) {
        reactionPosts = new ArrayAdapter<Reactions>(PostViewActivity.this, android.R.layout.simple_list_item_1, myDB.getAllReactions(post_id));
        lv_reactions.setAdapter(reactionPosts);
    }


}