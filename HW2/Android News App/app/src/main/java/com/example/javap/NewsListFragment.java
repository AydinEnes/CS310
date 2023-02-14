package com.example.javap;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;

public class NewsListFragment extends Fragment {

    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private final List<NewsModel> newsList = new ArrayList<>();
    private ProgressBar progressBar;

    private final int categoryId;

    public NewsListFragment(int categoryId) {
        this.categoryId = categoryId;
    }

    private final Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            progressBar.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);

            List<NewsModel> newList = (List<NewsModel>) msg.obj;

            newsList.clear();
            newsList.addAll(newList);

            adapter.notifyDataSetChanged();
            Log.d("request data===",msg.obj.toString());
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_list, container, false);
        ExecutorService srv = ((NewsApp) requireActivity().getApplication()).srv;

        NewsRepo repo = new NewsRepo();
        repo.getNews(srv, handler, categoryId);
        adapter = new NewsAdapter(getActivity(), newsList);

        recyclerView = view.findViewById(R.id.recycler_view);
        progressBar = view.findViewById(R.id.progressBar);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
        recyclerView.setAdapter(adapter);

        return view;
    }


}