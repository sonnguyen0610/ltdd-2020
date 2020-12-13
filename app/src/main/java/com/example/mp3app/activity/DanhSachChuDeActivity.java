package com.example.mp3app.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mp3app.R;
import com.example.mp3app.adapter.DanhSachChuDeAdapter;
import com.example.mp3app.model.ChuDe;
import com.example.mp3app.service.APIService;
import com.example.mp3app.service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhSachChuDeActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView rvDanhSachChuDe;
    DanhSachChuDeAdapter danhSachChuDeAdapter;
    ArrayList<ChuDe> listChuDe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_chu_de);
        addControls();
        getDataAllChuDe();
        addEvents();
    }

    private void addEvents() {
        toolbar.setTitleTextColor(Color.WHITE); // màu chữ cho ToolBar
        // nhấn vào mũi tên quay về
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    // lấy data tất cả chủ đề
    private void getDataAllChuDe() {
        DataService dataService = APIService.getService(); // khởi tạo  DataService, lấy đường dẫn
        Call<List<ChuDe>> callBack = dataService.getDataAllChuDe();// gọi pthuc trả về mảng các Album
        callBack.enqueue(new Callback<List<ChuDe>>() {
            @Override
            public void onResponse(Call<List<ChuDe>> call, Response<List<ChuDe>> response) {
                // sự kiện lăng nghe thành công
                listChuDe = (ArrayList<ChuDe>) response.body(); // trả về mảng dữ liệu
                // in ra xem kết quả
//                Log.d("BBBBBBBBBBB", listAlbums.get(0).getTenAlbum());
//                Log.d("BBBBBBBBBBB", listAlbums.get(0).getHinhAnhAlbum());
//                Log.d("BBBBBBBBBBB", listAlbums.get(0).getTenCasiAlbum());
                // gắn phần apdater lên
                danhSachChuDeAdapter = new DanhSachChuDeAdapter(DanhSachChuDeActivity.this, listChuDe);
                // hiển thị lên RecycleView ở đây, cho layout nào thì nó sẽ hiện thị dạng đó, Grid thì phải thêm số cột nữa
                // hiển thị dạng Grid với 2 cột
                rvDanhSachChuDe.setLayoutManager(new GridLayoutManager(DanhSachChuDeActivity.this, 2));
                //rvDanhSachChuDe.addItemDecoration(new GridSpacingItemDecoration(2, dp));
                rvDanhSachChuDe.setAdapter(danhSachChuDeAdapter);

            }

            // sự kiện thất bại
            @Override
            public void onFailure(Call<List<ChuDe>> call, Throwable t) {

            }
        });
    }

    private void addControls() {
        toolbar = findViewById(R.id.toolBarChuDe);
        rvDanhSachChuDe = findViewById(R.id.rvDanhSachChuDe);
        // tạo cái nút trên thanh toolbar để quay về
        // vì đã xoá action bar đi rồi nên dùng getSupport
        setSupportActionBar(toolbar); // thay thế tool bar vì đã bỏ action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // nút mũi tên quay lại
        getSupportActionBar().setTitle("List ChuDe");

    }
}
