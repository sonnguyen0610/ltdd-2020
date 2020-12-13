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

// lớp này dùng để lấy data và đưa lên layout
public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {
    Context context;
    ArrayList<Album> listAlbums;

    public AlbumAdapter(Context context, ArrayList<Album> listAlbums) {
        this.context = context;
        this.listAlbums = listAlbums;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.line_album, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Album album = listAlbums.get(position);
        holder.txtNameAlbum.setText(album.getTenAlbum());
        holder.txtAuthorAlbum.setText(album.getTenCasiAlbum());
        Picasso.get().load(album.getHinhAnhAlbum()).into(holder.imgAlbum);
    }

    // bao nhiêu item trong RecycleView
    @Override
    public int getItemCount() {
        return listAlbums.size();
    }

    // class view holder để tăng tóc độ ánh xạ các component
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNameAlbum, txtAuthorAlbum;
        ImageView imgAlbum;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNameAlbum = itemView.findViewById(R.id.txtNameAlbum);
            txtAuthorAlbum = itemView.findViewById(R.id.txtAuthorAlbum);
            imgAlbum = itemView.findViewById(R.id.imgAlbum);

            // sự kiện khi nhấn vào RecyvleView ở phần Album (ở ngoài giao diện chính)
            imgAlbum.setOnClickListener(new View.OnClickListener() {
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
