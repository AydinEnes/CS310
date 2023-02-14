package com.example.javap;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private final List<CommentModel> commentList;
    private final LayoutInflater inflater;
    Context ctx;

    public CommentAdapter(Context context, List<CommentModel> commentList) {
        this.commentList = commentList;
        this.ctx = context;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.commentlist_view, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.setIsRecyclable(false);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        if(commentList.isEmpty()){
            return;
        }
        CommentModel currentComment = commentList.get(holder.getAdapterPosition());
        holder.commentName.setText(String.valueOf(currentComment.getUsername()));
        holder.commentMessage.setText(String.valueOf(currentComment.getComment()));
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView commentName;
        TextView commentMessage;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            commentName = itemView.findViewById(R.id.commentName);
            commentMessage = itemView.findViewById(R.id.commentMessage);
        }
    }


}
