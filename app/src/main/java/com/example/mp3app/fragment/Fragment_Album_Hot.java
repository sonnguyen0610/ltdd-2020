package com.example.mp3app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mp3app.activity.DanhSachAlbumActivity;
import com.example.mp3app.adapter.AlbumAdapter;
import com.example.mp3app.model.Album;
import com.example.mp3app.R;
import com.example.mp3app.service.APIService;
import com.example.mp3app.service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Album_Hot extends Fragment {
    View view;
    RecyclerView rvAlbum;
    TextView txtAlbum, txtAlbumViewMore;
    AlbumAdapter albumAdapter;
    ArrayList<Album> listAlbums;

    // gắn layout cho nó
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_album_hot, container, false);
        rvAlbum = view.findViewById(R.id.rvAlbum);
        txtAlbum = view.findViewById(R.id.txtAlbum);
        txtAlbumViewMore = view.findViewById(R.id.txtAlbumViewMore);
        // xử lý sự kiện khi nhấn vào Text xem thêm trong album
        txtAlbumViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển qua màn hình danh sách tất cả album
                Intent intent = new Intent(getActivity(), DanhSachAlbumActivity.class);
                startActivity(intent);
            }
        });
        getData();
        return view;
    }
    // hàm lấy dữ liệu album
    private void getData() {
        DataService dataService = APIService.getService(); // khởi tạo  DataService, lấy đường dẫn
        Call<List<Album>> callBack = dataService.getDataAlbum(); // gọi pthuc trả về mảng các Album
        callBack.enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                // sự kiện lăng nghe thành công
                listAlbums = (ArrayList<Album>) response.body(); // trả về mảng dữ liệu
                // in ra xem kết quả
//                Log.d("BBBBBBBBBBB", listAlbums.get(0).getIdAlbum());
//                Log.d("BBBBBBBBBBB", listAlbums.get(0).getTenAlbum());
//                Log.d("BBBBBBBBBBB", listAlbums.get(0).getTenCasiAlbum());
//                Log.d("BBBBBBBBBBBBBBBBBB", listAlbums.get(0).getHinhAnhAlbum());
                albumAdapter = new AlbumAdapter(getActivity(), listAlbums); // gắn dữ liêu vào listView
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                rvAlbum.setLayoutManager(linearLayoutManager);
                rvAlbum.setAdapter(albumAdapter);


            }

            // sự kiện thất bại
            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {

            }
        });
    }
}
