package com.example.mp3app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mp3app.R;
import com.example.mp3app.adapter.BaiHatAdapter;
import com.example.mp3app.model.BaiHat;
import com.example.mp3app.service.APIService;
import com.example.mp3app.service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_BaiHat extends Fragment {
    View view;
    RecyclerView rvBaiHat;
    TextView txtNameSomg, txtAuthorSong;
    BaiHatAdapter baiHatAdapter;

    // gán layout
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bai_hat, container, false);
        rvBaiHat = view.findViewById(R.id.rvBaiHat);
        getData();
        return view;
    }

    private void getData() {
        DataService dataService = APIService.getService(); // khởi tạo  DataService, lấy đường dẫn
        Call<List<BaiHat>> callBack = dataService.getDataBaiHat(); // gọi pthuc trả về mảng các Album
        callBack.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                // sự kiện lăng nghe thành công
                ArrayList<BaiHat> listBaiHat = (ArrayList<BaiHat>) response.body(); // trả về mảng dữ liệu
                // in ra xem kết quả
//                Log.d("BBBBBBBBBBB", listBaiHat.get(0).getIdBaiHat());
//                Log.d("BBBBBBBBBBB", listBaiHat.get(0).getTenBaiHat());
//                Log.d("BBBBBBBBBBB", listBaiHat.get(0).getHinhCaSi());
//                Log.d("BBBBBBBBBBBBBBBBBB", listBaiHat.get(0).getCaSi());
//                Log.d("BBBBBBBBBBBBBBBBBB", listBaiHat.get(0).getLuotThich());
//                Log.d("BBBBBBBBBBBBBBBBBB", listBaiHat.get(0).getLinkBaiHat());
//                Log.d("BBBBBBBBBBBBBBBBBB", listBaiHat.get(0).getLoiBaiHat());

                baiHatAdapter = new BaiHatAdapter(getActivity(), listBaiHat); // gắn dữ liêu vào listView
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                rvBaiHat.setLayoutManager(linearLayoutManager);
                rvBaiHat.setAdapter(baiHatAdapter);

            }

            // sự kiện thất bại
            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {

            }
        });
    }


}
