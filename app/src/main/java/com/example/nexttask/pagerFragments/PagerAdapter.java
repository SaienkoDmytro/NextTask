package com.example.nexttask.pagerFragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class PagerAdapter extends FragmentStateAdapter {

    private final static int PAGE_COUNT = 2;

    public PagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        if (position == 0) {
            return new FirstFragment();
        } else return new SecondFragment();
    }

    @Override
    public int getItemCount() {
        return PAGE_COUNT;
    }
}
