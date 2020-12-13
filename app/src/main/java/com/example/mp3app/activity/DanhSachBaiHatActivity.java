package com.example.mp3app.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mp3app.R;
import com.example.mp3app.adapter.DanhSachBaiHatAdapter;
import com.example.mp3app.model.Acoustic;
import com.example.mp3app.model.Album;
import com.example.mp3app.model.BaiHat;
import com.example.mp3app.model.ChuDe;
import com.example.mp3app.model.PlayList;
import com.example.mp3app.model.QuangCao;
import com.example.mp3app.model.TheLoai;
import com.example.mp3app.service.APIService;
import com.example.mp3app.service.DataService;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DanhSachBaiHatActivity extends AppCompatActivity {
    QuangCao quangCao;
    PlayList playList;
    Album album;
    Acoustic acoustic;
    TheLoai theLoai;
    ChuDe chuDe;

    CoordinatorLayout coordinatorLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;
    FloatingActionButton floatingActionButton;
    RecyclerView rvDanhSachBaiHat;
    AppBarLayout appBarLayout;
    View viewBackGround;
    TextView txtTotalSong;
    ImageView imgDanhSach;
    NestedScrollView nestedScrollView;
    ArrayList<BaiHat> listBaiHat;
    DanhSachBaiHatAdapter danhSachBaiHatAdapter;
    int count = 0;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_bai_hat);

        // kiểm tra tín hiệu mạng
        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);
        getDataIntent();
        addControls();
        init();

        // nếu quảng cáo tồn tại và tên bài hát 0 bằng rỗng
        if (quangCao != null && !quangCao.getTenBaiHat().equals("")) {
            Toast.makeText(this, quangCao.getTenBaiHat(), Toast.LENGTH_LONG).show();
            setValueInView(quangCao.getTenBaiHat(), quangCao.getHinhCaSi());
            getDataQuangCao(quangCao.getIdQuangCao());
        }
        // nếu acoustic tồn tại và tên bài hát 0 bằng rỗng
        if (acoustic != null && !acoustic.getTenAlbum().equals("")) {
            Toast.makeText(this, acoustic.getTenAlbum(), Toast.LENGTH_LONG).show();
            setValueInView(acoustic.getTenAlbum(),acoustic.getHinhAnhAlbum());
            getDataAcoustic(acoustic.getIdAlbum());
        }

        // nếu playlist tồn tại và tên bài hát 0 bằng rỗng
        if (playList != null && !playList.getTen().equals("")) {
            Toast.makeText(this, playList.getTen(), Toast.LENGTH_LONG).show();
            setValueInView(playList.getTen(), playList.getHinhPlayList());
            getDataPlayList(playList.getIdPlayList());
        }
        // nếu album tồn tại và tên album ko bằng rỗng
        if (album != null && !album.getTenAlbum().equals("")) {
            Toast.makeText(this, album.getTenAlbum(), Toast.LENGTH_LONG).show();
            setValueInView(album.getTenAlbum(), album.getHinhAnhAlbum());
            getDataAlbum(album.getIdAlbum());
        }
        // nếu TheLoai tồn tại và tên theloai ko bằng rỗng
        if (theLoai != null && !theLoai.getTenTheLoai().equals("")) {
            Toast.makeText(this, theLoai.getTenTheLoai(), Toast.LENGTH_LONG).show();
            setValueInView(theLoai.getTenTheLoai(), theLoai.getHinhTheLoai());
            getDataTheLoai(theLoai.getIdTheLoai());
        }

        addEvents();

    }



    // sự kiện khi nhấn vào nút icon mp3 (Floating...)
    private void addEvents() {
        floatingActionButton.setEnabled(true);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // gửi data và chuyển qua màn hình PlayNhacActivity

//                if (PlayNhacActivity.mediaPlayer != null) {
//                    PlayNhacActivity.mediaPlayer.stop();
//                    PlayNhacActivity.mediaPlayer.release();
//                    PlayNhacActivity.mediaPlayer = null;
//                }
//                PlayNhacActivity.position = 0;
                // gửi danh sách tất cả bài hát đi
                Intent intent = new Intent(DanhSachBaiHatActivity.this, PlayNhacActivity.class);
                intent.putExtra("listsong", listBaiHat);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });

    }
    private void getDataTheLoai(String idTheLoai) {
        DataService dataService = APIService.getService(); // khởi tạo  DataService, lấy đường dẫn
        Call<List<BaiHat>> callBack = dataService.getDataBaiHatTheoTheLoai(idTheLoai);// gọi pthuc trả về mảng các Album
        callBack.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                // sự kiện lăng nghe thành công

                listBaiHat = (ArrayList<BaiHat>) response.body(); // trả về mảng dữ liệu
                // in ra xem kết quả
                //Log.d("BBBBBBBBBBB", listBaiHat.get(0).getTenBaiHat());
                danhSachBaiHatAdapter = new DanhSachBaiHatAdapter(DanhSachBaiHatActivity.this, listBaiHat);
                rvDanhSachBaiHat.setLayoutManager(new LinearLayoutManager(DanhSachBaiHatActivity.this));
                rvDanhSachBaiHat.setAdapter(danhSachBaiHatAdapter);

            }

            // sự kiện thất bại
            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {

            }
        });
    }

    private void getDataAcoustic(String idAcoustic) {
        DataService dataService = APIService.getService(); // khởi tạo  DataService, lấy đường dẫn
        Call<List<BaiHat>> callBack = dataService.getDataBaiHatTheoAcoustic(idAcoustic);// gọi pthuc trả về mảng các Album
        callBack.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                // sự kiện lăng nghe thành công

                listBaiHat = (ArrayList<BaiHat>) response.body(); // trả về mảng dữ liệu
                // in ra xem kết quả
                //Log.d("BBBBBBBBBBB", listBaiHat.get(0).getTenBaiHat());
                danhSachBaiHatAdapter = new DanhSachBaiHatAdapter(DanhSachBaiHatActivity.this, listBaiHat);
                rvDanhSachBaiHat.setLayoutManager(new LinearLayoutManager(DanhSachBaiHatActivity.this));
                rvDanhSachBaiHat.setAdapter(danhSachBaiHatAdapter);

            }

            // sự kiện thất bại
            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {

            }
        });
    }
    private void getDataAlbum(String idAlbum) {
        DataService dataService = APIService.getService(); // khởi tạo  DataService, lấy đường dẫn
        Call<List<BaiHat>> callBack = dataService.getDataBaiHatTheoAlbum(idAlbum);// gọi pthuc trả về mảng các Album
        callBack.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                // sự kiện lăng nghe thành công
                listBaiHat = (ArrayList<BaiHat>) response.body(); // trả về mảng dữ liệu
                // in ra xem kết quả
                //Log.d("BBBBBBBBBBB", listBaiHat.get(0).getTenBaiHat());
                danhSachBaiHatAdapter = new DanhSachBaiHatAdapter(DanhSachBaiHatActivity.this, listBaiHat);
                rvDanhSachBaiHat.setLayoutManager(new LinearLayoutManager(DanhSachBaiHatActivity.this));
                rvDanhSachBaiHat.setAdapter(danhSachBaiHatAdapter);

            }

            // sự kiện thất bại
            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {

            }
        });
    }

    private void getDataPlayList(String idPlayList) {
        DataService dataService = APIService.getService(); // khởi tạo  DataService, lấy đường dẫn
        Call<List<BaiHat>> callBack = dataService.getDataBaiHatTheoPlayList(idPlayList);// gọi pthuc trả về mảng các Album
        callBack.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                // sự kiện lăng nghe thành công
                listBaiHat = (ArrayList<BaiHat>) response.body(); // trả về mảng dữ liệu
                // in ra xem kết quả
                //Log.d("BBBBBBBBBBB", listBaiHat.get(0).getTenBaiHat());
                danhSachBaiHatAdapter = new DanhSachBaiHatAdapter(DanhSachBaiHatActivity.this, listBaiHat);
                rvDanhSachBaiHat.setLayoutManager(new LinearLayoutManager(DanhSachBaiHatActivity.this));
                rvDanhSachBaiHat.setAdapter(danhSachBaiHatAdapter);

            }

            // sự kiện thất bại
            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {

            }
        });
    }

    private void getDataQuangCao(String id) {
        DataService dataService = APIService.getService(); // khởi tạo  DataService, lấy đường dẫn
        Call<List<BaiHat>> callBack = dataService.getDataBaiHatTheoQuangCao(id);// gọi pthuc trả về mảng các Album
        callBack.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                // sự kiện lăng nghe thành công
                listBaiHat = (ArrayList<BaiHat>) response.body(); // trả về mảng dữ liệu
                // in ra xem kết quả
                //Log.d("BBBBBBBBBBB", listBaiHat.get(0).getTenBaiHat());
                danhSachBaiHatAdapter = new DanhSachBaiHatAdapter(DanhSachBaiHatActivity.this, listBaiHat);
                rvDanhSachBaiHat.setLayoutManager(new LinearLayoutManager(DanhSachBaiHatActivity.this));
                rvDanhSachBaiHat.setAdapter(danhSachBaiHatAdapter);

            }

            // sự kiện thất bại
            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {

            }
        });

    }

    private void setValueInView(String name, String image) {
        // đầu  tiên lấy dữ liệu bài hát set lại tên cho thanh toolbar
        collapsingToolbarLayout.setTitle(name);


        try {
            URL url = new URL(image);
            Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            // convert về dạng Bitmap
            BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
            //Toast.makeText(this, bitmapDrawable.toString(), Toast.LENGTH_LONG).show();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                collapsingToolbarLayout.setBackground(bitmapDrawable);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        Picasso.get().load(image).into(imgDanhSach);

    }

    // sự kiện trên thanh toolbar
    private void init() {
        setSupportActionBar(toolbar); // thay thế tool bar vì đã bỏ
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // nút mũi tên quay lại
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        floatingActionButton.setEnabled(false);
    }


    private void addControls() {
        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        appBarLayout = findViewById(R.id.appBarLayout);
        collapsingToolbarLayout = findViewById(R.id.collapsingToolBar);
        viewBackGround = findViewById(R.id.viewBackGround);
        toolbar = findViewById(R.id.toolBarDanhSach);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        rvDanhSachBaiHat = findViewById(R.id.rvDanhSachBaiHat);
        imgDanhSach = findViewById(R.id.imgDanhSach);
        nestedScrollView = findViewById(R.id.nestScrollView);
        //txtTotalSong = findViewById(R.id.txtTotalSong);

    }

    // hàm này để kiểm tra có dữ liệu có đc gửi qua hay k
    private void getDataIntent() {
        // lấy intent bên banner, playlist qua
        Intent intent = getIntent();
        if (intent != null) { // nếu có dữ liệu
            if (intent.hasExtra("banner")) {
                quangCao = (QuangCao) intent.getSerializableExtra("banner");
                //Toast.makeText(this, quangCao.getTenBaiHat().toString(), Toast.LENGTH_LONG).show();
            }
        }
        // nếu có dữ liệu
        if (intent != null) {
            if (intent.hasExtra("playlist")) {
                playList = (PlayList) intent.getSerializableExtra("playlist");
                ///Toast.makeText(this, playList.getTen().toString(), Toast.LENGTH_LONG).show();
            }
        }
        // nếu có dữ liệu
        if (intent != null) {
            if (intent.hasExtra("all_album")) {
                album = (Album) intent.getSerializableExtra("all_album");
                ///Toast.makeText(this, playList.getTen().toString(), Toast.LENGTH_LONG).show();
            }
        }

        // nếu có dữ liệu
        if (intent != null) {
            if (intent.hasExtra("acoustic")) {
                acoustic = (Acoustic) intent.getSerializableExtra("acoustic");
                ///Toast.makeText(this, playList.getTen().toString(), Toast.LENGTH_LONG).show();
            }
        }
        // nếu có dữ liệu
        if (intent != null) {
            if (intent.hasExtra("idtheloai")) {
                theLoai = (TheLoai) intent.getSerializableExtra("idtheloai");
                //Toast.makeText(this, theLoai.getTenTheLoai(), Toast.LENGTH_LONG).show();
            }
        }


    }
}
