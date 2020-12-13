package com.example.mp3app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mp3app.activity.PlayNhacActivity;
import com.example.mp3app.adapter.PlayNhacAdapter;
import com.example.mp3app.R;

public class Fragment_Play_Danh_Sach_Bai_Hat extends Fragment {
    View view;
    public RecyclerView rvPlayNhac;
    PlayNhacAdapter playNhacAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_play_danh_sach_bai_hat, container, false);
        rvPlayNhac = view.findViewById(R.id.rvPlayDanhSachBaiHat);
        // kiểm tra nếu có data thì mới gắn
        if (PlayNhacActivity.listSong.size() > 0) {
            playNhacAdapter = new PlayNhacAdapter(getActivity(), PlayNhacActivity.listSong);
            rvPlayNhac.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvPlayNhac.setAdapter(playNhacAdapter);
        }

        return view;
    }
}
