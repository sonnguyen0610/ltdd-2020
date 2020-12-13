package com.example.mp3app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mp3app.R;
import com.example.mp3app.adapter.AcousticAdapter;
import com.example.mp3app.model.Acoustic;
import com.example.mp3app.service.APIService;
import com.example.mp3app.service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Acoustic extends Fragment {
    View view;
    RecyclerView rvAcoustic;
    TextView txtAcoustic, txtAcousticViewMore;
    Button btnAcousticViewMore;
    AcousticAdapter acousticAdapter;
    ArrayList<Acoustic> listAcoustic;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_acoustic, container, false);
        rvAcoustic = view.findViewById(R.id.rvAcoustic);
        txtAcoustic = view.findViewById(R.id.txtAcoustic);
        btnAcousticViewMore = view.findViewById(R.id.btnAcousticViewMore);
        // xử lý sự kiện khi nhấn vào Text xem thêm trong album
//        txtAcousticViewMore.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Chuyển qua màn hình danh sách tất cả album
//                Intent intent = new Intent(getActivity(), DanhSachAlbumActivity.class);
//                startActivity(intent);
//            }
//        });
        getData();
        return view;
    }

    private void getData() {
        DataService dataService = APIService.getService(); // khởi tạo  DataService, lấy đường dẫn
        Call<List<Acoustic>> callBack = dataService.getDataAcoustic(); // gọi pthuc trả về mảng các Album
        callBack.enqueue(new Callback<List<Acoustic>>() {
            @Override
            public void onResponse(Call<List<Acoustic>> call, Response<List<Acoustic>> response) {
                // sự kiện lăng nghe thành công
                listAcoustic = (ArrayList<Acoustic>) response.body(); // trả về mảng dữ liệu
                // in ra xem kết quả
//                Log.d("BBBBBBBBBBB", listAlbums.get(0).getIdAlbum());
//                Log.d("BBBBBBBBBBB", listAlbums.get(0).getTenAlbum());
//                Log.d("BBBBBBBBBBB", listAlbums.get(0).getTenCasiAlbum());
//                Log.d("BBBBBBBBBBBBBBBBBB", listAlbums.get(0).getHinhAnhAlbum());
                acousticAdapter = new AcousticAdapter(getActivity(), listAcoustic); // gắn dữ liêu vào listView
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                rvAcoustic.setLayoutManager(linearLayoutManager);
                rvAcoustic.setAdapter(acousticAdapter);


            }

            // sự kiện thất bại
            @Override
            public void onFailure(Call<List<Acoustic>> call, Throwable t) {

            }
        });
    }
}
