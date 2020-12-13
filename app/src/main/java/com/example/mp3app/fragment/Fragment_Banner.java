package com.example.mp3app.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.mp3app.adapter.BannerAdapter;
import com.example.mp3app.model.QuangCao;
import com.example.mp3app.R;
import com.example.mp3app.service.APIService;
import com.example.mp3app.service.DataService;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Banner extends Fragment {
    View view;
    ViewPager viewPager;
    CircleIndicator circleIndicator;
    BannerAdapter bannerAdapter;
    Runnable runnable;
    Handler handler;
    int currentItem = 0;

    public Fragment_Banner() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_banner, container, false);
        addControls();
        getData();
        return view;
    }

    private void addControls() {
        viewPager = view.findViewById(R.id.viewPager);
        circleIndicator = view.findViewById(R.id.indicator);
    }

    public void getData() {
        DataService dataService = APIService.getService(); // khởi tạo  DataService
        Call<List<QuangCao>> callBack = dataService.getDataBanner();
        callBack.enqueue(new Callback<List<QuangCao>>() {
            @Override
            public void onResponse(Call<List<QuangCao>> call, Response<List<QuangCao>> response) {
                // trả về mảng Object
                ArrayList<QuangCao> bannners = (ArrayList<QuangCao>) response.body();
                // log ra xem kết quả trên consolve
//                Log.d("BBBBBBBBBBBBBB", bannners.get(0).getTenBaiHat().toString());
//                Log.d("BBBBBBBBBBBBBB", bannners.get(0).getNoiDung().toString());
//                Log.d("BBBBBBBBBBBBBB", bannners.get(0).getHinhAnh().toString());
//                Log.d("BBBBBBBBBBBBBB", bannners.get(0).getHinhCaSi().toString());
                bannerAdapter = new BannerAdapter(getActivity(), bannners);
                viewPager.setAdapter(bannerAdapter); // set số lượng view có bao nhiêu pager
                circleIndicator.setViewPager(viewPager);
                handler = new Handler();
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        currentItem = viewPager.getCurrentItem(); // lấy vị trí page hiện tại
                        currentItem++; // mỗi lần chạy sẽ tăng lên 1
                        // nếu vượt qua page thì sẽ quay về page đầu tiên
                        if (currentItem >= viewPager.getAdapter().getCount()) {
                            currentItem = 0; // set lại về vị trí đầu

                        }
                        viewPager.setCurrentItem(currentItem, true);
                        handler.postDelayed(runnable, 4500); // 4.5s thực hiện lại 1 lần

                    }
                };
                handler.postDelayed(runnable, 4500);
            }

            @Override
            public void onFailure(Call<List<QuangCao>> call, Throwable t) {

            }
        });
    }
}
