package com.example.mp3app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mp3app.R;

public class Fragment_Loi_Bai_Hat extends Fragment {
    View view;
    TextView txtLoiBaiHat;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_loi_bai_hat1, container, false);
        txtLoiBaiHat = view.findViewById(R.id.txtLoiBaiHat);
        return view;
    }

    public void setLoiBaiHatLenGiaoDien(String loiBaiHat) {
//        char[] arrKyTu = loiBaiHat.toCharArray();
//        String line = "";
//        int viTri = 0;
//        for (int i = 1; i < arrKyTu.length; i++) {
//            if (arrKyTu[i] >= 65 && arrKyTu[i] <= 90 || arrKyTu[i] == 'Đ' || arrKyTu[i] == 'Ă' || arrKyTu[i] == 'Â' ||
//                    arrKyTu[i] == 'Ê' || arrKyTu[i] == 'Ô'|| arrKyTu[i] == 'Ơ' || arrKyTu[i] == 'Ư') {
//                line += loiBaiHat.substring(viTri, i) + "";
//                if (arrKyTu[i-1] != '\n') {
//                    line += "\n";
//                }
//                viTri = i;
//            }
//        }
//        line += loiBaiHat.substring(viTri, loiBaiHat.length()); //câu cuối
        txtLoiBaiHat.setText(loiBaiHat);
    }
}