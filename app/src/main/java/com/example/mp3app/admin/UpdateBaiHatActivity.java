package com.example.mp3app.admin;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mp3app.R;
import com.example.mp3app.model.BaiHat;

import java.util.HashMap;
import java.util.Map;

public class UpdateBaiHatActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText edtChangeName, edtChangeAuthor, edtChangeImage;
    Button btnEdit, btnCancel;
    // địa chỉ file insert
    String urlUpdate = "https://ttdien.000webhostapp.com/Server/update.php";
    String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_song);

        // lấy dữ liệu từ MainActivity qua
        Intent intent = getIntent();
        BaiHat baiHat = (BaiHat) intent.getParcelableExtra("data");
        addcontrols();

        id = baiHat.getIdBaiHat();
        Toast.makeText(UpdateBaiHatActivity.this, id, Toast.LENGTH_LONG).show();
        edtChangeName.setText(baiHat.getTenBaiHat().toString().trim());
        edtChangeAuthor.setText(baiHat.getCaSi().toString().trim());
        edtChangeImage.setText(baiHat.getHinhCaSi().toString().trim());
        addEvents();


    }

    private void addEvents() {
        setSupportActionBar(toolbar); // thay thế tool bar vì đã bỏ action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // nút mũi tên quay lại
        getSupportActionBar().setTitle("");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // lấy giá trị người dùng nhập
                String name = edtChangeName.getText().toString();
                String author = edtChangeAuthor.getText().toString();
                String image = edtChangeImage.getText().toString();
                if (name.isEmpty() || author.isEmpty() || image.isEmpty()) {
                    Toast.makeText(UpdateBaiHatActivity.this, "Vui lòng nhập đầy đủ!", Toast.LENGTH_LONG).show();
                } else {
                    updateSong(urlUpdate);
                }

            }
        });
    }

    // hàm cập nhật bài hát
    private void updateSong(String urlUpdate) {
        // Volley để quản lí các Request Network
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlUpdate, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                // phần nhận kết quả
                if (response.trim().equalsIgnoreCase("Success")) {
                    Toast.makeText(UpdateBaiHatActivity.this, "Cập nhật thành công!", Toast.LENGTH_LONG).show();
                    // sau đó chuyển màn hình về MainActivity
                    Intent intent = new Intent(UpdateBaiHatActivity.this, QuanLyBaiHatActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(UpdateBaiHatActivity.this, "Cập nhật thất bại!", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UpdateBaiHatActivity.this, "Lỗi " + error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // lấy giá trị người dùng nhập
                String name = edtChangeName.getText().toString().trim();
                String author = edtChangeAuthor.getText().toString().trim();
                String image = edtChangeImage.getText().toString().trim();

                // khởi tạo Map để đẩy dữ liệu vào
                Map<String, String> map = new HashMap<>();
                map.put("id", id); // key khi đẩy data lên phải trùng với key khi viết mã PHP để lấy
                map.put("name", name);
                map.put("author", author);
                map.put("image", image);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void addcontrols() {
        toolbar = (Toolbar) findViewById(R.id.toolBarUpdateBaiHat);
        edtChangeName = (EditText) findViewById(R.id.edtChangeName);
        edtChangeAuthor = (EditText) findViewById(R.id.edtChangeAuthor);
        edtChangeImage = (EditText) findViewById(R.id.edtChangeImage);
        btnEdit = (Button) findViewById(R.id.btnEdit);
        btnCancel = (Button) findViewById(R.id.btnUpdateCancel);

    }

}
