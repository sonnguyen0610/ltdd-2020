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
import com.example.mp3app.model.Account;

import java.util.HashMap;
import java.util.Map;

public class UpdateUserActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText edtUpdateUsername, edtUpdatePassword, edtUpdateImage, edtUpdateRole;
    Button btnEdit, btnCancel;
    // địa chỉ file insert
    String urlUpdate = "https://ttdien.000webhostapp.com/Server/updateuser.php";
    String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

        // lấy dữ liệu từ MainActivity qua
        Intent intent = getIntent();
        Account account = (Account) intent.getParcelableExtra("data");
        addcontrols();

        id = account.getIdUser();
        //Toast.makeText(UpdateSongActivity.this, id, Toast.LENGTH_LONG).show();
        edtUpdateUsername.setText(account.getUsername().toString().trim());
        edtUpdatePassword.setText(account.getPassword().toString().trim());
        edtUpdateImage.setText(account.getHinhAnh().toString().trim());
        edtUpdateRole.setText(account.getRole().toString().trim());
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
                String username = edtUpdateUsername.getText().toString();
                String password = edtUpdatePassword.getText().toString();
                String image = edtUpdateImage.getText().toString();
                String role = edtUpdateRole.getText().toString();
                if (username.isEmpty() || password.isEmpty() || image.isEmpty() || role.isEmpty()) {
                    Toast.makeText(UpdateUserActivity.this, "Vui lòng nhập đầy đủ!", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(UpdateUserActivity.this, "Cập nhật thành công!", Toast.LENGTH_LONG).show();
                    // sau đó chuyển màn hình về MainActivity
                    Intent intent = new Intent(UpdateUserActivity.this, QuanLyUserActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(UpdateUserActivity.this, "Cập nhật thất bại!", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UpdateUserActivity.this, "Lỗi " + error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // lấy giá trị người dùng nhập
                String username = edtUpdateUsername.getText().toString();
                String password = edtUpdatePassword.getText().toString();
                String image = edtUpdateImage.getText().toString();
                String role = edtUpdateRole.getText().toString();

                // khởi tạo Map để đẩy dữ liệu vào
                Map<String, String> map = new HashMap<>();
                map.put("iduser", id); // key khi đẩy data lên phải trùng với key khi viết mã PHP để lấy
                map.put("username", username);
                map.put("password", password);
                map.put("image", image);
                map.put("role", role);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void addcontrols() {
        toolbar = (Toolbar) findViewById(R.id.toolBarUpdateUser);
        edtUpdateUsername = (EditText) findViewById(R.id.edtUpdateUsername);
        edtUpdatePassword = (EditText) findViewById(R.id.edtUpdatePassword);
        edtUpdateImage = (EditText) findViewById(R.id.edtUpdateImage);
        edtUpdateRole = (EditText) findViewById(R.id.edtUpdateRole);
        btnEdit = (Button) findViewById(R.id.btnUpdateUser);
        btnCancel = (Button) findViewById(R.id.btnUpdateCancel);

    }

}
