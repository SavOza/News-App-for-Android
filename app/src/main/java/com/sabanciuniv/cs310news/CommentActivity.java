package com.sabanciuniv.cs310news;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CommentActivity extends AppCompatActivity {

    private RecyclerView recView;
    private ProgressBar progBar;
    private int newsId;

    Handler dataHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            List<NewsComment> data = (List<NewsComment>)message.obj;
            CommentsAdapter adp = new CommentsAdapter(CommentActivity.this,data);
            recView.setAdapter(adp);

            progBar.setVisibility(View.INVISIBLE);
            recView.setVisibility(View.VISIBLE);

            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        getSupportActionBar().setTitle("Comments");

        recView = findViewById(R.id.commentsRecView);
        progBar = findViewById(R.id.commentProgBar);
        recView.setLayoutManager(new LinearLayoutManager(this));

        recView.setVisibility(View.INVISIBLE);
        progBar.setVisibility(View.VISIBLE);

        newsId = getIntent().getIntExtra("id",1);

        NewsRepository repo = new NewsRepository();
        repo.getCommentsByNewsId(((NewsApp)getApplication()).srv,dataHandler,newsId);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.post_comment_bar,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        else if (item.getItemId() == R.id.postCommentButton)
        {
            Intent i = new Intent(CommentActivity.this,PostActivity.class);
            i.putExtra("id",newsId);
            startActivity(i);
        }

        return true;
    }
}
