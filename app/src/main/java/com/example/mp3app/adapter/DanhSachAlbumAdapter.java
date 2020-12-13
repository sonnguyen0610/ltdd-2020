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

import com.example.mp3app.activity.DanhSachBaiHatActivity;
import com.example.mp3app.model.Album;
import com.example.mp3app.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DanhSachAlbumAdapter extends RecyclerView.Adapter<DanhSachAlbumAdapter.ViewHolder> {
    Context context;
    ArrayList<Album> listAlbums;

    public DanhSachAlbumAdapter(Context context, ArrayList<Album> listAlbums) {
        this.context = context;
        this.listAlbums = listAlbums;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.line_danh_sach_all_album, parent, false);
        return new DanhSachAlbumAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // gán data cho mỗi item
        Album album = listAlbums.get(position);
        Picasso.get().load(album.getHinhAnhAlbum()).into(holder.imgAllAlbum);
        holder.txtNameAllAlbum.setText(album.getTenAlbum());
        holder.txtAuthorAllAlbum.setText(album.getTenCasiAlbum());

    }

    @Override
    public int getItemCount() {
        return listAlbums.size();
    }

    // View Holder để tăng tốc việc ánh xạ lại cho View
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNameAllAlbum, txtAuthorAllAlbum;
        ImageView imgAllAlbum;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            txtNameAllAlbum = itemView.findViewById(R.id.txtNameAllAlbum);
            txtAuthorAllAlbum = itemView.findViewById(R.id.txtAuthorAllAlbum);
            imgAllAlbum = itemView.findViewById(R.id.imgAllAlbum);

            // sự kiện khi nhấn vào hình từng ietm trong Album (phần mở rộng bên trong)
            imgAllAlbum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // gửi data và chuyển qua màn hình danh sách các bài hát
                    Intent intent = new Intent(context, DanhSachBaiHatActivity.class);
                    intent.putExtra("all_album", listAlbums.get(getPosition()));// key put vào trùng với key playlist
                    context.startActivity(intent);
                }
            });
        }
    }
}
