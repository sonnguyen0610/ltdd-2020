package com.example.mp3app.admin;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.mp3app.R;
import com.example.mp3app.adapter.AdminAdapter;
import com.example.mp3app.model.Account;
import com.example.mp3app.model.Admin;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {
    Toolbar toolbar;
    ListView lvFunctionAdmin;
    ArrayList<Admin> listFunctionAdmin;
    AdminAdapter adminAdapter;
    ArrayList<Account> listAccount;
    Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        // lấy dữ liệu từ màn hình login qua
        Intent intent = getIntent();
        listAccount = intent.getParcelableArrayListExtra("admin_account");
        addControls();
        addEvents();
    }

    private void addEvents() {
        setSupportActionBar(toolbar); // thay thế tool bar vì đã bỏ action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // nút mũi tên quay lại
        getSupportActionBar().setTitle("Hi admin: " + listAccount.get(0).getUsername());
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // sự kiện khi nhấn vào từng item List View ở giao diện chức năng
        lvFunctionAdmin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(AdminActivity.this, "" + listFunctionAdmin.get(position).getTitle(), Toast.LENGTH_LONG).show();
                if (position==0) {
                    Intent intent = new Intent(AdminActivity.this, QuanLyUserActivity.class);
                    startActivity(intent);
                }
                if (position==1) {
                    Intent intent = new Intent(AdminActivity.this, QuanLyNhacActivity.class);
                    startActivity(intent);
                }
                if (position==2) {
//                    Intent intent = new Intent(AdminActivity.this, QuanLyUserActivity.class);
//                    startActivity(intent);
                }
                if (position==3) {
//                    Intent intent = new Intent(AdminActivity.this, QuanLyUserActivity.class);
//                    startActivity(intent);
                }
                if (position==4) {
//                    Intent intent = new Intent(AdminActivity.this, QuanLyUserActivity.class);
//                    startActivity(intent);
                }
            }
        });
    }

    private void addControls() {
        toolbar = (Toolbar) findViewById(R.id.toolBarFuctionAdmin);
        listFunctionAdmin = new ArrayList<>();
        lvFunctionAdmin = (ListView) findViewById(R.id.lvFunctionAdmin);
        registerForContextMenu(lvFunctionAdmin);
        listFunctionAdmin.add(new Admin("QUẢN LÝ NGƯỜI DÙNG", R.drawable.user_admin));
        listFunctionAdmin.add(new Admin("QUẢN LÝ NHẠC", R.drawable.music_admin));
        listFunctionAdmin.add(new Admin("THỐNG KÊ LƯỢT THÍCH", R.drawable.statistics_admin));
        listFunctionAdmin.add(new Admin("CHIẾN DỊCH MARKETING", R.drawable.marketing_admin));
        listFunctionAdmin.add(new Admin("THỐNG KÊ LƯỢT TẢI", R.drawable.download_admin));
        adminAdapter = new AdminAdapter(this, R.layout.line_admin, listFunctionAdmin);
        lvFunctionAdmin.setAdapter(adminAdapter);


    }
}
