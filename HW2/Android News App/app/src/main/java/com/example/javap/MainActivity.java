package com.example.javap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private TabLayout tabLayout;

    private final List<CategoryModel> categories = new ArrayList<>();
    private final List<NewsListFragment> fragments = new ArrayList<>();

    private ProgressBar progressBar;

    private final Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            Log.d("main=request data===",msg.obj.toString());

            List<CategoryModel> categoryData = (List<CategoryModel>) msg.obj;
            progressBar.setVisibility(View.INVISIBLE);
            tabLayout.setVisibility(View.VISIBLE);
            viewPager.setVisibility(View.VISIBLE);
            updateList(categoryData);
        }
    };

    private void updateList(List<CategoryModel> categories) {
        this.categories.clear();
        this.categories.addAll(categories);
        for(int i = 0; i < categories.size(); i++){
            fragments.add(new NewsListFragment(categories.get(i).getId()));
        }
        viewPager.setAdapter(new  NewsListFragmentStateAdapter(this,fragments));
        new TabLayoutMediator(  tabLayout,
                                viewPager,
                                (tab, position) -> tab.setText(categories.get(position).getName())
        ).attach();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CategoriesRepo repo = new CategoriesRepo();
        ExecutorService srv = ((NewsApp)getApplication()).srv;

        repo.getCategories(srv,handler);

        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tabLayout);
        progressBar = findViewById(R.id.maprogressBar);

        setTitle("NEWS");
        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_baseline_business_center_24);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager.setAdapter(new NewsListFragmentStateAdapter(this,fragments));

        progressBar.setVisibility(View.VISIBLE);
        tabLayout.setVisibility(View.INVISIBLE);
        viewPager.setVisibility(View.INVISIBLE);
    }
}