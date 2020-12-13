package com.example.mp3app.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mp3app.R;
import com.example.mp3app.adapter.DanhSachAlbumAdapter;
import com.example.mp3app.model.Album;
import com.example.mp3app.service.APIService;
import com.example.mp3app.service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhSachAlbumActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView rvDanhSachAlbum;
    ArrayList<Album> listAlbums;
    DanhSachAlbumAdapter danhSachAlbumAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_album);
        addControls();
        getDataAllAlbum();
        addEvents();
    }

    // lấy data tất cả album
    private void getDataAllAlbum() {
        DataService dataService = APIService.getService(); // khởi tạo  DataService, lấy đường dẫn
        Call<List<Album>> callBack = dataService.getDataAllAlbum();// gọi pthuc trả về mảng các Album
        callBack.enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                // sự kiện lăng nghe thành công
                listAlbums = (ArrayList<Album>) response.body(); // trả về mảng dữ liệu
                // in ra xem kết quả
//                Log.d("BBBBBBBBBBB", listAlbums.get(0).getTenAlbum());
//                Log.d("BBBBBBBBBBB", listAlbums.get(0).getHinhAnhAlbum());
//                Log.d("BBBBBBBBBBB", listAlbums.get(0).getTenCasiAlbum());
                // gắn phần apdater lên
                danhSachAlbumAdapter = new DanhSachAlbumAdapter(DanhSachAlbumActivity.this, listAlbums);
                // hiển thị lên RecycleView ở đây, cho layout nào thì nó sẽ hiện thị dạng đó, Grid thì phải thêm số cột nữa
                // hiển thị dạng Grid với 2 cột
                rvDanhSachAlbum.setLayoutManager(new GridLayoutManager(DanhSachAlbumActivity.this, 2));
//                rvDanhSachAlbum.addItemDecoration(new GridSpacingItemDecoration(2, dp));
                rvDanhSachAlbum.setItemAnimator(new DefaultItemAnimator());
                rvDanhSachAlbum.setAdapter(danhSachAlbumAdapter);

            }

            // sự kiện thất bại
            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {

            }
        });
    }

    private void addEvents() {
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void addControls() {
        toolbar = findViewById(R.id.toolBarAlbum);
        rvDanhSachAlbum = findViewById(R.id.rvDanhSachAlbum);
        // tạo cái nút trên thanh toolbar để quay về
        // vì đã xoá action bar đi rồi nên dùng getSupport
        setSupportActionBar(toolbar); // thay thế tool bar vì đã bỏ action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // nút mũi tên quay lại
        getSupportActionBar().setTitle("List Album");


    }


}
