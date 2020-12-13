package com.example.mp3app.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mp3app.R;
import com.example.mp3app.adapter.CreatePlaylistAdapter;
import com.example.mp3app.model.BaiHat;
import com.example.mp3app.service.APIService;
import com.example.mp3app.service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Fragment_Create_Playlists extends Fragment {
    View view;
    Button btnCreatePlaylists;
    ListView lvCreatePlaylists;
    CreatePlaylistAdapter createPlaylistAdapter;
    ArrayAdapter<String> test;
    ArrayList<String> testList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_create_playlists, container, false);
        btnCreatePlaylists = (Button) view.findViewById(R.id.btnCreatePlaylists);
        lvCreatePlaylists = (ListView) view.findViewById(R.id.lvCreatePlaylists);
//        // B1: Xác định nguồn Adapter
//        testList = new ArrayList<>();
//        testList.add("Trần Thanh Điền");
//        testList.add("Trần Thanh Điền");
//        testList.add("Trần Thanh Điền");
//        testList.add("Trần Thanh Điền");
//
//        test = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, testList);
//
//        //createPlaylistAdapter = new CreatePlaylistAdapter(getContext(), list);
//        lvCreatePlaylists.setAdapter(test);
        getData();
        addEvents();
        return view;
    }
    private void getData() {
        DataService dataService = APIService.getService(); // khởi tạo  DataService, lấy đường dẫn
        Call<List<BaiHat>> callBack = dataService.getDataBaiHat(); // gọi pthuc trả về mảng các Album
        callBack.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                // sự kiện lăng nghe thành công
                ArrayList<BaiHat> listBaiHat = (ArrayList<BaiHat>) response.body(); // trả về mảng dữ liệu
                // in ra xem kết quả
//                Log.d("BBBBBBBBBBB", listBaiHat.get(0).getIdBaiHat());
//                Log.d("BBBBBBBBBBB", listBaiHat.get(0).getTenBaiHat());
//                Log.d("BBBBBBBBBBB", listBaiHat.get(0).getHinhCaSi());
//                Log.d("BBBBBBBBBBBBBBBBBB", listBaiHat.get(0).getCaSi());
//                Log.d("BBBBBBBBBBBBBBBBBB", listBaiHat.get(0).getLuotThich());
//                Log.d("BBBBBBBBBBBBBBBBBB", listBaiHat.get(0).getLinkBaiHat());
//                Log.d("BBBBBBBBBBBBBBBBBB", listBaiHat.get(0).getLoiBaiHat());

                createPlaylistAdapter = new CreatePlaylistAdapter(getContext(), R.layout.line_create_play_list, listBaiHat); // gắn dữ liêu vào listView
//                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
//                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                //rvBaiHat.setLayoutManager(linearLayoutManager);
                lvCreatePlaylists.setAdapter(createPlaylistAdapter);

            }

            // sự kiện thất bại
            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {

            }
        });
    }

    public void addEvents() {
        // tạo playlists
        btnCreatePlaylists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getActivity().getLayoutInflater();
                View customLayout = inflater.inflate(R.layout.activity_create_playlists, null);
                builder.setView(customLayout);
                builder.setTitle("Create playlist");

                final EditText edt = (EditText) customLayout.findViewById(R.id.edtNamePlaylists);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = edt.getText().toString();
                        testList.add(name);
                        test.notifyDataSetChanged();
                        edt.setText("");
//                        builder.create();
//                        Toast.makeText(getContext(), "Tạo " + name + " thành công", Toast.LENGTH_LONG).show();

                        // xử lý thêm playlist ở đây


                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.show();

            }
        });

    }
}
