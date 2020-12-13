package com.example.mp3app.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.mp3app.activity.DanhSachBaiHatActivity;
import com.example.mp3app.model.QuangCao;
import com.example.mp3app.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BannerAdapter extends PagerAdapter {
    Context context;
    ArrayList<QuangCao> listBanner; /// dữ liệu quảng cáo lấy về

    public BannerAdapter(Context context, ArrayList<QuangCao> listBanner) {
        this.context = context;
        this.listBanner = listBanner;
    }

    @Override
    public int getCount() {
        return listBanner.size(); // trả về số hình quảng cáo, bao nhiêu view
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }


    // tất cả sự kiện cho View Pager đều viết tại đây
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        // gọi layout custom
        View view = inflater.inflate(R.layout.line_banner, null);
        // ánh xạ
        ImageView imageViewBackground = view.findViewById(R.id.imageViewBackgroundBanner); // hình ảnh background, quảng cáo
        ImageView imageSongBanner = view.findViewById(R.id.imageViewBanner); // hình ảnh bài hát
        TextView txtTitleSong = view.findViewById(R.id.txtTitleBanner);
        TextView txtContent = view.findViewById(R.id.txtNoiDung);
        // dùng Picasso để hiển thị hình ảnh
        Picasso.get().load(listBanner.get(position).getHinhAnh()).into(imageViewBackground);
        Picasso.get().load(listBanner.get(position).getHinhCaSi()).into(imageSongBanner);
//        Picasso.with(context).load(listBanner.get(position).getHinhAnh()).into(imageViewBackground); // hình ảnh quảng cáo
//        Picasso.with(context).load(listBanner.get(position).getHinhCaSi()).into(imageSongBanner); // hình ảnh ca sĩ bài hát
        txtTitleSong.setText(listBanner.get(position).getTenBaiHat());
        txtContent.setText(listBanner.get(position).getNoiDung());

        // sự kiện khi nhấn vào page trên quảng cáo
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "Bạn đã click", Toast.LENGTH_LONG).show();
                // chuyển và gửi dữ liệu qua màn hình danh sách các bài hát
                Intent intent = new Intent(context, DanhSachBaiHatActivity.class);
                intent.putExtra("banner", listBanner.get(position));
                context.startActivity(intent);
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
