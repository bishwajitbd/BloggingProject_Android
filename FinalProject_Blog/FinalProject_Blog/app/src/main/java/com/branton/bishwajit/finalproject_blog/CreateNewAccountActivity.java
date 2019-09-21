package com.branton.bishwajit.finalproject_blog;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateNewAccountActivity extends AppCompatActivity {
    private EditText firstName;
    private EditText lastNaem;
    private EditText email;
    private EditText password;
    private Button btnCreateAccount;
    private DatabaseReference mDatabaseReference;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mFirebaseAuth;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_account);
        mFirebaseDatabase= FirebaseDatabase.getInstance();
        mDatabaseReference= mFirebaseDatabase.getReference().child("mUsers");
        mFirebaseAuth = FirebaseAuth.getInstance();
        mProgressDialog= new ProgressDialog(this);

        firstName= (EditText) findViewById(R.id.txtFristNameCr);
        lastNaem= (EditText) findViewById(R.id.txtLastNameCr);
        email= (EditText) findViewById(R.id.txtEmailCr);
        password= (EditText) findViewById(R.id.txtPasswrodCr);
        btnCreateAccount= (Button) findViewById(R.id.btnCreateAccountCr);

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewAccount();
            }
        });
    }
    private void createNewAccount() {
        final String fname = firstName.getText().toString().trim();
        final String lname = lastNaem.getText().toString().trim();
        String mEmail = email.getText().toString().trim();
        String pws_before = password.getText().toString().trim();
        String pws;

        if (pws_before.length() < 9) {
            Toast.makeText(CreateNewAccountActivity.this, "Password should be 9 character", Toast.LENGTH_LONG).show();
        } else {
            pws = pws_before;

        if (!TextUtils.isEmpty(fname) && !TextUtils.isEmpty(lname)
                && !TextUtils.isEmpty(mEmail) && !TextUtils.isEmpty(pws)) {
            mProgressDialog.setMessage("Creating account...");
            mProgressDialog.show();

            mFirebaseAuth.createUserWithEmailAndPassword(mEmail, pws)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            if (authResult != null) {
                                String userid = mFirebaseAuth.getCurrentUser().getUid();
                                DatabaseReference currentUserDB = mDatabaseReference.child(userid);
                                currentUserDB.child("firstname").setValue(fname);
                                currentUserDB.child("lastname").setValue(lname);
                                mProgressDialog.dismiss();

                                // send users to postList
                                Intent intent = new Intent(CreateNewAccountActivity.this, PostListActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);

                            }
                        }
                    });
        }
    }
    }
}
