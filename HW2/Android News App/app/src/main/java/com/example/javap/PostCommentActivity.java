package com.example.javap;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutorService;

public class PostCommentActivity extends AppCompatActivity {

    EditText txtName;
    EditText txtComment;
    TextView txtError;
    ProgressDialog progressDialog;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {

            int isValid = ((Number)msg.obj).intValue();

            if(isValid == 1){
                Intent newIntent = new Intent();
                setResult(RESULT_OK, newIntent);
                progressDialog.dismiss();
                finish();
            }

            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_comment);

        Button btnPostComment = findViewById(R.id.postButton);

        txtName = findViewById(R.id.postName);
        txtComment= findViewById(R.id.postComment);
        txtError = findViewById(R.id.postError);

        txtError.setVisibility(View.INVISIBLE);


        btnPostComment.setOnClickListener(v -> {
            String name = txtName.getText().toString();
            String comment = txtComment.getText().toString();
            txtError.setVisibility(View.INVISIBLE);

            if (name.isEmpty() || comment.isEmpty()){
                txtError.setVisibility(View.VISIBLE);
                return;
            }
            CommentsRepo repo = new CommentsRepo();
            ExecutorService srv = ((NewsApp)getApplication()).srv;

            int newsId = getIntent().getIntExtra("id",1);
            progressDialog = ProgressDialog.show(PostCommentActivity.this, "Please wait", "...");

            repo.postComment(srv, handler, name, comment, newsId);

        });

    }


}

