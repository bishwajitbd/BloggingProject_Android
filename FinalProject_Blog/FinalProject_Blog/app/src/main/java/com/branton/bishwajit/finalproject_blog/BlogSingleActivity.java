package com.branton.bishwajit.finalproject_blog;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BlogSingleActivity extends AppCompatActivity {

    private String mPost_key=  null;

    private Button mBack;
    private String mPost_title=  null;
    private String mPost_description=  null;


    private DatabaseReference mFirebaseDatabase;
    private ImageView mBlogSingleImage;
    private TextView mBlogSinleTitle;
    private TextView mBlogSingleDesc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_single);

        mFirebaseDatabase= FirebaseDatabase.getInstance().getReference().child("MBlog");
        mPost_key= getIntent().getExtras().getString("blog_position");
        mPost_title= getIntent().getExtras().getString("blog_title");
        mPost_description= getIntent().getExtras().getString("blog_Description");

        mBlogSinleTitle = (TextView) findViewById(R.id.txtPostTitleSingle);
        mBlogSingleDesc= (TextView) findViewById(R.id.txtPostDescriptionSingle);
        mBack= (Button) findViewById(R.id.btnBack);

        mBlogSinleTitle.setText(mPost_title);
        mBlogSingleDesc.setText(mPost_description);

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Canceling the post
                startActivity(new Intent(BlogSingleActivity.this, PostListActivity.class));
            }
        });

    }
}
