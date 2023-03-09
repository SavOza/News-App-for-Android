package com.sabanciuniv.cs310news;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class PostActivity extends AppCompatActivity {

    private Button postButton;
    private TextView warnText;
    private EditText editTxtName;
    private EditText editTxtComment;
    private ProgressBar progBarPost;

    private int newsIdInt;

    Handler postHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {

            Intent i = new Intent(PostActivity.this,CommentActivity.class);
            i.putExtra("id",newsIdInt);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            progBarPost.setVisibility(View.INVISIBLE);
            startActivity(i);
            finish();
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        getSupportActionBar().setTitle("Post Comment");

        progBarPost = findViewById(R.id.postProgBar);
        postButton = findViewById(R.id.postButton);
        warnText = findViewById(R.id.warningText);
        editTxtName = findViewById(R.id.editTxtName);
        editTxtComment = findViewById(R.id.editTxtComment);

        progBarPost.setVisibility(View.INVISIBLE);

        newsIdInt = getIntent().getIntExtra("id",1);
        String newsIdStr = Integer.toString(newsIdInt);
        Log.d("STATE",newsIdStr);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTxtName.getText().toString();
                String text = editTxtComment.getText().toString();
                if (name.isEmpty() || text.isEmpty())
                {
                    warnText.setText("Empty fields detected!");
                }
                else{
                    progBarPost.setVisibility(View.VISIBLE);
                    NewsRepository repo = new NewsRepository();
                    repo.postComment(((NewsApp)getApplication()).srv,postHandler,name,text,newsIdStr);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return true;
    }

}
