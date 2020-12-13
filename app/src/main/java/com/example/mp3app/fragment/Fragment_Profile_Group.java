package com.example.mp3app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mp3app.R;


public class Fragment_Profile_Group extends Fragment {
    View view;
    ImageView imgDien, imgPhong, imgTuyen, imgXuan, imgMain;
    TextView txtDien, txtPhong, txtTuyen, txtXuan, txtPosition1, txtPosition2, txtPosition3, txtPosition4, txtMain;
    Button btnDien, btnPhong, btnTuyen, btnXuan;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile_group, container, false);
        imgMain = view.findViewById(R.id.imgMain);
        imgDien = view.findViewById(R.id.imgDien);
        imgPhong = view.findViewById(R.id.imgPhong);
        imgTuyen = view.findViewById(R.id.imgTuyen);
        imgXuan = view.findViewById(R.id.imgXuan);
        txtMain = view.findViewById(R.id.txtMain);
        txtDien = view.findViewById(R.id.txtDien);
        txtPhong = view.findViewById(R.id.txtPhong);
        txtTuyen = view.findViewById(R.id.txtTuyen);
        txtXuan = view.findViewById(R.id.txtXuan);
        txtPosition1 = view.findViewById(R.id.txtPosition1);
        txtPosition2 = view.findViewById(R.id.txtPosition2);
        txtPosition3 = view.findViewById(R.id.txtPosition3);
        txtPosition4 = view.findViewById(R.id.txtPosition4);
        btnDien = view.findViewById(R.id.btnDien);
        btnDien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Đang làm tiếp....", Toast.LENGTH_LONG).show();
            }
        });
        btnPhong = view.findViewById(R.id.btnPhong);
        btnTuyen = view.findViewById(R.id.btnTuyen);
        btnXuan = view.findViewById(R.id.btnXuan);



        return view;
    }
}
