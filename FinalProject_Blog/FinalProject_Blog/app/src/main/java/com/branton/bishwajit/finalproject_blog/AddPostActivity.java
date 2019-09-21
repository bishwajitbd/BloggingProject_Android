package com.branton.bishwajit.finalproject_blog;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class AddPostActivity extends AppCompatActivity {
    private ImageButton mPostImage;
    private EditText mPostTitle;
    private EditText mPostDescription;
    private Button mSubmitButton;
    private Button mCancel;
    private DatabaseReference mPostDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private ProgressDialog mProgress;
    private static final int IMAGE_GALLERY_CODE=1;
    private Uri mImageUri;
    private StorageReference mStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        mProgress = new ProgressDialog(this);
        mStorage=FirebaseStorage.getInstance().getReference();

        mAuth= FirebaseAuth.getInstance();
        mUser= mAuth.getCurrentUser();

        mPostDatabase= FirebaseDatabase.getInstance().getReference().child("MBlog");

        mPostImage= (ImageButton) findViewById(R.id.imageButton);
        mPostTitle= (EditText) findViewById(R.id.txtPostTitle);
        mPostDescription= (EditText) findViewById(R.id.txtPostDescription);
        mSubmitButton= (Button) findViewById(R.id.btnSubmitPost);
        mCancel= (Button) findViewById(R.id.btnCancel);

        mPostImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imageGallaryIntent= new Intent(Intent.ACTION_GET_CONTENT);
                imageGallaryIntent.setType("image/*");
                startActivityForResult(imageGallaryIntent, IMAGE_GALLERY_CODE);
            }
        });

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Posting to our database
                startPosting();

            }
        });
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Canceling the post
                startActivity(new Intent(AddPostActivity.this, PostListActivity.class));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode== IMAGE_GALLERY_CODE && resultCode==RESULT_OK){
            mImageUri=data.getData();
            mPostImage.setImageURI(mImageUri);
        }
    }

private void startPosting() {
    mProgress.setMessage("Posting to blog...");
    mProgress.show();

    final String titleValue= mPostTitle.getText().toString().trim();
    final String descriiptionValue= mPostDescription.getText().toString().trim();

    String timestamp_now= String.valueOf(java.lang.System.currentTimeMillis());

    if (!TextUtils.isEmpty(titleValue) && !TextUtils.isEmpty(descriiptionValue) && mImageUri != null)    {
        final StorageReference filePath = mStorage.child("MBlog_images/"+timestamp_now+mImageUri.getLastPathSegment());
        UploadTask uploadTask = filePath.putFile(mImageUri);
        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return filePath.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    Map<String, String> dataToSave = new HashMap<>();
                    dataToSave.put("title", titleValue);
                    dataToSave.put("description", descriiptionValue);
                    dataToSave.put("image", downloadUri.toString());
                    dataToSave.put("timestamp", String.valueOf(java.lang.System.currentTimeMillis()));
                    dataToSave.put("user_id", mUser.getUid());
                    mPostDatabase.push().setValue(dataToSave);
                    mProgress.dismiss();
                    startActivity(new Intent(AddPostActivity.this,PostListActivity.class));
                    Toast.makeText(AddPostActivity.this, "Post Added", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                }
            }
        });
    }}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

}
