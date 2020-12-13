package com.example.mp3app.fragment;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mp3app.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Fragment_Disk extends Fragment {

    View view;
    CircleImageView circleImageView;
    public TextView txtNameCaSi, txtTenBaiHat;
    public ObjectAnimator objectAnimator;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_disk, container, false);
        txtTenBaiHat = view.findViewById(R.id.txtTenBaiHat);   //ÁNH XẠ TÊN BÀI HÁT
        txtNameCaSi = view.findViewById(R.id.txtTenCaSi);   //ÁNH XẠ TÊN CA SĨ

        circleImageView = view.findViewById(R.id.imgDisk);
        //txtNameCaSi = view.findViewById(R.id.txtTenCaSi);
        objectAnimator = ObjectAnimator.ofFloat(circleImageView, "rotation", 0f, 360f);
        objectAnimator.setDuration(25000);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setRepeatMode(ValueAnimator.RESTART);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.start();

        return view;
    }
    public void playNhac(String hinhanh) {
        Picasso.get().load(hinhanh).into(circleImageView);
    }
}
