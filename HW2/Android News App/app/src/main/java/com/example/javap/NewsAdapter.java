package com.example.javap;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    Context ctx;
    private final List<NewsModel> newsList;
    private final LayoutInflater inflater;

    public NewsAdapter(Context context, List<NewsModel> newsList) {
        this.newsList = newsList;
        this.ctx = context;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.news_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.setIsRecyclable(false);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        NewsModel currentNews = newsList.get(position);
        holder.txtDate.setText(String.valueOf(currentNews.getDate()));
        holder.txtTitle.setText(currentNews.getTitle());

        NewsApp app = (NewsApp)((AppCompatActivity)ctx).getApplication();

        holder.downloadImage(app.srv,newsList.get(holder.getAdapterPosition()).getImagePath());

        holder.row.setOnClickListener(v -> {
            Intent newIntent = new Intent(ctx, NewsPageActivity.class);
            newIntent.putExtra("id",newsList.get(holder.getAdapterPosition()).getId());
            ctx.startActivity(newIntent);
        });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitle;
        TextView txtDate;
        ImageView imgList;
        ConstraintLayout row;
        boolean imageDownloaded;

        Handler imgHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {

                Bitmap img = (Bitmap)msg.obj;
                imgList.setImageBitmap(img);
                imageDownloaded = true;
                return true;
            }
        });

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            txtDate = itemView.findViewById(R.id.npDate);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            imgList = itemView.findViewById(R.id.imgList);
            row = itemView.findViewById(R.id.row_list);
        }
        public void downloadImage(ExecutorService srv, String path){

            if (!imageDownloaded){

                NewsRepo repo = new NewsRepo();
                repo.downloadImage(srv,imgHandler,path);
            }
        }
    }


}
