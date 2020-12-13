package com.example.mp3app.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mp3app.R;
import com.example.mp3app.adapter.DanhSachTheLoaiTheoChuDeAdapter;
import com.example.mp3app.model.ChuDe;
import com.example.mp3app.model.TheLoai;
import com.example.mp3app.service.APIService;
import com.example.mp3app.service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhSachTheLoaiTheoChuDeActivity extends AppCompatActivity {
    ChuDe chuDe;
    Toolbar toolbar;
    RecyclerView rvDanhSachTheLoaiTheoChuDe;
    ArrayList<TheLoai> listTheLoai;
    DanhSachTheLoaiTheoChuDeAdapter danhSachTheLoaiTheoChuDeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_the_loai_theo_chu_de);
        getDataIntent();
        addControls();
        getDataAllAlbum();
        addEvents();
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
        toolbar = findViewById(R.id.toolBarTheLoaiTheoChuDe);
        rvDanhSachTheLoaiTheoChuDe = findViewById(R.id.rvDanhSachTheLoaiTheoChuDe);
        // tạo cái nút trên thanh toolbar để quay về
        // vì đã xoá action bar đi rồi nên dùng getSupport
        setSupportActionBar(toolbar); // thay thế tool bar vì đã bỏ action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // nút mũi tên quay lại
        getSupportActionBar().setTitle(chuDe.getTenChuDe());
        
    }
    // lấy data tất cả album
    private void getDataAllAlbum() {
        DataService dataService = APIService.getService(); // khởi tạo  DataService, lấy đường dẫn
        Call<List<TheLoai>> callBack = dataService.getDataTheLoaiTheoChuDe(chuDe.getIdChuDe());// gọi pthuc trả về mảng các Album
        callBack.enqueue(new Callback<List<TheLoai>>() {
            @Override
            public void onResponse(Call<List<TheLoai>> call, Response<List<TheLoai>> response) {
                // sự kiện lăng nghe thành công
                listTheLoai = (ArrayList<TheLoai>) response.body(); // trả về mảng dữ liệu
                // in ra xem kết quả
//                Log.d("BBBBBBBBBBB", listAlbums.get(0).getTenAlbum());
//                Log.d("BBBBBBBBBBB", listAlbums.get(0).getHinhAnhAlbum());
//                Log.d("BBBBBBBBBBB", listAlbums.get(0).getTenCasiAlbum());
                // gắn phần apdater lên
                danhSachTheLoaiTheoChuDeAdapter = new DanhSachTheLoaiTheoChuDeAdapter(DanhSachTheLoaiTheoChuDeActivity.this, listTheLoai);
                // hiển thị lên RecycleView ở đây, cho layout nào thì nó sẽ hiện thị dạng đó, Grid thì phải thêm số cột nữa
                // hiển thị dạng Grid với 2 cột
                rvDanhSachTheLoaiTheoChuDe.setLayoutManager(new GridLayoutManager(DanhSachTheLoaiTheoChuDeActivity.this, 2));
//                rvDanhSachAlbum.addItemDecoration(new GridSpacingItemDecoration(2, dp));
                rvDanhSachTheLoaiTheoChuDe.setItemAnimator(new DefaultItemAnimator());
                rvDanhSachTheLoaiTheoChuDe.setAdapter(danhSachTheLoaiTheoChuDeAdapter);

            }

            // sự kiện thất bại
            @Override
            public void onFailure(Call<List<TheLoai>> call, Throwable t) {

            }
        });
    }

    // lấy nội dung từ màn hình DanhSachAllChuDe gửi qua
    private void getDataIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra("chu_de")) {
            chuDe = (ChuDe) intent.getSerializableExtra("chu_de");
            Toast.makeText(DanhSachTheLoaiTheoChuDeActivity.this, chuDe.getTenChuDe(), Toast.LENGTH_LONG).show();
        }

    }
}
