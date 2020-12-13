package com.example.mp3app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mp3app.R;
import com.example.mp3app.admin.AdminActivity;
import com.example.mp3app.model.Account;
import com.example.mp3app.service.APIService;
import com.example.mp3app.service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText editUsername, editPassword;
    Button btnLogin;
    String user_name, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_name = editUsername.getText().toString();
                password = editPassword.getText().toString();
                // kiểm tra phải có dữ liệu
                if (user_name.length() > 0 && password.length() > 0) {
                    DataService dataService = APIService.getService();
                    Call<List<Account>> callBack = dataService.getDataAccount(user_name, password);
                    callBack.enqueue(new Callback<List<Account>>() {
                        @Override
                        public void onResponse(Call<List<Account>> call, Response<List<Account>> response) {
                            ArrayList<Account> listAccounts = (ArrayList<Account>) response.body();
                            if (listAccounts.size() > 0) {
//                                Log.d("BBBBBBBBBB", listAccounts.get(0).getUsername());
//                                Log.d("BBBBBBBBBB", listAccounts.get(0).getPassword());
//                                Log.d("BBBBBBBBBB", listAccounts.get(0).getRole());
                                // nếu user đó là admin thì chuyển qua màn hình admin
                                if (listAccounts.get(0).getRole().equalsIgnoreCase("1")) {
                                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                                    intent.putExtra("admin_account", listAccounts);
                                    startActivity(intent);
                                    // ngược lại chuyển vào màn hình user thường
                                } else {
                                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_LONG).show();
                                    Intent getData = getIntent();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.putExtra("user_account", listAccounts);
                                    startActivity(intent);

                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Account>> call, Throwable t) {
                            Toast.makeText(LoginActivity.this, "Không tồn tại tài khoản!", Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    //Toast.makeText(LoginActivity.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }


            }
        });

    }
    private void addControls() {
        editUsername = (EditText) findViewById(R.id.editEmail);
        editPassword = (EditText) findViewById(R.id.editPassword);
        editUsername.setText("ttdien");
        editPassword.setText("dien1998");
        btnLogin = (Button) findViewById(R.id.btnSign);


    }
}
