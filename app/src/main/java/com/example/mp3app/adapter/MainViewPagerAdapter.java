package com.example.mp3app.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class MainViewPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> listFragment = new ArrayList<>(); // danh sách chứa các Fragment
    private ArrayList<String> listTitle = new ArrayList<>(); // danh sách các title của từng Fragment
    public MainViewPagerAdapter(FragmentManager fm)
    {
        super(fm);
    }

    // lấy Fragment khi người dùng lướt qua lướt lại (chọn)
    @Override
    public Fragment getItem(int position) {
        return listFragment.get(position); // lấy vị trí Fragment
    }

    // lấy bao nhiêu Fragment
    @Override
    public int getCount() {
       return listFragment.size();
    }

    // phương thức thêm 1 Fragment với title tương ứng của nó
    public void addFragment(Fragment fm, String title) {
        listFragment.add(fm); // thêm vào Fragment
        listTitle.add(title); // thêm vào list title
    }

    // lấy tên của Tab
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return listTitle.get(position); // lấy vị trí của title
    }
}
