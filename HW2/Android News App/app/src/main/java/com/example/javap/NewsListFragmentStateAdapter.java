package com.example.javap;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import java.util.List;

class NewsListFragmentStateAdapter extends FragmentStateAdapter {

    private List<NewsListFragment> fragmentList;

    public NewsListFragmentStateAdapter(@NonNull FragmentActivity fragmentActivity, List<NewsListFragment> fragmentList) {
        super(fragmentActivity);
        this.fragmentList = fragmentList;
    }

    @Override
    public int getItemCount() {
        return fragmentList.size();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentList.get(position);
    }
}
