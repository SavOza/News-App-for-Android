package com.sabanciuniv.cs310news;

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
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.time.format.DateTimeFormatter;

public class NewsDetails extends AppCompatActivity {
    ImageView imgView;
    TextView txtTitle;
    TextView txtDate;
    TextView txtDesc;

    int newsId;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");


    Handler dataHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            News news = (News) message.obj;

            txtTitle.setText(news.getTitle());
            txtDate.setText(news.getDate().format(formatter));
            txtDesc.setText(news.getText());

            NewsRepository repo = new NewsRepository();
            repo.downloadImage(((NewsApp)getApplication()).srv,imgHandler, news.getImagePath());
            return true;
        }
    });

    Handler imgHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            Bitmap img = (Bitmap) message.obj;
            imgView.setImageBitmap(img);

            return true;
        }
    });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_details);
        newsId = getIntent().getIntExtra("id",1);

        getSupportActionBar().setTitle(getIntent().getStringExtra("category"));

        imgView = findViewById(R.id.detailsImage);
        txtDate = findViewById(R.id.detailsDate);
        txtDesc = findViewById(R.id.detailsText);
        txtTitle = findViewById(R.id.detailsTitle);


        NewsRepository repo = new NewsRepository();
        repo.getNewsById(((NewsApp)getApplication()).srv,dataHandler,newsId);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.show_comment_bar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        else if (item.getItemId() == R.id.addCommentButton)
        {
            Intent i = new Intent(NewsDetails.this,CommentActivity.class);
            i.putExtra("id",newsId);
            startActivity(i);
        }
        return true;
    }
}
