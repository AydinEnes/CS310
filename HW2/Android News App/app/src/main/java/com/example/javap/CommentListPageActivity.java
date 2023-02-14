package com.example.javap;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class CommentListPageActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private final List<CommentModel> commentList = new ArrayList<>();
    private int newsId;

    ProgressDialog progressDialog;

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (resultCode != RESULT_OK || reqCode != 1)
            return;

        CommentsRepo repo = new CommentsRepo();
        ExecutorService exService = ((NewsApp)getApplication()).srv;
        newsId = getIntent().getIntExtra("id",1);
        repo.getCommentById(exService, handler, newsId);
    }

    private final Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            List<CommentModel> newList = (List<CommentModel>) msg.obj;
            updateList(newList);
            Log.d("request data===",msg.obj.toString());
        }
    };

    private void updateList(List<CommentModel> newList) {
        commentList.clear();
        commentList.addAll(newList);
        recyclerView.setAdapter(new CommentAdapter(CommentListPageActivity.this, commentList));
        progressDialog.dismiss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CommentsRepo repo = new CommentsRepo();
        setContentView(R.layout.comment_view);

        recyclerView = findViewById(R.id.commentList);
        progressDialog = ProgressDialog.show(this, "Please wait", "...");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ExecutorService exService = ((NewsApp)getApplication()).srv;
        newsId = getIntent().getIntExtra("id",1);

        repo.getCommentById(exService,handler, newsId);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_comment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.openPost){
            Intent newActivity = new Intent(this, PostCommentActivity.class);
            newActivity.putExtra("id",newsId);
            startActivityForResult(newActivity, 1);
        }
        else if(item.getItemId() == android.R.id.home){
            finish();
        }

        return true;
    }

}
