package com.example.javap;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class NewsPageActivity extends AppCompatActivity {

    ImageView imgDetails;
    TextView txtNameDetail;
    TextView txtHistoryDetail;
    TextView txtDate;
    int newsId;

    Handler dataHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            NewsModel currentNews = (NewsModel) msg.obj;

            setTitle(currentNews.getCategoryName());

            txtNameDetail.setText(currentNews.getTitle());
            txtHistoryDetail.setText(currentNews.getDetails());
            txtDate.setText(String.valueOf(currentNews.getDate()));


            NewsRepo repo = new NewsRepo();
            repo.downloadImage(((NewsApp)getApplication()).srv,imgHandler,currentNews.getImagePath());

            return true;
        }
    });


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    Handler imgHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {

            Bitmap img = (Bitmap) msg.obj;
            imgDetails.setImageBitmap(img);

            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_page);

        newsId = getIntent().getIntExtra("id",1);

        imgDetails = findViewById(R.id.npImage);
        txtNameDetail = findViewById(R.id.npTitle);
        txtHistoryDetail = findViewById(R.id.npDetails);
        txtDate = findViewById(R.id.npDate);

        NewsRepo repo = new NewsRepo();
        repo.getNewsById(((NewsApp)getApplication()).srv, dataHandler, newsId);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.openComment){
            Intent newIntent = new Intent(this, CommentListPageActivity.class);
            newIntent.putExtra("id", newsId);
            this.startActivity(newIntent);
        }
        else if(item.getItemId() == android.R.id.home){
            finish();
        }

        return true;
    }

}
