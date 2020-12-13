package com.example.mp3app.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mp3app.R;
import com.example.mp3app.adapter.DanhSachPlayListAdapter;
import com.example.mp3app.model.PlayList;
import com.example.mp3app.service.APIService;
import com.example.mp3app.service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhSachPlayListActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView rvDanhSachPlayList;
    ArrayList<PlayList> playLists;
    DanhSachPlayListAdapter danhSachPlayListAdapter;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_play_list);
        addControls();
        init();
        getDataPlayList();
    }

    private void getDataPlayList() {
        DataService dataService = APIService.getService(); // khởi tạo  DataService, lấy đường dẫn
        Call<List<PlayList>> callBack = dataService.getDataPlayList();// gọi pthuc trả về mảng các Album
        callBack.enqueue(new Callback<List<PlayList>>() {
            @Override
            public void onResponse(Call<List<PlayList>> call, Response<List<PlayList>> response) {
                // sự kiện lăng nghe thành công
                playLists = (ArrayList<PlayList>) response.body(); // trả về mảng dữ liệu
                // in ra xem kết quả
//                Log.d("BBBBBBBBBBB", playLists.get(0).getTen());
//                Log.d("BBBBBBBBBBB", playLists.get(0).getIcon());
//                Log.d("BBBBBBBBBBB", playLists.get(0).getHinhPlayList());
                // gắn phần apdater lên
                danhSachPlayListAdapter = new DanhSachPlayListAdapter(DanhSachPlayListActivity.this, playLists);
                // hiển thị lên RecycleView ở đây, cho layout nào thì nó sẽ hiện thị dạng đó, Grid thì phải thêm số cột nữa
                // hiển thị dạng Grid với 2 cột
                rvDanhSachPlayList.setLayoutManager(new GridLayoutManager(DanhSachPlayListActivity.this, 2));
                rvDanhSachPlayList.setAdapter(danhSachPlayListAdapter);
            }

            // sự kiện thất bại
            @Override
            public void onFailure(Call<List<PlayList>> call, Throwable t) {

            }
        });
    }

    private void init() {
        setSupportActionBar(toolbar); // thay thế tool bar vì đã bỏ action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // nút mũi tên quay lại
        getSupportActionBar().setTitle("PlayList");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void addControls() {
        toolbar = findViewById(R.id.toolBarPlayList);
        rvDanhSachPlayList = findViewById(R.id.rvDanhSachPlayList);
    }
}
