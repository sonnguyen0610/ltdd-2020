package com.example.mp3app.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class ViewPagerList extends FragmentPagerAdapter {
    public final ArrayList<Fragment> fragments = new ArrayList<Fragment>();

    public ViewPagerList(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public void addFragment(Fragment f) {
        fragments.add(f);
    }
}
