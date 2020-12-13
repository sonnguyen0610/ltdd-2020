package com.example.mp3app.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mp3app.R;
import com.example.mp3app.activity.DanhSachTheLoaiTheoChuDeActivity;
import com.example.mp3app.model.ChuDe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DanhSachChuDeAdapter extends RecyclerView.Adapter<DanhSachChuDeAdapter.ViewHolder> {
    Context context;
    ArrayList<ChuDe> listChuDe;

    public DanhSachChuDeAdapter(Context context, ArrayList<ChuDe> listChuDe) {
        this.context = context;
        this.listChuDe = listChuDe;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.line_danh_sach_all_chu_de, parent, false);
        return new DanhSachChuDeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // gán data cho mỗi item
        ChuDe chuDe = listChuDe.get(position);
        Picasso.get().load(chuDe.getHinhChuDe()).into(holder.imgAllChuDe);
        holder.txtNameAllChuDe.setText(chuDe.getTenChuDe());

    }

    @Override
    public int getItemCount() {
        return listChuDe.size();
    }

    // View Holder để tăng tốc việc ánh xạ lại cho View
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNameAllChuDe, txtAuthorAllChuDe;
        ImageView imgAllChuDe;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            txtNameAllChuDe = itemView.findViewById(R.id.txtNameAllChuDe);
            txtAuthorAllChuDe = itemView.findViewById(R.id.txtAuthorAllChuDe);
            imgAllChuDe = itemView.findViewById(R.id.imgAllChuDe);

            // sự kiện khi nhấn vào hình từng ietm trong Tất cả các chủ đề (phần mở rộng bên trong)
            // Nó sẽ chuyển qua màn hình các thể loại tương ứng với chủ đề đó
            imgAllChuDe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // gửi data và chuyển qua màn hình danh sách các bài hát
                    Intent intent = new Intent(context, DanhSachTheLoaiTheoChuDeActivity.class);
                    intent.putExtra("chu_de", listChuDe.get(getPosition()));// key put vào trùng với key playlist
                    context.startActivity(intent);
                }
            });
        }
    }
}
