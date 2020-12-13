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

public class Fragment_LoiBaiHat extends Fragment {
    View view;
    TextView txtLoiBaiHat;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_loi_bai_hat, container, false);
        txtLoiBaiHat = view.findViewById(R.id.txtLoiBaiHat);
        //getLoiBaiHat();
        return view;
    }

//    private void getLoiBaiHat() {
//        // kiểm tra nếu có data thì mới gắn
//        if (PlayNhacActivity.listSong.size() > 0) {
//            //Toast.makeText(getContext(), PlayNhacActivity.listSong.get(0).getLoiBaiHat(), Toast.LENGTH_LONG).show();
//            final String urlSong = PlayNhacActivity.listSong.get(PlayNhacActivity.position).getLoiBaiHat();
//            //Toast.makeText(getContext(), url, Toast.LENGTH_LONG).show();
//            final ArrayList<String> urls = new ArrayList<String>(); //to read each line
//            try {
//                URL url1 = new URL(urlSong); //My text file location
//                Toast.makeText(getContext(), urlSong, Toast.LENGTH_LONG).show();
//                //First open the connection
//                HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
//                //conn.setConnectTimeout(60000); // timing out in a minute
//
//                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//                String str;
//                while ((str = in.readLine()) != null) {
//                    urls.add(str);
//                    Toast.makeText(getContext(), urls.get(0), Toast.LENGTH_LONG).show();
//                }
//                in.close();
//            } catch (Exception e) {
//                txtLoiBaiHat.setText(e.toString());
//            }
//        }
//    }
}
