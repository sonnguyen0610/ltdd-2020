package com.example.mp3app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mp3app.R;
import com.example.mp3app.activity.PlayNhacOffline;


public class Fragment_Downloaded extends Fragment {
    Button btnPlay;
    ListView lvPlayNhacOffline;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_downloaded, container, false);
        btnPlay = view.findViewById(R.id.btnPlayNhacOffline);
        lvPlayNhacOffline = view.findViewById(R.id.lvNhacOffline);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PlayNhacOffline.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
