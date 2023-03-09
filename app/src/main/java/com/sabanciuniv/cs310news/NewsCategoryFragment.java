package com.sabanciuniv.cs310news;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class NewsCategoryFragment extends Fragment {

    private RecyclerView recView;
    private ProgressBar progressBar;

    Handler dataHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            List<News> data = (List<News>) message.obj;
            NewsAdapter adp = new NewsAdapter(getActivity(),data);
            recView.setAdapter(adp);
            recView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);

            return true;
        }
    });



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_news_category, container, false);

        recView = v.findViewById(R.id.recyclerViewList);
        progressBar = v.findViewById(R.id.progressBar);
        recView.setLayoutManager(new LinearLayoutManager(getActivity()));

        progressBar.setVisibility(View.VISIBLE);
        recView.setVisibility(View.INVISIBLE);


        NewsRepository repo = new NewsRepository();
        repo.getNewsByCategory(((NewsApp) getActivity().getApplication()).srv, dataHandler,getArguments().getInt("id"));


        return v;
    }


}