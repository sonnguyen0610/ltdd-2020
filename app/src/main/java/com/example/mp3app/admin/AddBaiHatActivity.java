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

import java.util.HashMap;
import java.util.Map;

public class AddBaiHatActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText edtName, edtAuthor, edtImage;
    Button btnAdd, btnCancel;
    // địa chỉ file insert
    String urlInsert = "https://ttdien.000webhostapp.com/Server/insert.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_song);
        addControls();
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

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // lấy giá trị người dùng nhập
                String name = edtName.getText().toString();
                String author = edtAuthor.getText().toString();
                String image = edtImage.getText().toString();
                // kiểm tra người dùng có nhập đầy đủ thông tin hay k?
                if (name.isEmpty() || author.isEmpty() || image.isEmpty()) {
                    Toast.makeText(AddBaiHatActivity.this, "Vui lòng nhập đầy đủ!", Toast.LENGTH_LONG).show();
                } else {
                    addSongs(urlInsert);
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // hàm thêm bài hát
    private void addSongs(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        // đẩy dữ liệu lên nên dùng POST
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // phần nhận kết quả
                if (response.trim().equalsIgnoreCase("Success")) {
                    Toast.makeText(AddBaiHatActivity.this, "Thêm thành công", Toast.LENGTH_LONG).show();
                    // sau đó chuyển màn hình về MainActivity
                    Intent intent = new Intent(AddBaiHatActivity.this, QuanLyBaiHatActivity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(AddBaiHatActivity.this, "Thêm thất bại", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // phần lỗi
                Toast.makeText(AddBaiHatActivity.this, "Lỗi " + error.toString(), Toast.LENGTH_LONG).show();
            }
        }
        ) {
            // đẩy dữ liệu lên Server
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // lấy giá trị người dùng nhập
                String name = edtName.getText().toString();
                String author = edtAuthor.getText().toString();
                String image = edtImage.getText().toString();

                // khởi tạo Map để đẩy dữ liệu vào
                Map<String, String> map = new HashMap<>();
                map.put("name", name); // key khi đẩy data lên phải trùng với key khi viết mã PHP để lấy
                map.put("author", author);
                map.put("image", image);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    // ánh xạ các component
    private void addControls() {
        toolbar = (Toolbar) findViewById(R.id.toolBarAddBaiHat);
        edtName = (EditText) findViewById(R.id.edtName);
        edtAuthor = (EditText) findViewById(R.id.edtAuthor);
        edtImage = (EditText) findViewById(R.id.edtImage);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnCancel = (Button) findViewById(R.id.btnCancel);

    }
}
