package com.example.mp3app.admin;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.mp3app.R;

public class QuanLyNhacActivity extends AppCompatActivity {
    Toolbar toolbar;
    CardView cardViewBaiHat, cardViewAlbum, cardViewPlaylist, cardViewChuDe, cardViewTheLoai;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_nhac);
        addControls();
        addEvents();
    }

    private void addEvents() {
        setSupportActionBar(toolbar); // thay thế tool bar vì đã bỏ action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // nút mũi tên quay lại
        getSupportActionBar().setTitle("Quản lý nhạc");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void addControls() {
        toolbar = (Toolbar) findViewById(R.id.toolBarQuanLyNhac);
        linearLayout = (LinearLayout) findViewById(R.id.ll);
        cardViewBaiHat = (CardView) findViewById(R.id.cardViewBaiHat);
        cardViewAlbum = (CardView) findViewById(R.id.cardViewAlbum);
        cardViewPlaylist = (CardView) findViewById(R.id.cardViewPlaylist);
        cardViewChuDe = (CardView) findViewById(R.id.cardViewChuDe);
        cardViewTheLoai = (CardView) findViewById(R.id.cardViewTheLoai);
        //Intent intent = new Intent(this,ae.class);
        cardViewBaiHat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(QuanLyNhacActivity.this, "Bài hát", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(QuanLyNhacActivity.this, QuanLyBaiHatActivity.class);
                startActivity(intent);
            }
        });
        cardViewAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(i);
                Toast.makeText(QuanLyNhacActivity.this, "Album", Toast.LENGTH_LONG).show();
            }
        });
        cardViewPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(i);
                Toast.makeText(QuanLyNhacActivity.this, "Playlist", Toast.LENGTH_LONG).show();
            }
        });
        cardViewChuDe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(i);
                Toast.makeText(QuanLyNhacActivity.this, "Chủ đề", Toast.LENGTH_LONG).show();
            }
        });
        cardViewTheLoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(i);
                Toast.makeText(QuanLyNhacActivity.this, "Thể loại", Toast.LENGTH_LONG).show();
            }
        });
        
    }
}
