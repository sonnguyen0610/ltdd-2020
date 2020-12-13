package com.example.mp3app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mp3app.activity.DanhSachBaiHatActivity;
import com.example.mp3app.activity.DanhSachPlayListActivity;
import com.example.mp3app.adapter.PlayListAdapter;
import com.example.mp3app.model.PlayList;
import com.example.mp3app.R;
import com.example.mp3app.service.APIService;
import com.example.mp3app.service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// Fragment này để gắn layout cho Playlist
public class Fragment_PlayList extends Fragment {
    View view;
    ListView lvPlayList;
    TextView txtTitlePlayList;
    Button btnViewMorePlayList;
    PlayListAdapter playListAdapter;
    ArrayList<PlayList> playLists;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // gắn layout cho Fragment
        view = inflater.inflate(R.layout.fragment_playlist, container, false);
        /// ánh xạ component
        lvPlayList = view.findViewById(R.id.lvPlayList);
        txtTitlePlayList = view.findViewById(R.id.txtTitlePlayList);
        btnViewMorePlayList = view.findViewById(R.id.btnMorePlayList);


        // sự kiện nhấn vào nút View more Playlist
        btnViewMorePlayList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DanhSachPlayListActivity.class);
                startActivity(intent);
            }
        });

        getData();
        return view;
    }


    private void getData() {
        DataService dataService = APIService.getService(); // khởi tạo  DataService, lấy đường dẫn
        Call<List<PlayList>> callBack = dataService.getPlayListCurrentDay(); // gọi pthuc trả về PlayList
        callBack.enqueue(new Callback<List<PlayList>>() {
            @Override
            public void onResponse(Call<List<PlayList>> call, Response<List<PlayList>> response) {
                // sự kiện lăng nghe
                playLists = (ArrayList<PlayList>) response.body(); // trả về mảng dữ liệu
//                Log.d("BBBBBBBBBBB", playLists.get(0).getTen());
//                Log.d("BBBBBBBBBBB", playLists.get(0).getIcon());
//                Log.d("BBBBBBBBBBB", playLists.get(0).getHinhPlayList());
                playListAdapter = new PlayListAdapter(getActivity(), android.R.layout.simple_list_item_1, playLists); // gắn dữ liêu vào listView
                lvPlayList.setAdapter(playListAdapter); // set Adapter cho list View
                setListViewHeightBasedOnChildren(lvPlayList);

                // sự kiện khi nhấn vào List View playlist (ở ngoài giao diện chính)
                lvPlayList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // gửi dữ liệu qua DanhSachBaiHat
                        Intent intent = new Intent(getActivity(), DanhSachBaiHatActivity.class);
                        intent.putExtra("playlist", playLists.get(position));
                        startActivity(intent);
                    }
                });
            }

            // sự kiện thất bại
            @Override
            public void onFailure(Call<List<PlayList>> call, Throwable t) {

            }
        });
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);

            if (listItem != null) {
                listItem.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
                listItem.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                //listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

}
