package com.sabanciuniv.cs310news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentViewHolder> {

    private Context ctx;
    private List<NewsComment> data;

    public CommentsAdapter(Context ctx, List<NewsComment> data) {
        this.ctx = ctx;
        this.data = data;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View root = LayoutInflater.from(ctx).inflate(R.layout.comments_row_layout,parent,false);
        CommentViewHolder holder = new CommentViewHolder(root);

        holder.setIsRecyclable(false);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        holder.commentName.setText(data.get(holder.getAdapterPosition()).getName());
        holder.commentText.setText(data.get(holder.getAdapterPosition()).getText());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class CommentViewHolder extends RecyclerView.ViewHolder{
        TextView commentName;
        TextView commentText;
        ConstraintLayout row;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);

            commentName = itemView.findViewById(R.id.commentRowTitle);
            commentText = itemView.findViewById(R.id.commentRowText);
            row = itemView.findViewById(R.id.commentsRow);
        }
    }
}
